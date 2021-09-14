#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)    # 현재 stop.sh가 속해 있는 경로
source ${ABSDIR}/profile.sh   # 일종의 import문 => profile.sh의 함수 사용 가능

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 port: $IDLE_PORT"
  echo "> Port 전환"
  # set~: nginx가 변경할 프록시 주소 설정
  # sudo~: set~에서 넘겨준 문장을 service-url.inc에 덮어씀
  echo "> set \$service_url http://127.0.0.1:${IDLE_PORT};" |
          sudo tee /etc/nginx/conf.d/service-url.inc
  echo "> Nginx Reload"
  sudo service nginx reload
}