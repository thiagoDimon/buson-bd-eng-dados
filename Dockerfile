FROM postgres:14.1-alpine

COPY modelo.sql /docker-entrypoint-initdb.d/