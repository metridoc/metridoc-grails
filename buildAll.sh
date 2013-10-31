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

echo ""
echo "Testing all grails projects"
echo ""

echo "first testing core"
cd metridoc-grails-core
systemCall "./grailsw --refresh-dependencies --non-interactive tA --stacktrace"
echo "installing [metridoc-grails-core]"
systemCall "./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace"
cd -

for DIRECTORY in $DIRECTORIES
do
    if [ "$DIRECTORY" != "./metridoc-grails-core" ]; then
        echo "testing [$DIRECTORY]"
        cd $DIRECTORY
        systemCall "./grailsw --refresh-dependencies --non-interactive tA --stacktrace"
        echo "installing [$DIRECTORY]"
        systemCall "./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace"
        cd -
    fi
done

