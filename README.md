# Employee Management - REST API

This project is a Spring Boot-based Employee Management System that provides RESTful APIs to manage employee data. It includes basic CRUD operations, pagination with sorting, validation, logging, testing, and a Swagger UI for API documentation.

## 🔗 Application URLs

- **Swagger API Docs**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)  
- **H2 Database Console**: [http://localhost:8080/h2-console/login.jsp](http://localhost:8080/h2-console/login.jsp)

## ✅ Features Included

1. **CRUD APIs for Employee**
   - Create, Update, Delete, Get by ID, Get All Employees

2. **Pagination & Sorting**
   - Supports dynamic sorting on any field (e.g., `name`, `salary`) along with pagination.

3. **Swagger Integration**
   - API documentation and testing using Swagger UI.

4. **Logging**
   - Logging enabled using `slf4j` for debugging and monitoring.

5. **Validations**
   - DTO, Entity, and Service-level validation using `javax.validation`.

6. **Unit Testing**
   - Unit tests using **JUnit 5** and **Mockito 3**.

7. **Global Exception Handling**
   - Centralized exception handling using `@ControllerAdvice`.

8. **H2 In-Memory Database**
   - In-memory H2 database with `data.sql` for sample employee records.

9. **Development Tools**
   - Includes dependencies for Spring Dev Tools, Lombok, etc., for developer productivity.

## 🛠️ Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- H2 Database
- Lombok
- JUnit & Mockito
- Swagger (springdoc-openapi)
- Maven

---

## 📂 Run Instructions

1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/endpointe-task.git
   cd endpointe-task

2. **Run the Application**
From IDE: Run the EmployeeApplication.java file.

From terminal:

bash
Copy
Edit
mvn spring-boot:run

Access Swagger & H2 Console
Visit the Swagger UI: http://localhost:8080/swagger-ui/index.html

H2 Console:
URL: jdbc:h2:mem:testdb
Username: sa
Password: (leave blank)
