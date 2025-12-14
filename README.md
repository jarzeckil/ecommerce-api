# E-Commerce Order Management API

![Java](https://img.shields.io/badge/Java-21-orange?logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.0-6DB33F?logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?logo=docker&logoColor=white)

Backend service for handling high-concurrency e-commerce orders. Focuses on **data integrity**, **transactional safety**, and **historical accuracy** of financial records.

## 🎯 The Business Problem

Building an e-commerce backend is not just about CRUD operations. The core challenge is ensuring that financial data remains consistent over time, even when the product catalog changes.

**Key constraints solved:**

1.  **Price Volatility:** Product prices change. An order placed yesterday must retain yesterday's price, even if the catalog price doubles today.
2.  **Data Integrity:** An order cannot exist without valid line items, and inventory must be reserved atomically.

## ⚡ Tech Stack

| Category     | Technology                             |
| ------------ | -------------------------------------- |
| **Core**     | Java 21, Spring Boot 3 (Web, Data JPA) |
| **Database** | PostgreSQL 15 (Prod), H2 (Test)        |
| **Infra**    | Docker, Docker Compose                 |
| **Testing**  | JUnit 5, Mockito                       |
| **Docs**     | Swagger/OpenAPI                        |

## 🏗️ Architecture & Key Solutions

### 1. Historical Price Freezing (The "Snapshot" Pattern)

To solve the Price Volatility problem, I avoided direct linking to the `Item` price in historical records.

- **Implementation:** The `ItemOrder` join table acts as a snapshot.
- **Mechanism:** When an order is placed, the system copies the _current_ price from `Item` to `ItemOrder`.
- **Result:** Financial reports remain accurate regardless of future catalog updates.

### 2. Transactional Atomicity

Ensuring that an Order is never created if a Line Item fails validation.

- **Solution:** Strict `@Transactional` boundaries at the Service layer.
- **Outcome:** If any part of the order processing fails (e.g., validation error, db connection), the entire transaction rolls back. No "ghost orders" in the database.

## 🔧 Technical Challenges & Solutions

### Challenge: Global Error Handling

**Problem:** Default Spring Boot errors expose stack traces and internal structure to the client.
**Solution:** Built a centralized `@RestControllerAdvice` to catch exceptions (like `EntityNotFound` or `ValidationException`) and map them to a standardized JSON error structure.

## 📦 How to Run

No Java installation required. Runs entirely in Docker.

1.  **Clone & Start:**

    ```bash
    git clone https://github.com/jarzeckil/ecommerce-api.git
    cd ecommerce-api
    docker-compose up --build
    ```

2.  **Access:**
    - **API & UI:** [http://localhost:8080](http://localhost:8080)
    - **Swagger Docs:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    - **DB Credentials:** `postgres` / `postgres` (Port 5432)

## 🧪 Testing

To run unit and integration tests locally:

```bash
./mvnw test
```
