FROM postgres:15.1-alpine

LABEL author="Igar Skachko"
LABEL description="Postgres Image for my project"
LABEL version="1.0"

COPY ./sql/*.sql /docker-entrypoint-initdb.d/