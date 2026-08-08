# S2DCMS Backend

Spring Boot backend API for the Student to Department Complaint Management System.

## Related Repositories

- **Frontend**: [s2dcms-frontend](https://github.com/Doomd37/s2dcms-frontend) - React frontend with Vite and Tailwind CSS

## Overview

This backend provides REST APIs for student and department authentication, complaint management, file uploads, and messaging with JWT-based security, Redis caching, and RabbitMQ email processing.


# Features

## Authentication & Security
- **JWT Authentication System**: Secure token-based authentication with access and refresh tokens
- **Refresh Token Rotation**: Advanced token management with automatic rotation - maintains maximum 4 active sessions per user, automatically invalidating oldest tokens when new sessions are created
- **Automated Token Cleanup**: Scheduled daily cleanup of expired and revoked tokens to optimize database performance
- **Role-Based Authorization**: Granular access control with STUDENT, DEPARTMENT, and ADMIN roles
- **Secure Password Hashing**: BCrypt encryption for secure password storage
- **Rate Limiting Protection**: Brute-force attack prevention with configurable attempt limits

## Student Features
- Register & Login
- Update Profile
- Upload Profile Picture
- Submit Complaints
- Upload Complaint Attachments
- View Complaint Status
- Messaging System

## Department Features
- View Student Complaints
- Open Complaint Details
- Reply to Students
- Track Seen/Unread Messages

## File Upload System
- Profile Image Upload
- Complaint Attachments
- File Validation
- File Size Restrictions
- MIME Type Validation

## Performance & Optimization
- **Redis Caching**: High-performance caching for frequently accessed data
- **Database Indexing**: Optimized database queries with strategic indexing on frequently accessed columns
- **Pagination**: Efficient data retrieval with server-side pagination
- **Lazy Loading**: Optimized entity loading to reduce database queries
- **DTO-based Responses**: Clean separation between internal models and API contracts

## Database & Migration
- PostgreSQL
- Flyway Migration
- JPA / Hibernate

---

# Tech Stack

- **Java 17+** - Programming language
- **Spring Boot 3.x** - Application framework
- **Spring Security** - Security framework
- **JWT (jjwt)** - Token-based authentication
- **Spring Data JPA** - Database ORM
- **Hibernate** - JPA implementation
- **PostgreSQL** - Primary database
- **Redis** - Caching and session storage
- **RabbitMQ** - Message queue for email processing
- **Flyway** - Database migration
- **Brevo API** - Email service
- **Maven** - Build tool


# Project Status

This project is currently under active development.

Completed:
- Authentication System
- Role-Based Access Control
- Complaint Management
- File Uploads
- Redis Caching
- Profile Management
- Department Messaging


# Setup Instructions

## Clone Repository

```bash
git clone https://github.com/Doomd37/s2dcms-backend.git
cd s2dcms-backend
```

---

## Configure Environment

Create:

```text
src/main/resources/application.properties
```

Copy values from:

```text
application-example.properties
```

And replace placeholders with your real credentials.


## Run PostgreSQL

Ensure PostgreSQL is running and create your database.


## Run Redis

Ensure Redis is running on:

```text
localhost:6379
```

Install Redis:
- Windows: Download from [Redis official site](https://redis.io/download)
- Mac: `brew install redis`
- Linux: `sudo apt-get install redis-server`

## Run RabbitMQ

Ensure RabbitMQ is running for email processing:
- Windows: Download from [RabbitMQ official site](https://www.rabbitmq.com/download.html)
- Mac: `brew install rabbitmq`
- Linux: `sudo apt-get install rabbitmq-server`

Default RabbitMQ URL: `amqp://guest:guest@localhost:5672`


## Run Application

```bash
./mvnw spring-boot:run
```


# API Security

This project uses JWT-based authentication.

Protected endpoints require:

```text
Authorization: Bearer <token>
```


# File Uploads

Supported file types:
- PNG
- JPG
- JPEG
- PDF
- DOC
- DOCX

Maximum upload size:
- 20MB


# Architecture Highlights

- **Layered Architecture**: Controller → Service → Repository pattern for clean separation of concerns
- **DTO-based API responses**: Clean separation between internal models and API contracts
- **Service-oriented design**: Business logic encapsulated in service layer
- **Redis caching strategy**: Token storage and session management for improved performance
- **Secure file handling**: Validation, size limits, and secure storage
- **Role-based endpoint protection**: Spring Security with custom JWT authentication filters
- **Async email processing**: RabbitMQ message queue for email operations
- **Database migrations**: Flyway for version-controlled schema changes
- **Advanced token management**: Refresh token rotation with session limits and automated cleanup
- **Database optimization**: Strategic indexing on frequently accessed columns for query performance


# Author

Developed by Eze Emmanuel