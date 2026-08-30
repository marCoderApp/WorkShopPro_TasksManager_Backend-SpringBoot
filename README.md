# WorkShopPro TasksManager — Backend

REST API for the internal management of a technical service workshop.
Allows managing users, assigning tasks to technicians, and tracking the status of each work order.

---

## Tech Stack

- Java 21
- Spring Boot 4.0.6
- Spring Security + JWT (jjwt 0.11.5)
- PostgreSQL 16
- Hibernate / JPA
- Lombok
- Swagger / OpenAPI 3 (springdoc 3.0.2)

---

## Prerequisites

- JDK 21
- Maven 3.9+
- PostgreSQL running on localhost

---

## Database Setup

Create the database in PostgreSQL:

```sql
CREATE DATABASE tallerpro_db;
```

---

## Configuration

Edit src/main/resources/application.properties:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5434/tallerpro_db
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# JWT
jwt.secret=YourBase64SecretKeyVeryLongAndSecure1234567890123456789012
jwt.expiration=86400000
```

---

## Running the App

```bash
mvn spring-boot:run
```

The app starts at http://localhost:8080

On first startup, the DataSeeder automatically creates the initial users if they don't exist yet.

---

## Swagger Documentation

http://localhost:8080/swagger-ui/index.html

How to test protected endpoints:
1. Call POST /api/auth/login with your credentials
2. Copy the token from the response
3. Click the Authorize button (top right)
4. Enter: Bearer your_token
5. Click Authorize then Close
6. All endpoints will now use that token automatically

---

## Default Credentials (loaded by DataSeeder)

| Role        | Email                 | Password  |
|-------------|-----------------------|-----------|
| SUPER_ADMIN | superadmin@taller.com | Admin123! |
| ADMIN       | admin@taller.com      | Admin123! |
| TECNICO     | carlos@taller.com     | Admin123! |
| TECNICO     | maria@taller.com      | Admin123! |

Note: There is no public registration endpoint.
Users are created by ADMIN or SUPER_ADMIN through the /api/usuarios endpoint.

---

## Role Permissions

| Action               | TECNICO | ADMIN | SUPER_ADMIN |
|----------------------|---------|-------|-------------|
| Login                | YES     | YES   | YES         |
| View own tasks       | YES     | YES   | YES         |
| View all tasks       | NO      | YES   | YES         |
| Create tasks         | NO      | YES   | YES         |
| Assign tasks         | NO      | YES   | YES         |
| Change task status   | YES     | NO    | NO          |
| Add comments         | YES     | YES   | YES         |
| Create TECNICO users | NO      | YES   | YES         |
| Create ADMIN users   | NO      | NO    | YES         |
| Create SUPER_ADMIN   | NO      | NO    | NO          |
| Manage all users     | NO      | NO    | YES         |

---

## API Endpoints

### Auth (public)

| Method | Endpoint        | Description               |
|--------|-----------------|---------------------------|
| POST   | /api/auth/login | Login — returns JWT token |

### Users

| Method | Endpoint               | Required Role       | Description       |
|--------|------------------------|---------------------|-------------------|
| GET    | /api/usuarios          | SUPER_ADMIN         | List all users    |
| POST   | /api/usuarios          | ADMIN / SUPER_ADMIN | Create new user   |
| GET    | /api/usuarios/{id}     | ADMIN / SUPER_ADMIN | Get user by ID    |
| PUT    | /api/usuarios/{id}/rol | SUPER_ADMIN         | Edit user role    |
| DELETE | /api/usuarios/{id}     | SUPER_ADMIN         | Delete user       |
| GET    | /api/usuarios/perfil   | Authenticated       | View own profile  |
| PUT    | /api/usuarios/perfil   | Authenticated       | Edit own profile  |

### Tasks

| Method | Endpoint                     | Required Role       | Description               |
|--------|------------------------------|---------------------|---------------------------|
| GET    | /api/tareas                  | ADMIN / SUPER_ADMIN | List all tasks            |
| GET    | /api/tareas/mis-tareas       | TECNICO             | List own assigned tasks   |
| GET    | /api/tareas/{id}             | Authenticated       | Get task by ID            |
| POST   | /api/tareas                  | ADMIN / SUPER_ADMIN | Create new task           |
| PUT    | /api/tareas/{id}/asignar     | ADMIN / SUPER_ADMIN | Assign task to technician |
| PATCH  | /api/tareas/{id}/estado      | TECNICO             | Change task status        |
| POST   | /api/tareas/{id}/comentarios | Authenticated       | Add comment to task       |
| PATCH  | /api/tareas/{id}/prioridad   | ADMIN / SUPER_ADMIN | Change task priority      |

---

## Task Status Flow

PENDIENTE -> ASIGNADA -> EN_PROGRESO -> COMPLETADA -> CERRADA
                              |
                          EN_ESPERA

Any status -> CANCELADA (ADMIN / SUPER_ADMIN only)

---

## Security Features

- Passwords hashed with BCrypt (cost factor 10)
- Stateless authentication with JWT (24h expiration)
- Account lockout after 5 failed login attempts (15 minutes)
- Role-based access control on every endpoint
- Generic error messages on failed login (no user enumeration)
- No public registration endpoint — all users created by administrators

---

## Project Structure

src/main/java/com/equipo/tallerproapp/
├── config/         -> SecurityConfig, JwtAuthFilter, SwaggerConfig, DataSeeder
├── model/          -> User, Task, Comment, Role (enum), EstadoTask (enum)
├── dto/            -> All request and response DTOs
├── repository/     -> UserRepository, TaskRepository
├── service/        -> AuthService, JwtService, UsuarioService, TaskService
├── controller/     -> AuthController, UsuarioController, TaskController
└── exception/      -> GlobalExceptionHandler

---

## Error Response Format

All errors return a consistent JSON response:

{
  "status": 400,
  "mensaje": "El email ya esta registrado",
  "timestamp": "2025-01-15T10:30:00"
}

| Status | Meaning                        |
|--------|--------------------------------|
| 400    | Bad request / validation error |
| 401    | Unauthorized / invalid token   |
| 403    | Forbidden / insufficient role  |
| 404    | Resource not found             |
| 500    | Internal server error          |
