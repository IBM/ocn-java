#!/bin/bash
set -euo pipefail
cd ${0%/*}

function build() {
  pushd $1
    mvn clean package
    docker build -t sdaschner/${1}:coffee-book-example-1 .
  popd
}

build coffee-shop
build barista
