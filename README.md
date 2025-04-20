# 🔐 Spring Boot Auth Microservice

This project is a simple authentication microservice using **Spring Boot**, **H2 in-memory database**, and **JWT tokens**. It supports user login/logout with preloaded users.

---

## 📦 Features

- ✅ User login with JWT token generation  
- ✅ Logout endpoint updates last logout time  
- ✅ H2 in-memory database to store users  
- ✅ Preloaded sample users (admin and user)  
- ✅ Login count tracking  
- ✅ Salted password hashing  
- ✅ Role-based support

---

## 🚀 Getting Started

### 🔧 Prerequisites

- Java 17+
- Maven
- Postman (for testing)

### 1. Start Run the app

```bash
mvn spring-boot:run
```

### 2. Login Request

- Method: POST
- URL: http://localhost:8080/auth/login
- Body Type: raw → JSON
- Request Body:
  ```bash
  {
  "email": "admin@example.com",
  "password": "admin123"
  }
  ```
