#!/bin/bash
set -euo pipefail

docker stop coffee-shop barista &> /dev/null || true

docker run --rm -d \
  -p 9080:9080 \
  -p 9443:9443 \
  --name coffee-shop \
  --network dkrnet \
  sdaschner/coffee-shop:coffee-book-example-1

docker run --rm -d \
  --name barista \
  --network dkrnet \
  sdaschner/barista:coffee-book-example-1
