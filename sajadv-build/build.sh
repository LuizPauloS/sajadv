#!/usr/bin/env bash
cd sajadv-api/
mvn clean install -DskipTests
cd ../sajadv-app/
ng --version
npm install && ng build --prod