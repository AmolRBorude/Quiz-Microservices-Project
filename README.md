# Quiz Microservices Project

## 📌 Project Overview

The Quiz Microservices Project is a web-based application developed using Spring Boot Microservices and React. It separates application responsibilities into independent services.

The project uses Spring Cloud Gateway for API routing, Eureka Server for service discovery, and MySQL for database management.

## 🚀 Technologies Used

### Backend
- Java
- Spring Boot
- Spring Data JPA
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Maven
- MySQL

### Frontend
- React.js
- Vite
- JavaScript
- HTML
- CSS

## 🏗️ Microservices

1. **Service Registry** – Registers and discovers microservices using Eureka Server.
2. **API Gateway** – Provides a single entry point for frontend API requests.
3. **Question Service** – Manages question-related operations.
4. **Quiz Service** – Manages quiz-related operations.
5. **React Frontend** – Provides the user interface.

## 📂 Project Structure

```text
Quiz-Microservices/
│
├── ApiGateway1/
├── QuestionService/
├── Quize-Service/
├── ServiceRegistry/
├── quiz-microservices-frontend/
├── .gitignore
└── README.md
