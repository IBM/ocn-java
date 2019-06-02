#!/bin/bash
set -euo pipefail
cd ${0%/*}/coffee-shop

trap cleanup EXIT

function cleanup() {
  echo stopping Docker container...
  docker stop coffee-shop &> /dev/null || true
  rm -Rf /tmp/wad-dropins/*
}

echo building project...
docker build -t coffee-shop .
docker stop coffee-shop &> /dev/null || true

mkdir -p /tmp/wad-dropins/

echo starting container...
docker run -d --rm \
  --name coffee-shop \
  --network dkrnet \
  -p 9080:9080 \
  -e COFFEESHOP_ORDER_DEFAULTCOFFEETYPE=LATTE \
  -p 9443:9443 \
  -v /tmp/wad-dropins/:/opt/ol/wlp/usr/servers/defaultServer/dropins/ \
  coffee-shop

wad.sh
