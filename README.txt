Описание проекта:

База данных:
sudo -u postgres psql
CREATE DATABASE demo  WITH OWNER = postgres ENCODING = 'UTF8' CONNECTION LIMIT = -1;
CREATE USER demo WITH password 'demo';
GRANT ALL ON DATABASE demo TO demo;

АПИ:
http://localhost:8081/swagger-ui.html

Докер:
docker build ./ -t springbootapp
docker-compose -f docker-compose.yml up