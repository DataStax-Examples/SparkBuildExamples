#!/bin/bash

start_location=`pwd`
exit_value=0
failures=""

# First Arg Directory
# Second Arg Command
test_command () {
    for sys in "dse" "oss"
    do
        echo "Testing $1/$sys $2"
        cd $start_location
        cd $1/$sys
        $2 || { exit_value=$?; echo "ERROR: $1/$sys $2 Failed"; failures=$failures+"$1/$sys $2 Failed"+$'\n'; }
    done
}

for language in "java" "scala"
do
    echo "Testing $language Builds"
    echo "Gradle"
    test_command "$language/gradle" "./gradlew -q shadowJar"
    echo "SBT"
    test_command "$language/sbt" "sbt -Dsbt.log.noformat=true --error assembly"
    echo "Maven"
    test_command "$language/maven" "mvn -q package"
done
echo $failures
exit $exit_value



