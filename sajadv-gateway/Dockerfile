### Multi Stage Build ###
##Estágio 1 - Compilar projeto java com maven
FROM maven:3.6.3-jdk-11-openj9 as builder

ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

RUN mkdir -p /usr/src/api
WORKDIR /usr/src/api

COPY . /usr/src/api
RUN mvn clean install

##Estágio 2 - Deploy api
FROM adoptopenjdk/openjdk11-openj9:alpine-slim
MAINTAINER 'Luiz Paulo da Silva'

ENV JAVA_OPTS=" -server -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.country=BR -Dfile.encoding=UTF-8 -Dspring.profiles.active=production"
WORKDIR /api

COPY --from=builder /usr/src/api/target/*.jar /api/sajadv-gateway.jar

EXPOSE 8762

CMD java $JAVA_OPTS -jar sajadv-gateway.jar