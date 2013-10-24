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

synchronizeVersions() {

    VERSION=`cat VERSION`

    echo ""
    echo "synchronizing all versions to [$VERSION]"
    echo ""

    DIRECTORIES=`find . -type d -maxdepth 1 -mindepth 1`
    for DIRECTORY in $DIRECTORIES
    do
        if [ $DIRECTORY == ./metridoc-grails* ]; then
            echo $DIRECTORY
            cd $DIRECTORY
            systemCall "./grailsw set-version $VERSION"
            systemCall "git add ."
            git commit -m'synchronizing version for $DIRECTORY'
            systemCall "git push origin master"
            cd -
        fi
    done

}

DIRECTORIES=`find . -type d -maxdepth 1 -mindepth 1`

echo ""
echo "Testing all grails projects"
echo ""

for DIRECTORY in $DIRECTORIES
do
    if [ $DIRECTORY == ./metridoc-grails* ]; then
        echo $DIRECTORY
        cd $DIRECTORY
        systemCall "./grailsw --refresh-dependencies --non-interactive tA"
        cd -
    fi
done

synchronizeVersions

git checkout master
VERSION=`cat VERSION`

if grep -q "\-SNAPSHOT" "VERSION"; then
    echo "VERSION file has SNAPSHOT in it, skipping release"
    exit 0
fi

synchronizeVersions

echo ""
echo "Testing all grails projects for release"
echo ""

for DIRECTORY in $DIRECTORIES
do
    if [ $DIRECTORY == ./metridoc-grails* ]; then
        echo $DIRECTORY
        cd $DIRECTORY
        systemCall "./grailsw --refresh-dependencies --non-interactive tA"
        cd -
    fi
done

echo ""
echo "Releasing ${VERSION} to GitHub"
echo ""

systemCall "git tag -a v${VERSION} -m 'tagging release'"
systemCall "git push origin v${VERSION}"

#release to bintray
echo ""
echo "Releasing ${VERSION} to BinTray"
echo ""

for DIRECTORY in $DIRECTORIES
do
    if [ $DIRECTORY == ./metridoc-grails* ]; then
        echo $DIRECTORY
        cd $DIRECTORY
        systemCall "./grailsw --refresh-dependencies --non-interactive upload-to-bintray --failOnBadCondition=false"
        cd -
    fi
done

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

synchronizeVersions

