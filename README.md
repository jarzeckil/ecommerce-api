# E-Commerce Order Management API

A robust, transactional RESTful API designed to handle e-commerce operations, focusing on product catalog management and secure order processing.

This project demonstrates a clean architecture approach using **Java** and **Spring Boot**, with a strong emphasis on data integrity, ACID transactions, and proper handling of Many-to-Many relationships with historical attributes.

## 🚀 Key Features

* **Transactional Order Processing:** Implements atomic operations using `@Transactional` to ensure that orders and their line items are persisted together or rolled back entirely in case of failure.
* **Historical Price Freezing:** Solves the classic e-commerce problem of changing catalog prices. The system snapshots the price of an item at the moment of purchase, preserving the order history integrity.
* **RESTful Architecture:** Follows standard REST conventions for resource management (GET, POST).
* **Advanced ORM:** Utilizes Hibernate/JPA for complex entity mapping, including `OneToMany` and bidirectional relationships.
* **Containerized Environment:** Fully dockerized database setup for easy deployment and development.
* **Automated Documentation:** Integrated **Swagger/OpenAPI** for real-time API exploration.

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3 (Spring Web, Spring Data JPA)
* **Database:** PostgreSQL 15
* **Tools:** Docker & Docker Compose, Maven, Lombok, Swagger UI

## 🏗️ Architecture & Database Model

The system is built around a normalized relational database schema consisting of 5 core entities:

1.  **Customer:** User identity management.
2.  **ItemCategory:** Product classification.
3.  **Item:** The product catalog (contains current prices).
4.  **Order:** Order header (timestamp, customer reference).
5.  **ItemOrder (Join Table):** The association entity linking Orders and Items. It stores the *quantity* and the *frozen price* at the transaction time.

## 📦 Getting Started

### Prerequisites
* Docker & Docker Compose
* Java JDK 17 or higher
* Maven (optional, wrapper included)

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/jarzeckil/ecommerce-api.git]
    cd ecommerce-api
    ```

2.  **Start the Infrastructure**
    Launch the PostgreSQL database container:
    ```bash
    docker compose up -d
    ```

3.  **Run the Application**
    Build and start the Spring Boot server:
    ```bash
    ./mvnw spring-boot:run
    ```

The server will start on port `8080`.
*Note: The application includes a `DataSeeder` that will automatically populate the database with sample products and customers upon the first launch.*

## 🔌 API Documentation

Once the application is running, you can access the interactive Swagger UI to test endpoints:

👉 **[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)**

### Main Endpoints

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/items` | Retrieve all products |
| `GET` | `/items?categoryId={id}` | Filter products by category |
| `GET` | `/items/{id}` | Get product details |
| `POST` | `/orders` | Place a new order (Transactional) |

### Sample JSON for Order Placement (POST)

```json
{
  "customerId": 1,
  "items": [
    {
      "itemId": 1,
      "quantity": 2
    },
    {
      "itemId": 3,
      "quantity": 1
    }
  ]
}
```
# TODO
- add tests
