#!/usr/bin/env bash
mvn clean install
cd sajadv-app/
ng --version
npm install && ng build --prod