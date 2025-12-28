# Setup and Run Instructions

## Extract
Unzip the project archive and enter the directory.

## Database
Ensure PostgreSQL is running.
Create the database:
CREATE DATABASE bookstore_db;

## Configuration
Check src/main/resources/application.properties.
Make sure the username and password match your local PostgreSQL installation.

## Build and Run
./gradlew clean build
./gradlew bootRun

## Testing
To run unit tests:
./gradlew test

To test the API manually:
1. Open Postman.
2. Import Bookstore_API.postman_collection.json.
3. Use the Login endpoint to get a token.
4. Paste the token into the Authorization tab for other requests.

## Credentials
Use these accounts to test different roles:

1. Admin
   email: admin@bookstore.com
   pass: admin123

2. Manager
   email: manager@bookstore.com
   pass: manager123

3. User
   email: user@bookstore.com
   pass: user123

## Troubleshooting
- If port 8089 is in use, change the server.port in application.properties.
- If the database connection fails, check if the PostgreSQL service is running and the credentials are correct.