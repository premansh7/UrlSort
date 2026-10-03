# URLSort

**URLSort** is a URL-shortening service built with Spring Boot and PostgreSQL.

It converts long URLs into short, randomly generated 7-character codes and redirects users from the short URL to the original destination.

The project includes a responsive black-and-pink frontend, REST APIs, URL statistics, validation, centralized exception handling, and Swagger/OpenAPI documentation.

## Features

- Generate unique 7-character short codes
- Redirect short URLs to original URLs
- Track click counts
- View URL statistics
- Validate submitted URLs
- REST API
- Swagger/OpenAPI documentation
- PostgreSQL persistence
- Responsive frontend
- Copy-to-clipboard support
- Centralized exception handling

## How It Works

```text
User enters long URL
        │
        ▼
Frontend sends POST /api/shorten
        │
        ▼
Spring Boot Controller
        │
        ▼
UrlShortenerService
        │
        ├── Generate unique 7-character code
        │
        ▼
PostgreSQL
        │
        ▼
Short URL returned
        │
        ▼
GET /{shortCode}
        │
        ▼
Original URL + click count update
```

## Tech Stack

- **Java 25**
- **Spring Boot 4.1.1**
- Spring Web MVC
- Spring Data JPA
- Spring Validation
- PostgreSQL
- Lombok
- Springdoc OpenAPI / Swagger
- Maven
- HTML
- CSS
- JavaScript

## Project Structure

```text
urlsort/
├── src/
│   ├── main/
│   │   ├── java/com/example/urlsort/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── exception/
│   │   │   ├── model/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── UrlsortApplication.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   ├── script.js
│   │       │   └── style.css
│   │       └── application.properties
│   └── test/
├── .env.example
├── .gitignore
├── LICENSE
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Prerequisites

- Java 25+
- PostgreSQL 14+
- Git

Check Java:

```bash
java -version
```

## Database Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE urlshortner;
```

The application expects PostgreSQL on:

```text
localhost:5432
```

You can change the database connection using environment variables.

## Configuration

Set:

```text
DB_URL=jdbc:postgresql://localhost:5432/urlshortner
DB_USERNAME=postgres
DB_PASSWORD=your_password
APP_BASE_URL=http://localhost:8080
```

For Windows PowerShell:

```powershell
$env:DB_URL="jdbc:postgresql://localhost:5432/urlshortner"
$env:DB_USERNAME="postgres"
$env:DB_PASSWORD="your_password"
$env:APP_BASE_URL="http://localhost:8080"
```

For macOS/Linux:

```bash
export DB_URL="jdbc:postgresql://localhost:5432/urlshortner"
export DB_USERNAME="postgres"
export DB_PASSWORD="your_password"
export APP_BASE_URL="http://localhost:8080"
```

> Do not commit `.env` files or real database passwords to GitHub.

## Run the Application

Using the Maven wrapper:

### Windows

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

### macOS/Linux

```bash
./mvnw clean test
./mvnw spring-boot:run
```

Or, if Maven is installed:

```bash
mvn clean test
mvn spring-boot:run
```

Open:

```text
http://localhost:8080
```

## API Reference

### Create Short URL

```http
POST /api/shorten
Content-Type: application/json
```

Request:

```json
{
  "originalUrl": "https://example.com/very/long/url"
}
```

Response:

```json
{
  "shortCode": "aB3xY91",
  "shortUrl": "http://localhost:8080/aB3xY91",
  "originalUrl": "https://example.com/very/long/url"
}
```

### Redirect

```http
GET /{shortCode}
```

Example:

```text
http://localhost:8080/aB3xY91
```

The server responds with a redirect to the original URL and increments the click count.

### Statistics

```http
GET /api/stats/{shortCode}
```

Example response:

```json
{
  "id": 1,
  "shortCode": "aB3xY91",
  "originalUrl": "https://example.com/very/long/url",
  "createdAt": "2026-10-03T13:00:00",
  "clickCount": 4
}
```

## Swagger / OpenAPI

Once the application is running, Swagger UI is available at:

```text
http://localhost:8080/swagger-ui.html
```

OpenAPI documentation is also available through the generated OpenAPI endpoint.

## URL Code Generation

URLSort generates a random **7-character alphanumeric code** using `SecureRandom`.

The available alphabet contains:

```text
a-z
A-Z
0-9
```

The service checks the database for collisions before saving the generated code.

## Validation and Error Handling

The API validates that submitted URLs:

- Are not blank
- Start with `http://` or `https://`

The global exception handler returns appropriate `400` and `404` responses for invalid requests and missing short codes.

## Testing

Run:

```bash
./mvnw test
```

or on Windows:

```powershell
.\mvnw.cmd test
```

## Limitations

- No user accounts or authentication
- No custom aliases
- No URL expiration
- No rate limiting
- Click count is a simple aggregate counter
- PostgreSQL is currently required for persistence
- Short-code generation is random rather than hash-based

## Future Improvements

- User authentication with Spring Security + JWT
- Redis caching
- Rate limiting
- Custom aliases
- URL expiration
- QR-code generation
- Advanced analytics
- Per-user URL management
- Docker deployment
- CI/CD with GitHub Actions
- Production database configuration
- Link safety/abuse detection

## Portfolio Highlights

This project demonstrates:

- Spring Boot REST API design
- Layered architecture
- JPA/Hibernate
- PostgreSQL integration
- DTO-based API design
- Input validation
- Exception handling
- Random unique identifier generation
- HTTP redirects
- Click tracking
- OpenAPI/Swagger
- Frontend-backend integration

## License

This project is licensed under the MIT License. See [`LICENSE`](LICENSE).
