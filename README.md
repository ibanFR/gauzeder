# Gauzeder

> A reference implementation of **DDD + Jakarta EE 10 + React 19** — clean architecture from browser to database.

---

## Table of Contents

1. [Project Structure](#project-structure)
2. [Prerequisites](#prerequisites)
3. [Quick Start (Docker)](#quick-start-docker)
4. [Local Development (without Docker)](#local-development-without-docker)
5. [Hello World Test](#hello-world-test)
6. [Architecture Notes](#architecture-notes)

---

## Project Structure

```
gauzeder/
├── frontend/              # React 19 + Vite SPA
│   ├── src/
│   │   ├── App.jsx        # Main component — fetches greeting from backend
│   │   └── main.jsx       # React entry point
│   ├── vite.config.js     # Dev proxy: /api → http://localhost:8080
│   └── package.json
│
├── backend/               # Jakarta EE 10 multi-module Maven project
│   ├── domain/            # Pure Java — zero framework dependencies
│   │   └── src/main/java/com/ibanfr/gauzeder/domain/
│   │       ├── model/         HelloMessage (value object record)
│   │       ├── repository/    HelloRepository (port interface)
│   │       └── event/         DomainEvent, HelloRequestedEvent
│   │
│   ├── application/       # Use cases / CDI orchestration
│   │   └── src/main/java/com/ibanfr/gauzeder/application/
│   │       └── GetHelloUseCase.java
│   │
│   ├── infrastructure/    # JPA, JAX-RS, WildFly WAR
│   │   └── src/main/java/com/ibanfr/gauzeder/infrastructure/
│   │       ├── persistence/   HelloJpaEntity, JpaHelloRepository
│   │       └── rest/          HelloResource, HelloResponse, JaxRsApplication, CorsFilter
│   │
│   ├── wildfly-config/    configure-datasource.cli
│   └── pom.xml            # Backend parent POM
│
├── docker-compose.yml     # PostgreSQL + WildFly
└── README.md
```

### DDD Layering

| Layer | Package | Rule |
|---|---|---|
| **Domain** | `domain/` | Zero Jakarta EE / JPA / CDI compile deps. Pure Java records and interfaces. |
| **Application** | `application/` | CDI beans (`@ApplicationScoped`). Orchestrates domain objects. No HTTP/JPA knowledge. |
| **Infrastructure** | `infrastructure/` | JPA entities, repository impls, JAX-RS resources. Framework-aware. |

---

## Prerequisites

| Tool | Version |
|---|---|
| Java | 17+ |
| Maven | 3.9+ |
| Node.js | 20+ |
| Docker + Docker Compose | Latest stable |

---

## Quick Start (Docker)

```bash
# 1. Build the backend WAR
cd backend
mvn clean package -DskipTests
cd ..

# 2. (Optional) Build the frontend for production
cd frontend
npm install
npm run build
cd ..

# 3. Start all services
docker-compose up
```

The application will be available at:
- **Backend API**: `http://localhost:8080/gauzeder/api/hello`
- **WildFly Management Console**: `http://localhost:9990`
- **PostgreSQL**: `localhost:5432` (DB: `gauzeder`, user: `gauzeder`, password: `gauzeder`)

---

## Local Development (without Docker)

### 1. Start PostgreSQL locally

Using Docker for just the database:

```bash
docker run -d \
  --name gauzeder-postgres \
  -e POSTGRES_DB=gauzeder \
  -e POSTGRES_USER=gauzeder \
  -e POSTGRES_PASSWORD=gauzeder \
  -p 5432:5432 \
  postgres:16
```

### 2. Configure and start WildFly

Download [WildFly 33](https://www.wildfly.org/downloads/) and set `JBOSS_HOME`.

```bash
# Register the PostgreSQL datasource
$JBOSS_HOME/bin/jboss-cli.sh --file=backend/wildfly-config/configure-datasource.cli

# Start the server
$JBOSS_HOME/bin/standalone.sh

# Deploy the WAR (in a separate terminal after building)
cd backend && mvn clean package -DskipTests
$JBOSS_HOME/bin/jboss-cli.sh --connect \
  --command="deploy backend/infrastructure/target/gauzeder.war --force"
```

### 3. Start the React dev server

```bash
cd frontend
npm install
npm run dev
```

The Vite dev server starts at `http://localhost:5173` and proxies `/api` requests to WildFly at `http://localhost:8080`.

---

## Hello World Test

### End-to-End Flow

```
Browser (React)
  → GET /api/hello                      (Vite proxy → WildFly)
  → HelloResource.getHello()            (JAX-RS)
  → GetHelloUseCase.execute()           (CDI @ApplicationScoped)
  → JpaHelloRepository.findGreeting()   (JPA @PersistenceContext)
  → SELECT * FROM hello_message         (PostgreSQL 16)
  ← HelloMessage("Hello, World!")       (domain value object)
  ← HelloResponse("Hello, World!")      (DTO)
  ← {"message":"Hello, World!"}         (JSON)
```

### cURL

```bash
curl http://localhost:8080/gauzeder/api/hello
```

Expected response:

```json
{"message":"Hello, World!"}
```

### Browser

Open `http://localhost:5173` — you should see a centred card with the greeting from the database.

---

## Architecture Notes

### Why domain has no framework dependencies

The `domain` module's `pom.xml` intentionally declares **zero** Jakarta EE, JPA, or CDI compile-time dependencies. This enforces the Dependency Rule of Clean Architecture: outer layers depend on inner layers, never the reverse. Domain objects are plain Java records/classes that can be unit-tested without a container.

### How domain events flow via CDI

1. `GetHelloUseCase.execute()` calls the repository, retrieves the greeting.
2. It fires a `HelloRequestedEvent` via CDI `Event<HelloRequestedEvent>.fire(...)`.
3. Any `@Observes HelloRequestedEvent` observer (e.g. for audit logging) is called synchronously within the same transaction.
4. Events are fired **after** the business operation succeeds — never before.

### How to add a new feature (DDD pattern)

1. **Domain**: Add a value object or entity in `domain/model/`, a repository interface in `domain/repository/`, and optionally a domain event in `domain/event/`.
2. **Application**: Add a new `@ApplicationScoped` use case class in `application/` — one class per user action.
3. **Infrastructure**: Add a JPA entity in `infrastructure/persistence/`, implement the repository interface, and expose a JAX-RS endpoint in `infrastructure/rest/`.

Rule of thumb: **never** put business logic in JAX-RS resources or JPA entities.
