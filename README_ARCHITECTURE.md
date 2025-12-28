# Architecture Documentation

## Overview
The application uses a standard 3-tier architecture.

1. Presentation Layer (Controllers)
   Handles HTTP requests and maps JSON to DTOs.

2. Business Logic Layer (Services)
   Contains all business rules, validations, and transaction management.

3. Data Access Layer (Repositories)
   Interfaces with PostgreSQL using Spring Data JPA.

## Request Flow
When a request comes in (e.g., Create Order):
1. Security Filter checks the JWT token.
2. Controller receives the DTO.
3. Service validates the data (stock check, user check).
4. Service calls Repository to save data.
5. Entity is converted back to a Response DTO.
6. Controller returns the DTO to the client.

## Security Architecture
We use Spring Security with JWT (JSON Web Tokens).

Flow:
1. User logs in with email/password.
2. Server validates and returns a token.
3. Client sends this token in the Authorization header for future requests.
4. Server validates the token on every request to determine user identity and role.

Permissions:
- Public: Login, Register, View Books
- User: Create Orders, View Own Orders
- Manager: Manage Inventory (Books/Authors), View All Orders
- Admin: Manage Users, Full Access

## Data Migration
Liquibase is used for version control of the database schema.
Files are located in src/main/resources/db/changelog.

Order of execution:
1. Create Users
2. Create Categories/Authors
3. Create Books
4. Create Orders/Items
5. Insert Sample Data

## Testing Strategy
Unit tests focus on the Service layer.
- Mockito is used to mock Repositories.
- Tests cover success scenarios, error handling, and business logic (like stock calculation).