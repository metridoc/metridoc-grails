#!/bin/sh

systemCall() {
    echo "running $1"
    if eval $1; then
		echo "command [$1] ran successfully"
	else
		echo "command [$1] failed with exit status [$?]"
		exit 1
	fi
}

DIRECTORIES=`find ./metridoc-grails* -type d -maxdepth 0 -mindepth 0`

VERSION=`cat VERSION`
if grep -q "\-SNAPSHOT" "VERSION"; then
    echo "VERSION file has SNAPSHOT in it, skipping release"
    exit 0
fi
