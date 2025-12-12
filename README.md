# E-Commerce Order Management API

A robust, transactional RESTful API designed to handle e-commerce operations, focusing on product catalog management and secure order processing.

This project demonstrates a clean architecture approach using **Java** and **Spring Boot**, with a strong emphasis on data integrity, ACID transactions, and proper handling of Many-to-Many relationships with historical attributes.

## 🚀 Key Features

### Core Business Logic
* **Transactional Order Processing:** Implements atomic operations using `@Transactional` to ensure that orders and their line items are persisted together or rolled back entirely in case of failure.
* **Historical Price Freezing:** Solves the classic e-commerce problem of changing catalog prices. The system snapshots the price of an item at the moment of purchase, preserving the order history integrity.
* **Advanced ORM:** Utilizes Hibernate/JPA for complex entity mapping, including `OneToMany` and bidirectional relationships.

### Quality Assurance & Security
* **Robust Input Validation:** Utilizes `Jakarta Validation` (Hibernate Validator) to ensure data integrity at the controller entry point. Protects the API from invalid IDs, negative quantities, or malformed requests before they reach business logic.
* **Centralized Exception Handling:** Implements `@RestControllerAdvice` to transform uncaught exceptions and validation errors into standardized, user-friendly JSON error responses (avoiding raw stack traces in responses).
* **Unit Testing:** Business logic is verified using **JUnit 5** and **Mockito**, ensuring service methods handle edge cases (like missing customers or products) correctly in isolation.

### DevOps & Infrastructure
* **Containerized Environment:** Fully dockerized database setup. The `docker-compose.yaml` configuration ensures a consistent development environment identical to production, eliminating "works on my machine" issues.
* **CI/CD Pipeline:** Integrated **GitHub Actions** workflow that automatically compiles the code and runs the test suite on every push to the main branch, ensuring code quality and build stability.
* **Automated Documentation:** Integrated **Swagger/OpenAPI** for real-time API exploration.

## 🛠️ Tech Stack

* **Language:** Java 21
* **Framework:** Spring Boot 3 (Spring Web, Spring Data JPA, Validation)
* **Database:** PostgreSQL 15 (Production/Dev), H2 (Testing)
* **Testing:** JUnit 5, Mockito
* **DevOps:** Docker & Docker Compose, GitHub Actions
* **Tools:** Maven, Lombok, Swagger UI

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

### Why Docker?
This project uses Docker to orchestrate the PostgreSQL database. This allows you to run the application without installing and configuring a local database server manually. It guarantees that the database version and configuration match the application's requirements perfectly.

### Installation

1.  **Clone the repository**
    ```bash
    git clone [https://github.com/jarzeckil/ecommerce-api.git](https://github.com/jarzeckil/ecommerce-api.git)
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
## 🧪 Testing

### Unit Tests
The project includes a suite of unit tests focusing on the `OrderService`. These tests use **Mockito** to mock Repositories, allowing for fast verification of business logic (e.g., price freezing, customer validation) without spinning up the full Spring Context.

To run the unit tests, execute:
```bash
./mvnw test
```
### Integration Tests
End-to-end integration tests are implemented using `@SpringBootTest` and an in-memory **H2 database**. These tests verify the entire request lifecycle—from the REST Controller, through the Service layer, down to the Database persistence—ensuring all components interact correctly without requiring a running PostgreSQL container.

**Key scenarios covered:**
* **Happy Path:** Placing a valid order via `MockMvc` and verifying it persists in the H2 database.
* **Error Handling:** Verifying that invalid input (e.g., negative quantity) returns `400 Bad Request` and proper JSON error messages.
* **Data Integrity:** Ensuring `@Transactional` works correctly and rolls back data between tests to maintain a clean state.


## 💡 Learning Outcomes

Building this project provided deep practical insights into modern backend development:

* **Spring Context & IoC:** Gained a solid understanding of Dependency Injection and how Spring manages bean lifecycles.
* **Database Transactions:** Learned how `@Transactional` proxies work to guarantee data consistency across multiple database tables (ACID principles).
* **DTO Pattern:** Understood the importance of separating database Entities from Data Transfer Objects (DTOs) for security and API versioning.
* **Input Validation & Exception Handling:** Learned how to sanitize user input using Jakarta Validation and how to implement a global exception handler to provide clean API error responses.
* **Docker & Containerization:** Learned how to define infrastructure as code using Docker Compose to create reproducible development environments.
* **Testing Strategy:** Learned the difference between Unit and Integration testing and how to use Mocks to isolate components.
* **CI/CD:** Configured a GitHub Actions pipeline to automate the build and test process, enforcing code quality standards on every commit.
