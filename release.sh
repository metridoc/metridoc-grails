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

DIRECTORIES=`find . -type d -maxdepth 1 -mindepth 1`

echo ""
echo "Testing all grails projects"
echo ""
#
#for DIRECTORY in $DIRECTORIES
#do
#    if [ $DIRECTORY == ./metridoc-grails* ]; then
#        echo $DIRECTORY
#        cd $DIRECTORY
#        systemCall "./grailsw tA"
#        cd -
#    fi
#done

VERSION=`cat VERSION`

echo ""
echo "synchronizing all versions to [$VERSION]"
echo ""


for DIRECTORY in $DIRECTORIES
do
    if [ $DIRECTORY == ./metridoc-grails* ]; then
        echo $DIRECTORY
        cd $DIRECTORY
        systemCall "./grailsw set-version $VERSION"
        cd -
    fi
done



