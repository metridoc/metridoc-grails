#!/bin/sh

source helper.sh

echo ""
echo "Testing all grails projects"
echo ""

echo "first testing core"
cd metridoc-grails-core
./grailsw --refresh-dependencies --non-interactive tA :unit --stacktrace
./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace
cd -

for DIRECTORY in $DIRECTORIES
do
    if [ "$DIRECTORY" != "./metridoc-grails-core" ]; then
        echo "testing [$DIRECTORY]"
        cd $DIRECTORY
        ./grailsw --refresh-dependencies --non-interactive tA :unit --stacktrace
        ./grailsw --refresh-dependencies --non-interactive maven-install --stacktrace
        cd -
    fi
done
