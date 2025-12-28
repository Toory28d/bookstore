# Online Bookstore REST API

## Project Overview
This is a REST API for an online bookstore built using Spring Boot. It handles user authentication, role-based access control, and CRUD operations for books, authors, categories, and orders.

Developer: [Your Name]

## System Architecture

### Database Entities
The application uses PostgreSQL with these entities:
- Users: Customer and admin accounts
- Authors: Book authors
- Categories: Book genres/categories
- Books: Inventory
- Orders: Purchase records
- OrderItems: Items inside an order

### Relationships
- One User has many Orders
- One Order has many OrderItems
- One Book has one Author
- One Book has one Category
- OrderItem links to one Book

## Technical Stack
- Java 17
- Spring Boot 3.2.0
- Spring Security (JWT)
- Spring Data JPA (Hibernate)
- PostgreSQL
- Liquibase
- MapStruct
- Gradle

## Prerequisites
- Java 17+
- PostgreSQL 12+
- Gradle 7.x+

## Installation & Setup

1. Clone the Repository
   git clone <repository-url>
   cd bookstore-project

2. Configure Database
   Create a database named bookstore_db in PostgreSQL.
   Update src/main/resources/application.yml with your username and password.

3. Build
   ./gradlew clean build

4. Run
   ./gradlew bootRun

The app runs on http://localhost:8089. Liquibase will create tables automatically.

## Default Users

Admin
Email: admin@bookstore.com
Password: admin123

Manager
Email: manager@bookstore.com
Password: manager123

User
Email: user@bookstore.com
Password: user123

## Security Roles

ADMIN
- Full system access
- Manage users (block, delete)
- Manage all data

MANAGER
- Manage books, authors, categories
- Process orders

USER
- View books
- Place orders
- Manage own profile

## API Endpoints

Authentication
- POST /api/auth/register
- POST /api/auth/login

Users (Admin only)
- GET /api/users
- POST /api/users
- PUT /api/users/{id}/block

Content (Books, Authors, Categories)
- GET endpoints are public
- POST/PUT/DELETE require Admin or Manager role

Orders
- POST /api/orders (User)
- GET /api/orders (Admin/Manager)
- GET /api/orders/my-orders (User)

## Testing
Run unit tests with:
./gradlew test

## Postman
A Postman collection is included in the root directory (Bookstore_API.postman_collection.json). Import it to test the API.

## Features
- Soft Delete implemented for all entities
- DTO pattern used for all requests/responses
- Stock management logic (reduces quantity on order)
- Global exception handling

## License
Educational project.