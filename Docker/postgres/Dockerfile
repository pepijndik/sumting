FROM postgres:latest

ADD ${BACKUP_SQL_PATH} /docker-entrypoint-initdb.d
#CMD psql ${POSTGRES_DB} < /backup.sql

