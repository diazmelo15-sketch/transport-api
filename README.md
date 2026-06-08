# Transport API   

API REST para la gestión de órdenes de transporte. 

## Tecnologías  
- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Securty
- JWT
- MySQL
- Swagger/OpenAPI
- Maven
 
## Instalación y ejecución

Requisitos

Java 17

Maven 3.9+

Docker y Docker Compose (opcional)

Git

## Configurar la base de datos

Si utilizas MariaDB/MySQL local:

Crear una base de datos:

CREATE DATABASE transport_db;

## Configuración spring

Crear el archivo 'application-local.properties':  

#### properties 

#datos de conexion a DB

spring.datasource.url=jdbc:mysql://localhost:3306/transport_db

spring.datasource.username=user

spring.datasource.password=password

spring.jpa.show-sql=true

#propiedades para JWT

jwt.secret=miClaveSuperSecretaParaTransportApi2026

jwt.expiration=300000

#credenciales para auth

auth.username=admin

#admin123

auth.password=$2a$10$CcnkaswgxGSHxwcBvbg.Sex1nPzY9w3twPgrYKuV/didC6dSfnFO2

file.upload.dir=C:/transport-api/files-uploads

#ruta logs

logging.file.name=C:/transport-api/logs/transport-api.log


## Compilar el proyecto
mvn clean package

-sin test(en caso de error por la conexión a DB)

mvn clean package -DskipTests

-en caso que no se tenga intalado maven

./mvnw.cmd clean package -DskipTests

## Crear contenedor Docker

### Opcion1 (Mysql externo de docker)

-crear imagen

docker build -t transport-api .

-correr contenedor

docker run --name transport-api-app -p 8080:8080 transport-api

### Opcion 2 (con mysql en docker)

docker compose up --build

## Acceder a Swagger

http://localhost:8080/swagger-ui/index.html#/

