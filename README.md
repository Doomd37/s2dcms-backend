# S2DCMS - Student Department Complaint Management System

S2DCMS is a full-stack complaint management platform that allows students to send complaints to their department through a secure complaint system.

The system supports authentication, complaint tracking, role-based access control, file uploads, caching, and department/student communication.


# Features

## Authentication & Security
- JWT Authentication
- Access & Refresh Tokens
- Role-Based Authorization
- Secure Password Hashing
- Rate Limiting Protection

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
- Redis Caching
- Pagination
- Lazy Loading
- DTO-based Responses

## Database & Migration
- PostgreSQL
- Flyway Migration
- JPA / Hibernate

---

# Tech Stack

## Backend
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Redis
- Flyway
- PostgreSQL

## Frontend
- React
- Tailwind CSS
- Axios


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
git clone YOUR_REPOSITORY_LINK
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

- DTO-based API responses
- Layered architecture
- Service-oriented design
- Redis caching strategy
- Secure file handling
- Role-based endpoint protection


# Author

Developed by Eze Emmanuel