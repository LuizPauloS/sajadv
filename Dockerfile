FROM postgres
MAINTAINER "Luiz Paulo da Silva"

ENV POSTGRES_USER sajadv
ENV POSTGRES_PASSWORD s4j@dv2020

COPY init.sql /docker-entrypoint-initdb.d/

VOLUME /home/postgresql/data/:/var/lib/postgresql/data/

EXPOSE 5432