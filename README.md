# InPost shopping platform

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring-boot&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2CA5E0?style=flat-square&logo=docker&logoColor=white)

---

Project created for the needs of the recruitment process. Partial implementation of a shopping platform. 

---

### Key product assumptions:
1. The project is part of the shopping platform and allows for the calculation of the total price of a specified order item.
2. Order item is understood as a product identifier and its selected quantity. 
3. Each time the service calculates total price, it verifies whether any discount is currently valid for a selected product and applies it if it's justified. 
4. Discounts can be applied according to two policies - quantity-based, percentage-based. 
5. Only one discount for a selected product can be valid at one time. 

---

### Key technical assumptions:
1. Products in the system are identified by `UUID`
2. The implemented logic found fully its place in the `products` package, which is a kind of simplification. Further stages of service development require defining the boundaries between `products` and, for example, the part responsible for `orders`.
3. Discount configuration is stored as JSON in the database. Configuration can be changed using SQL query.
4. Expected discount configuration format:
   1. quantity-based
   ```
    [{"minimumOrderQuantity": 3, "discountInCents": 300}, {"minimumOrderQuantity": 5, "discountInCents": 500}]
   ```
   2. percentage-based
   ```
   [{"minimumOrderQuantity": 2, "discountPercent": 0.05}, {"minimumOrderQuantity": 8, "discountPercent": 0.25}]
   ```
---

### How to run on your local environment?
#### Prerequisites
1. Docker - [Docker Desktop](https://docs.docker.com/desktop/) or [Docker CE](https://docs.docker.com/engine/install/)
2. Java 21 - recommended implementation [Amazon Corretto](https://docs.aws.amazon.com/corretto/latest/corretto-21-ug/what-is-corretto-21.html)
3. `JAVA_HOME` in your `PATH`

#### Standard development setup
1. Run database
```
docker-compose -f docker-compose-local-database.yml up -d
```
2. Run application using CLI...
```
./gradlew bootLocalRun
```
3. ... or IntelliJ (set *local* profile)

#### Starting the application with the local profile runs the scripts responsible for adding sample data to the database, including sample discounts configuration.

#### Fully containerized setup
1. Build application
```
./gradlew build
```
2. Build docker image
```
docker build -t shopping-platform:local -f Dockerfile .
```
3. Run the application using docker compose
```
docker-compose -f docker-compose-full-setup.yml up -d
```

---
### API
Use the prepared sample HTTP requests to find out how the prepared API works: [sample requests](intellij_http_client/products/products.rest). Happy shopping!