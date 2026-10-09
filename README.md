# QuizHub — Quiz Microservices with MCQ Gameplay

A full-stack quiz application built with **React + Vite**, **Spring Boot microservices**, **Eureka Service Registry**, **Spring Cloud API Gateway**, and **MySQL**.

Users can browse quizzes, view multiple-choice questions (MCQs), select an answer, submit it for backend validation, receive correct/wrong feedback, and see their score.

## Features

- Quiz dashboard with quiz titles and IDs
- MCQ questions with four options (A, B, C, D)
- Single-option selection for each question
- Backend answer validation
- Correct/wrong feedback after submission
- Next-question navigation
- Final score and percentage
- Create quizzes and questions (if corresponding POST endpoints are implemented)
- Service discovery using Eureka
- API routing through Spring Cloud Gateway
- MySQL persistence

## Architecture

```text
React Frontend (Vite)
        |
        v
Spring Cloud API Gateway
        |
        +--------------------+
        |                    |
        v                    v
   Quiz Service        Question Service
        |                    |
        +---------+----------+
                  |
                  v
                MySQL

Eureka Server provides service registration and discovery.
```

## Project Structure

Your repository can be organized as a monorepo:

```text
Quiz-Microservices/
├── ServiceRegistry/
├── ApiGateway1/
├── Quize-Service/
├── Question-Service/
└── quiz-microservices-frontend/
```

Adjust folder names to match your actual project.

## Technology Stack

- **Frontend:** React, Vite, JavaScript, CSS
- **Backend:** Java, Spring Boot, Spring Data JPA
- **Microservices:** Eureka Server, Spring Cloud Gateway, REST APIs
- **Database:** MySQL
- **Build tool:** Maven
- **API testing:** Postman

## Prerequisites

Install the following before running the project:

