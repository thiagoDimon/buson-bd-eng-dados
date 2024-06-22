FROM postgres:16.2

ENV POSTGRES_PASSWORD postgres
ENV POSTGRES_DB postgres
ENV POSTGRES_HOST_AUTH_METHOD trust
COPY ./docker-entrypoint-initdb.d/init.sql /docker-entrypoint-initdb.d/