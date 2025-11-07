# Pland-IA

A full-stack productivity platform with intelligent pantry management, built as a personal portfolio project to demonstrate modern microservices architecture and development practices.

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![.NET](https://img.shields.io/badge/.NET-8.0-purple.svg)](https://dotnet.microsoft.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-green.svg)](https://spring.io/projects/spring-boot)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.0+-blue.svg)](https://www.typescriptlang.org/)
[![Tests](https://img.shields.io/badge/Tests-34%2F34%20passing-brightgreen.svg)](#testing)

---

## Table of Contents

- [About](#about)
- [Features](#features)
- [Architecture](#architecture)
- [Tech Stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Testing](#testing)
- [Project Structure](#project-structure)
- [Development Progress](#development-progress)
- [Contributing](#contributing)
- [Contact](#contact)
- [License](#license)

---

## About

Pland-IA combines a Notion-inspired productivity workspace with an AI-powered pantry management system. This project serves as a comprehensive demonstration of my full-stack development capabilities, showcasing expertise across multiple programming languages, frameworks, and modern software development practices.

### Project Goals

**Primary Objectives:**

- Demonstrate proficiency in microservices architecture with multiple tech stacks
- Showcase clean code, testing practices, and production-ready development
- Integrate AI functionality (OpenAI GPT) in a practical, real-world context
- Build a complete system from authentication to data persistence

**Technical Showcase:**

- **.NET 8** backend service (Auth Service)
- **Spring Boot 3** backend service (Core Service)
- **Python/FastAPI** backend service (Pantry Service - planned)
- **React + TypeScript** frontend (in development)
- **PostgreSQL** with multi-schema architecture
- **JWT authentication** with token refresh flow
- **Comprehensive unit testing** (34 tests across services)
- **Docker-ready** deployment setup

### Core Features

**Productivity Suite**

- Workspace and project organization
- Rich text pages and documentation
- Task management system
- Secure user authentication

**Intelligent Pantry System**

- AI-powered meal plan generation based on dietary preferences
- Virtual pantry inventory tracking
- Automated shopping list creation
- Recipe suggestions using available ingredients

---

## Features

### Currently Implemented

**Authentication & Authorization**

- User registration and login with secure password hashing (BCrypt)
- JWT access and refresh token flow
- Token validation across services
- User session management

**Workspace Management**

- Create, read, update, and delete workspaces
- User-specific workspace isolation
- RESTful API endpoints with proper error handling

**Infrastructure**

- Environment-based configuration (Development/Production)
- Structured logging with rotation policies
- CORS configuration for cross-origin requests
- Database migrations with Entity Framework

### Planned Features

- Project and page management within workspaces
- Task tracking with deadlines and priorities
- Rich text editor for documentation
- Pantry inventory management
- AI-powered diet generation
- Recipe recommendation engine
- Shopping list automation
- Web and mobile clients

---

## Architecture

### System Overview

Pland-IA follows a microservices architecture with clear separation of concerns. Each service handles specific business domains and communicates through well-defined APIs.

```
┌────────────────────────────────────────────────────────┐
│                    CLIENTS                             │
│                                                        │
│  Web App (React)  │  Mobile (Flutter)  │  API Clients│
└────────────────────────────────────────────────────────┘
                            │
                            │ HTTPS + JWT
                            ▼
┌────────────────────────────────────────────────────────┐
│                  MICROSERVICES                         │
│                                                        │
│  ┌──────────────┐  ┌──────────────┐  ┌─────────────┐│
│  │Auth Service  │  │Core Service  │  │Pantry Service││
│  │(.NET 8)      │  │(Spring Boot) │  │(FastAPI)     ││
│  │Port: 5001    │  │Port: 8080    │  │Port: 8000    ││
│  │              │  │              │  │              ││
│  │• Register    │  │• Workspaces  │  │• Pantry      ││
│  │• Login       │  │• Projects    │  │• AI Diets    ││
│  │• JWT Tokens  │  │• Pages       │  │• Recipes     ││
│  │• Refresh     │  │• Tasks       │  │• Shopping    ││
│  └──────────────┘  └──────────────┘  └─────────────┘│
└────────────────────────────────────────────────────────┘
                            │
                            ▼
┌────────────────────────────────────────────────────────┐
│           PostgreSQL 15 (Multi-Schema)                 │
│                                                        │
│  auth_schema  │  core_schema  │  pantry_schema       │
└────────────────────────────────────────────────────────┘
```

### Authentication Flow

1. User registers/logs in through Auth Service
2. Auth Service returns JWT access token + refresh token
3. Client includes access token in Authorization header for subsequent requests
4. Core/Pantry services validate JWT and extract user identity
5. Refresh token can be used to obtain new access token when expired

### Service Responsibilities

| Service | Technology | Purpose | Status |
|---------|-----------|---------|--------|
| **Auth Service** | .NET 8 | User authentication, JWT generation | ✅ Complete |
| **Core Service** | Spring Boot 3 | Workspaces, projects, pages, tasks | ✅ Complete |
| **Pantry Service** | FastAPI | AI diets, recipes, inventory | 🔄 Planned |

---

## Tech Stack

### Backend Services

**Auth Service (.NET 8)**

- ASP.NET Core Web API
- Entity Framework Core (ORM)
- PostgreSQL database
- BCrypt password hashing
- JWT token generation
- Serilog structured logging
- xUnit + Moq for testing

**Core Service (Spring Boot 3.5)**

- Spring Web MVC
- Spring Data JPA (Hibernate)
- Spring Security + JWT
- PostgreSQL database
- Logback logging
- JUnit 5 + Mockito for testing

**Pantry Service (Python 3.11 - Planned)**

- FastAPI framework
- SQLAlchemy ORM
- OpenAI API integration
- pytest for testing

### Database

- **PostgreSQL 15** with separate schemas per service
- Entity Framework migrations (.NET)
- Hibernate auto-DDL (Spring Boot)

### DevOps & Tools

- Docker & Docker Compose
- Git with conventional commits
- Environment-based configuration
- Structured logging with rotation
- Comprehensive unit testing

### Frontend (Planned)

- React 18 with TypeScript
- Vite build tool
- Material-UI components
- Zustand state management
- Axios for HTTP requests

---

## Prerequisites

Before setting up the project, ensure you have:

**Required**

- **.NET SDK 8.0+** - [Download](https://dotnet.microsoft.com/download)
- **Java JDK 17+** - [Download](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.6+** - [Download](https://maven.apache.org/download.cgi)
- **PostgreSQL 15+** or **Docker** - [Download](https://www.postgresql.org/download/) | [Docker](https://www.docker.com/)
- **Git** - [Download](https://git-scm.com/downloads)

**Optional**

- **Python 3.11+** (for Pantry Service when implemented)
- **Node.js 18+** (for frontend when implemented)
- **Redis 7** (for caching - optional)

**Verify Installations**

```bash
dotnet --version    # Should be 8.x.x
java --version      # Should be 17+
mvn --version       # Should be 3.6+
docker --version    # For containerized database
git --version
```

---

## Installation

### 1. Clone Repository

```bash
git clone https://github.com/MarioAJ11/Pland-IA.git
cd Pland-IA
```

### 2. Database Setup

**Option A: Docker (Recommended)**

```bash
# Start PostgreSQL and Redis
docker-compose up -d
```

**Option B: Local PostgreSQL**

```bash
# Install PostgreSQL 15 and create database
createdb plandia_db
```

### 3. Configure Environment Variables

**Auth Service**

```bash
cd apps/auth-service/AuthService
cp .env.example .env
```

Edit `.env` with your values:

```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=plandia_db
DB_USER=postgres
DB_PASSWORD=postgres123

# JWT Configuration
JWT_SECRET=your-very-secure-secret-key-minimum-32-characters
JWT_ISSUER=PlandIA.AuthService
JWT_AUDIENCE=PlandIA.Clients
JWT_EXPIRATION_MINUTES=60
JWT_REFRESH_EXPIRATION_DAYS=7

# Server
PORT=5001
ASPNETCORE_ENVIRONMENT=Development
```

**Core Service**

```bash
cd apps/core-service
cp .env.example .env
```

Edit `.env`:

```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=plandia_db
DB_USER=postgres
DB_PASSWORD=postgres123

# JWT (must match Auth Service)
JWT_SECRET=your-very-secure-secret-key-minimum-32-characters
JWT_ISSUER=PlandIA.AuthService
JWT_AUDIENCE=PlandIA.Clients

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:5173,http://localhost:3000

# Server
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=dev
```

### 4. Run Database Migrations

**Auth Service**

```bash
cd apps/auth-service/AuthService
dotnet ef database update
```

**Core Service**

Spring Boot will auto-create tables on first run (using `hibernate.ddl-auto=update`).

### 5. Start Services

**Terminal 1: Auth Service**

```bash
cd apps/auth-service/AuthService
dotnet run
```

Service will be available at `http://localhost:5001`

**Terminal 2: Core Service**

```bash
cd apps/core-service
./mvnw spring-boot:run
# or: mvn spring-boot:run
```

Service will be available at `http://localhost:8080`

### 6. Verify Installation

**Test Auth Service**

```bash
# Register new user
curl -X POST http://localhost:5001/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "SecurePass123!",
    "name": "Test User"
  }'

# Login
curl -X POST http://localhost:5001/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "SecurePass123!"
  }'
```

**Test Core Service (requires JWT from login)**

```bash
# Get workspaces (replace YOUR_TOKEN with access_token from login)
curl -X GET http://localhost:8080/api/workspaces \
  -H "Authorization: Bearer YOUR_TOKEN"
```

---

## Testing

### Test Summary

**Current Status: 34/34 tests passing**

| Service | Framework | Tests | Coverage |
|---------|-----------|-------|----------|
| Auth Service | xUnit + Moq | 10/10 ✅ | ~75% |
| Core Service | JUnit 5 + Mockito | 24/24 ✅ | ~75% |

### Running Tests

**Auth Service Tests**

```bash
cd apps/auth-service
dotnet test

# With detailed output
dotnet test --logger "console;verbosity=detailed"

# With coverage
dotnet test /p:CollectCoverage=true
```

**Test Coverage - Auth Service (10 tests)**

- User registration with token generation
- Duplicate email validation
- Login with valid credentials
- Invalid email/password handling
- JWT refresh token flow
- Token expiration validation
- Password hashing verification
- JWT claims structure validation
- Timestamp handling

**Core Service Tests**

```bash
cd apps/core-service
./mvnw test

# Or with Maven
mvn test

# Specific test class
mvn test -Dtest=JwtUtilTest
```

**Test Coverage - Core Service (24 tests)**

*JwtUtil (12 tests)*

- Token validation (valid, expired, invalid signature)
- Claims extraction (userId, email, name)
- Issuer and audience validation
- Malformed token handling
- Null/empty token handling

*WorkspaceService (11 tests)*

- CRUD operations with mocked repository
- User-specific workspace retrieval
- Exception handling for not found scenarios
- Field update verification
- Null input handling

---

## Project Structure

```
Pland-IA/
├── .github/
│   ├── copilot-instructions.md
│   ├── PROJECT_CONTEXT.md
│   └── ROADMAP.md
│
├── apps/
│   ├── auth-service/              # .NET 8 Authentication Service
│   │   ├── AuthService/
│   │   │   ├── Controllers/       # API endpoints
│   │   │   ├── Services/          # Business logic
│   │   │   ├── Models/
│   │   │   │   ├── Entities/      # Database entities
│   │   │   │   └── DTOs/          # Data transfer objects
│   │   │   ├── Data/              # DbContext
│   │   │   ├── Middleware/        # Request pipeline
│   │   │   ├── Migrations/        # EF migrations
│   │   │   ├── appsettings.json
│   │   │   └── Program.cs
│   │   └── AuthService.Tests/     # Unit tests (xUnit)
│   │
│   ├── core-service/              # Spring Boot Core Service
│   │   └── src/
│   │       ├── main/java/com/plandai/coreservice/
│   │       │   ├── controller/    # REST controllers
│   │       │   ├── service/       # Business logic
│   │       │   ├── repository/    # Data access
│   │       │   ├── model/
│   │       │   │   ├── entity/    # JPA entities
│   │       │   │   └── dto/       # DTOs
│   │       │   ├── config/        # Spring configuration
│   │       │   ├── security/      # JWT security
│   │       │   └── exception/     # Error handling
│   │       └── test/java/         # Unit tests (JUnit 5)
│   │
│   └── pantry-service/            # Python FastAPI Service (Planned)
│       └── app/
│           ├── main.py
│           ├── models/
│           ├── schemas/
│           ├── services/
│           └── routes/
│
├── database/
│   └── init-schemas.sql           # Database initialization
│
├── docker-compose.yml             # PostgreSQL + Redis
├── .gitignore
├── README.md
└── LICENSE
```

---

## Development Progress

### Completed Features

**Backend Foundation (Complete)**

- Multi-service microservices architecture
- JWT authentication with refresh tokens
- PostgreSQL multi-schema database
- Environment-based configuration
- Structured logging with rotation
- Flexible CORS configuration
- Comprehensive unit testing (34 tests)
- Docker-ready setup

**Auth Service (Complete)**

- User registration with validation
- Secure login with BCrypt
- JWT access and refresh tokens
- Token refresh endpoint
- User management CRUD
- Exception handling
- 10 passing unit tests

**Core Service (Complete)**

- Workspace CRUD operations
- JWT validation middleware
- User context extraction
- Repository pattern implementation
- Exception handling
- 24 passing unit tests

### In Progress

- README documentation update
- API documentation (Swagger/OpenAPI)

### Planned

- Pantry Service implementation (FastAPI)
- OpenAI integration for meal planning
- Frontend React application
- Mobile Flutter application
- Flyway database migrations
- Integration tests
- CI/CD pipeline
- Deployment configuration

### Development Stats

- **Lines of Code**: ~15,000+
- **Commits**: 8
- **Test Coverage**: ~75% (Auth & Core services)
- **Services**: 2/3 complete
- **API Endpoints**: 12 implemented

---

## Contributing

This is primarily a personal portfolio project, but suggestions and feedback are welcome. If you find any issues or have ideas for improvements, feel free to open an issue or reach out directly.

### Development Principles

- Clean, readable code over clever solutions
- Comprehensive testing for critical paths
- Proper error handling and validation
- Structured logging for debugging
- Environment-based configuration
- Security best practices
- Conventional commit messages

### Commit Convention

Following [Conventional Commits](https://www.conventionalcommits.org/):

- `feat:` New feature
- `fix:` Bug fix
- `docs:` Documentation changes
- `test:` Adding or updating tests
- `refactor:` Code refactoring
- `chore:` Maintenance tasks

---

## Contact

**Mario Alguacil**

- Email: [alguacilmario6@gmail.com](mailto:alguacilmario6@gmail.com)
- LinkedIn: [linkedin.com/in/marioaj11](https://www.linkedin.com/in/marioaj11)
- GitHub: [@MarioAJ11](https://github.com/MarioAJ11)

Feel free to reach out if you have questions about the project or want to discuss potential opportunities.

---

## License

This project is licensed under the MIT License - see the [LICENSE](./LICENSE) file for details.

---

**Note**: This is an active development project and a work in progress. The current focus is on building a solid backend foundation with proper architecture and testing before moving to frontend implementation.
