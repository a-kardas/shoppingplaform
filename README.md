# InPost shopping platform

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=flat-square&logo=docker&logoColor=white)

---

Project created for the needs of the recruitment process. Partial implementation of a shopping platform. 

---

### Key assumptions:
1. Products in the system are identified by `UUID`
2. Discounts can be applied according to two policies - quantity-based, percentage-based
3. The discount policy used is configurable using TBD

---

### How to run on your local environment?
#### Prerequisites
1. Docker - [Docker Desktop](https://docs.docker.com/desktop/) or [Docker CE](https://docs.docker.com/engine/install/)
2. Java 21 - recommended implementation [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html)
3. Set `JAVA_HOME` in your `PATH`

#### Standard development setup
1. Run database
```
docker-compose up -d
```
2. Run application using CLI...
```
gradlew bootLocalRun
```
3. ... or IntelliJ (set *local* profile)

#### Fully containerized setup
1. Build application
```
gradlew build
```
2. Build docker image
```
docker build -t shopping-platform:local -f Dockerfile .
```
3. Run the application using docker compose
```
docker-compose up -d
```
---
### API
Use the prepared sample HTTP requests to find out how the prepared API works: [sample requests](intellij_http_client/products/products.rest). Happy shopping!