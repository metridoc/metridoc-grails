#!/bin/sh

source helper.sh

if grep -q "\-SNAPSHOT" "VERSION"; then
    echo "VERSION file has SNAPSHOT in it, skipping release and just running build"
    source buildAll.sh
    exit 0
fi

if git diff-index --quiet HEAD --; then
    echo "everything is up to date, safe to synchronize versions and release"
else
    echo "there are changes, cannot synchronize versions or release"
    systemCall "git status"
    exit 1
fi

#release to bintray
echo ""
echo "Releasing ${VERSION} to BinTray"
echo ""

echo "resolving metridoc-core first"
cd metridoc-grails-core

systemCall "./grailsw set-version $VERSION"
systemCall "git add ."
git commit -m"synchronizing version"
systemCall "git push origin master"
systemCall "./grailsw --refresh-dependencies --non-interactive tA --stacktrace"
systemCall "./grailsw --refresh-dependencies --non-interactive upload-to-bintray --failOnBadCondition=false"
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
        systemCall "./grailsw --refresh-dependencies --non-interactive tA --stacktrace"
        systemCall "./grailsw --refresh-dependencies --non-interactive upload-to-bintray --failOnBadCondition=false"
        cd -
    fi
done

MINOR_VERSION=`sed 's/[0-9]*\.[0-9]*\.\([0-9]*\).*/\1/g' VERSION`
MAJOR_VERSION=`sed 's/\([0-9]*\.[0-9]*\).*/\1/g' VERSION`
NEW_VERSION="$MAJOR_VERSION.$((MINOR_VERSION+1))-SNAPSHOT"

echo ""
echo "Releasing ${VERSION} to GitHub"
echo ""

systemCall "git tag -a v${VERSION} -m 'tagging release'"
systemCall "git push origin v${VERSION}"

echo ""
echo "Bumping version to [$NEW_VERSION]"
echo ""

echo "$NEW_VERSION" > VERSION

systemCall "git add VERSION"
systemCall "git commit -m 'committing a new version'"
systemCall "git push origin master"

synchronizeVersions

