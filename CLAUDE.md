# CLAUDE.md

## Project Overview

This project is a backend application built with Java 22, Spring Boot, Spring Security, Spring Data JPA, Hibernate, and MySQL.

The application follows a clean and maintainable architecture, with a clear separation between controllers, services, repositories, entities, DTOs, and security concerns.

## Development Guidelines

- Follow existing project conventions before introducing new patterns.
- Keep the code simple, readable, and maintainable.
- Prefer small, focused classes and methods.
- Avoid unnecessary abstractions and over-engineering.
- Reuse existing utilities, services, and components when possible.
- Do not introduce new dependencies unless they are necessary.
- Keep business logic out of controllers.
- Keep database access inside repositories or dedicated persistence components.
- Use DTOs for API request and response models instead of exposing entities directly.
- Validate incoming request data using Jakarta Bean Validation.
- Handle errors consistently using appropriate HTTP status codes and a global exception handler.

## Architecture

Follow this general structure:

- `controller/` — REST API endpoints
- `service/` — business logic
- `repository/` — database access
- `entity/` — JPA entities
- `dto/` — request and response objects
- `config/` — application configuration
- `security/` — Spring Security and JWT configuration
- `exception/` — custom exceptions and global exception handling
- `mapper/` — entity/DTO mapping when required

Do not bypass the service layer from controllers unless there is a clear reason.

## Database

- Use Spring Data JPA for database access.
- Use Hibernate as the JPA implementation.
- Use MySQL as the primary database.
- Use Flyway for database migrations.
- Never modify the database schema manually when a migration should be created.
- Avoid N+1 queries.
- Use appropriate indexes for frequently queried columns.
- Use transactions explicitly where required.
- Be careful with lazy/eager relationships and cascading operations.

## Security

- Use Spring Security for authentication and authorization.
- Use JWT for stateless authentication.
- Never hardcode passwords, secrets, JWT keys, or credentials.
- Store sensitive configuration in environment variables or secure configuration.
- Passwords must always be securely hashed.
- Validate and authorize authenticated users before accessing protected resources.
- Do not expose sensitive information in API responses or logs.
- Follow the principle of least privilege.

## API Guidelines

- Build RESTful APIs using Spring Web.
- Use appropriate HTTP methods and status codes.
- Keep endpoint naming consistent.
- Validate request bodies and parameters.
- Return consistent response structures.
- Document public endpoints using OpenAPI/Swagger when appropriate.
- Do not expose internal implementation details through the API.

## Testing

Every meaningful feature or change should include appropriate tests.

- Use JUnit 5 for tests.
- Use Mockito for unit testing when appropriate.
- Use Spring Boot Test for integration testing.
- Use Testcontainers for database integration tests when required.
- Test business logic independently from controllers.
- Test authentication and authorization for protected endpoints.
- Do not remove or weaken existing tests just to make the build pass.

Before considering a change complete, run:

```bash
./mvnw test
./mvnw clean package
