#!/bin/sh

source helper.sh

MINOR_VERSION=`sed 's/[0-9]*\.[0-9]*\.\([0-9]*\).*/\1/g' VERSION`
MAJOR_VERSION=`sed 's/\([0-9]*\.[0-9]*\).*/\1/g' VERSION`
NEW_VERSION="$MAJOR_VERSION.$((MINOR_VERSION+1))-SNAPSHOT"

echo ""
echo "Bumping version to [$NEW_VERSION]"
echo ""

echo "$NEW_VERSION" > VERSION

systemCall "git add VERSION"
systemCall "git commit -m 'committing a new version'"
systemCall "git push origin master"

echo "resolving metridoc-core first"
cd metridoc-grails-core

systemCall "./grailsw set-version $VERSION"
systemCall "git add ."
git commit -m"synchronizing version"
systemCall "git push origin master"
cd -

for DIRECTORY in $DIRECTORIES
    do
    if [ "$DIRECTORY" != "./metridoc-grails-core" ]; then
        echo "uploading [$DIRECTORY]"
        cd $DIRECTORY
        systemCall "./grailsw set-version $VERSION"
        systemCall "git add ."
        git commit -m"synchronizing version"
        systemCall "git push origin master"
        cd -
    fi
done


