#!/usr/bin/env bash

# nginx와 연결되어 있지않은 springboot를 종료하기위한 스크립트
# 즉, 해당 springboot를 종료하고, 새로운 버전의 jar를 올려 실행시킨 후 nginx와 연결

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)    # 현재 stop.sh가 속해 있는 경로
source ${ABSDIR}/profile.sh   # 일종의 import문 => profile.sh의 함수 사용 가능

IDLE_PORT=$(find_idle_port)   # Nginx와 비연결 상태인 springboot 포트번호

echo "> $IDLE_PORT 에서 구동 중인 애플리케이션 pid 확인"
IDLE_PID=$(lsof -ti tcp:${IDLE_PORT})

if [ -z ${IDLE_PID} ]
then
  echo "> 현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $IDLE_PID"
  kill -15 ${IDLE_PID}
  sleep 5
fi