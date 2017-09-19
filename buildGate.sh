#!/bin/bash

source helper.sh

echo ""
echo "Testing all Gate Plugin"
echo ""

cd ./metridoc-grails-gate
systemCall "./grailsw --refresh-dependencies --non-interactive tA :unit --stacktrace"
systemCall "./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace"
cd -