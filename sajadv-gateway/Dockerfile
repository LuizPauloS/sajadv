FROM adoptopenjdk/openjdk11-openj9:alpine-slim
MAINTAINER 'Luiz Paulo da Silva' <luiz.paulo.09@hotmail.com>

ENV JAVA_OPTS=" -server -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.country=BR -Dfile.encoding=UTF-8 -Dspring.profiles.active=production"
WORKDIR /api

COPY ./target/*.jar /api/sajadv-gateway.jar

EXPOSE 8762

CMD java $JAVA_OPTS -jar sajadv-gateway.jar