#!/bin/sh

source helper.sh

echo ""
echo "installing all grails projects"
echo ""

echo "first installing core"
cd metridoc-grails-core
systemCall "./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace"
cd -

for DIRECTORY in $DIRECTORIES
do
    if [ "$DIRECTORY" != "./metridoc-grails-core" ]; then
        cd $DIRECTORY
        echo "installing [$DIRECTORY]"
        systemCall "./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace"
        cd -
    fi
done