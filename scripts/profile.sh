#!/usr/bin/env bash

# 쉬고있는 profile 찾기: real1이 쉬는중->real2 사용, real2가 쉬는중->real1 사용
function find_idle_profile() {
  # $(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)
  #     : 현재 nginx가 보고있는 springboot가 정상 수행중인지 확인(리턴값: HttpStatus)
  #     : 정상은 200, 비정상은 400~503값 이므로 400이상 값은 모두 예외처리 => real2로 profile변경
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ]   # 400보다 큰값 리턴
  then
    CURRENT_PROFILE=real2
  else
    CURRENT_PROFILE=$(curl -s hyyp://localhost/profile)
  fi

  # IDLE_PROFILE: 현재 nginx에 연결되어 있지 않은 profile
  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고있는 profile의 port찾기
# bash는 값을 반환할 수 없으므로 마지막 line에 결과를 출력한 후 클라이언트에서 이 값을 사용하도록 설계
function find_idle_port() {
  IDLE_PROFILE=$(find_idle_profile)
  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}