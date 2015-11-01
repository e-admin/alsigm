#!/bin/bash
if [ -f setenv.sh ]
then
    source setenv.sh
fi
mvn install:install-file -Dfile=maven-plugins/maven-generate-resources-archivo-plugin-1.1.jar -Dpackaging=jar -DgroupId=es.ieci.maven.plugins -DartifactId=maven-generate-resources-archivo-plugin -Dversion=1.1 -DgeneratePom=true
mvn clean install -Dmaven.test.skip=true
mvn clean install -Dwars -Dmaven.test.skip=true
mvn package -P generate-distri
