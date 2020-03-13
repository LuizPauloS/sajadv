FROM adoptopenjdk/openjdk11-openj9:alpine-slim
LABEL maintainer 'Luiz Paulo da Silva'

ENV JAVA_OPTS=" -server -Duser.timezone=America/Sao_Paulo -Duser.language=pt -Duser.country=BR -Dfile.encoding=UTF-8"
ADD target/*.jar /build/app.jar
WORKDIR /build/

VOLUME /home/db/img/:/home/img

EXPOSE 8080

CMD java $JAVA_OPTS -jar app.jar