# AI GlossApp

An AI-powered language glossary app to help with language learning.

## Tech Stack

**Backend**
- Java 26 · Spring Boot 3.5 · Spring AI 1.1
- PostgreSQL + pgvector (vector store for AI embeddings)
- Flyway (database migrations)
- OAuth2 Resource Server (Auth0)

**Frontend**
- React 19 · TypeScript 5 · Vite 8
- MUI 9
- TanStack Router & Query
- Auth0

## Prerequisites

- Java 26
- Node.js + npm
- Docker & Docker Compose
- A [Google Gemini API key](https://aistudio.google.com/app/apikey)
- An [Auth0](https://auth0.com/) tenant configured as both an Authorization Server and a Resource Server

## Running Locally

### 1. Start infrastructure
```
bash
docker compose up -d
```
This starts a PostgreSQL instance with pgvector and pgAdmin (available at [http://localhost:8543](http://localhost:8543), credentials: `admin@admin.com` / `admin`).

### 2. Configure the backend

Edit `src/main/resources/application.properties` with valid values for your Auth0 resource server and database connection, then set the Gemini API key:
```
export GEMINI_API_KEY=your_api_key_here
```
### 3. Run the backend
```
./mvnw spring-boot:run
```
Or run `GlossappApplication` directly from your IDE.

### 4. Configure and run the frontend

Edit `web/.env` with your Auth0 settings, then:
```
cd web
pnpm install
pnpm run dev
```
The app will be available at [http://localhost:5173](http://localhost:5173).

### Notes
- Spring AI supports only Spring Boot 3.4.x and 3.5.x.

