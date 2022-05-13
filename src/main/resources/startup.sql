DROP DATABASE IF EXISTS acmebnb;
CREATE DATABASE acmebnb;

DROP USER IF EXISTS 'acmebnb'@'localhost';
CREATE USER 'acmebnb'@'localhost' IDENTIFIED BY 'FW6DLmm#OMio';
GRANT ALL PRIVILEGES ON acmebnb.* TO 'acmebnb'@'localhost';