- Java JDK compatible with your Spring Boot version
- Maven (or use each project's Maven Wrapper if present)
- Node.js LTS and npm
- MySQL Server
- Git
- Postman (recommended for API testing)

## Database Setup

Create/select the database:

```sql
CREATE DATABASE IF NOT EXISTS micro_yt;
USE micro_yt;
```

Configure each service's database settings in its `application.properties`. Do not commit real credentials to GitHub.

Example local configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/micro_yt
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Set `DB_USERNAME` and `DB_PASSWORD` as environment variables or replace them with local development values outside version control.

### MCQ Question Model

The Question entity should include fields similar to:

- `questionId`
- `question`
- `optionA`
- `optionB`
- `optionC`
- `optionD`
- `correctAnswer` (stores `A`, `B`, `C`, or `D)
- `quizId`

With `spring.jpa.hibernate.ddl-auto=update`, Hibernate can add the new columns to an existing table. Existing rows need their options and correct answer populated before they can be played.

Example SQL (replace `6` with an existing quiz ID):

```sql
INSERT INTO question
(question, optionA, optionB, optionC, optionD, correctAnswer, quiz_id)
VALUES
(
  'What is JVM in Java?',
  'Java Variable Method',
  'Java Virtual Machine',
  'Java Visual Model',
  'Java Verified Mode',
  'B',
  6
);
```

Check the real quiz IDs before inserting:

```sql
SELECT id, title FROM quiz;
```

## Backend Configuration

Use service names consistently. Example names and ports for local development:

| Service | Example application name | Example port |
|---|---|---:|
| Service Registry | `ServiceRegistry` | `8761` |
| API Gateway | `ApiGateway1` | `8080` |
| Quiz Service | `QUIZE-SERVICE` | `8081` |
| Question Service | `QUESTION-SERVICE` | `8082` |

These are examples; use the names and ports configured in your applications.

### Eureka Client Configuration

For Quiz Service, Question Service, and API Gateway, configure the Eureka URL. Example for local development:

```properties
eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

The Eureka Server should not register itself as a client:

```properties
server.port=8761
spring.application.name=ServiceRegistry
eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
```

### Gateway Routes

For a Spring Cloud Gateway MVC setup, routes should direct requests to the service IDs registered with Eureka. Example configuration (verify the exact property structure for your Spring Cloud version):

```yaml
server:
  port: 8080

spring:
  application:
    name: ApiGateway1
  cloud:
    gateway:
      server:
        webmvc:
          routes:
            - id: quiz-service
              uri: lb://QUIZE-SERVICE
              predicates:
                - Path=/quiz/**
            - id: question-service
              uri: lb://QUESTION-SERVICE
              predicates:
                - Path=/question/**

eureka:
  client:
    service-url:
      defaultZone: http://localhost:8761/eureka/
```

## MCQ API Endpoints

The following endpoints are expected from the MCQ implementation described in this project. Confirm the controller mappings and service ports match your code.

| Method | Endpoint | Purpose |
|---|---|---|
| `GET` | `/question/quiz/{quizId}/play` | Get quiz questions and options without revealing the correct answer |
| `POST` | `/question/{questionId}/answer` | Submit selected option and check answer |
| `GET` | `/question` | List questions; in a production setup, return a DTO that excludes `correctAnswer` |
| `GET` | `/quiz` | List quizzes |

### Submit an Answer

Request:

```http
POST /question/1/answer
Content-Type: application/json
```

```json
{
  "selectedAnswer": "B"
}
```

Example response:

```json
{
  "correct": true,
  "message": "Correct answer!",
  "correctAnswer": "B"
}
```

The `correctAnswer` field should be returned only after a user submits an answer. Ensure other endpoints do not expose it before submission.

## Run Locally

Start the services in this order:

1. **MySQL** — make sure the database is running.
2. **ServiceRegistry** — start the Eureka Server on port `8761`.
3. **Quiz Service** — start on its configured port (example `8081`).
4. **Question Service** — start on its configured port (example `8082`).
5. **API Gateway** — start on port `8080`.
6. **React frontend** — run the commands below.

### Start React

Open a terminal in the frontend directory:

```bash
npm install
```

Create a `.env` file in the frontend directory:

```env
VITE_API_BASE_URL=http://localhost:8080
```

Then run:

```bash
npm run dev
```

Open the local Vite URL shown in the terminal, usually `http://localhost:5173`.

The React API helper should use:

```javascript
const API_BASE = import.meta.env.VITE_API_BASE_URL;
```

If you use direct service calls during development instead of the gateway, update the API URLs accordingly.

## CORS

For local development, allow the frontend origin `http://localhost:5173` at the API Gateway. If you call services directly from the browser, those services must allow the frontend origin too.

For production, configure CORS to allow only your deployed frontend domain. Do not rely on localhost CORS settings for a deployed site.

## Test with Postman

With the services running, try these local requests through the gateway:

```http
GET http://localhost:8080/quiz
GET http://localhost:8080/question
GET http://localhost:8080/question/quiz/6/play
POST http://localhost:8080/question/1/answer
```

For the POST request, select **Body → raw → JSON**:

```json
{
  "selectedAnswer": "B"
}
```

Use quiz and question IDs that actually exist in your database.

## Deployment Notes

- Deploy MySQL and all Spring Boot services to reachable environments.
- Configure datasource credentials and URLs using environment variables.
- Configure service-to-service Eureka URLs and advertised hostnames for the deployment platform; do not use `localhost` between deployed services.
- Expose the API Gateway publicly and point the React frontend at its public URL with `VITE_API_BASE_URL`.
- Update CORS to allow the deployed frontend domain.
- Do not commit passwords, database credentials, JWT secrets, or other private values.
- If a route returns `503`, check Eureka registration, service IDs, advertised hostnames, and ports.
- If the browser reports a CORS error, check the Gateway CORS configuration and the exact frontend origin.

## Important Implementation Notes

- The code snippets in setup instructions must be integrated with your existing service interfaces, implementation classes, and controller mappings.
- Make sure `QuestionService` and `QuestionServiceImpl` include the `getQuestionsForQuiz` and `checkAnswer` methods used by the controller.
- The question response DTO should not include `correctAnswer` before submission.
- For production-grade scoring, consider submitting all answers to a backend quiz-attempt endpoint and calculating the final score on the server. The simple frontend score shown here is suitable as a learning/demo feature, but can be manipulated by a client.
- If Lombok is used, ensure annotation processing is enabled in your IDE and Lombok is included in the Maven dependencies.

## License

Add a license file if you intend to publish or distribute this project publicly.
