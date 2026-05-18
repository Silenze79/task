# Campaign Notification System

A Spring Boot backend application for managing notification campaigns using CSV uploads.  
The system supports campaign creation, tenant importing, notification tracking, and retrying failed notifications.

---

# Features

- Create notification campaigns
- Upload tenant data using CSV files
- Support for multiple notification channels:
  - EMAIL
  - SMS
  - PUSH_NOTIFICATION
- Track notification status:
  - PENDING
  - PROCESSING
  - COMPLETED
  - FAILED
- Retry failed notifications
- REST API endpoints
- JPA + Hibernate persistence

---

# Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- Lombok
- Maven
- MySQL / PostgreSQL (configurable)

---

# Project Structure

```text
src/main/java/com/example/task/task
│
├── controller
├── service
├── repository
├── entity
├── dto
└── config
```

---

# Requirements

Before running the project, install:

- Java 17 or newer
- Maven
- MySQL or PostgreSQL
- IntelliJ IDEA (recommended)

---

# Setup Instructions

## 1. Clone Repository

```bash
git clone <repository-url>
cd task
```

---

## 2. Configure Database

Open:

```text
src/main/resources/application.properties
```

Example configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/campaign_db
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Create the database manually:

```sql
CREATE DATABASE campaign_db;
```

---

## 3. Install Lombok Plugin

If using IntelliJ IDEA:

### Install Plugin

```text
File → Settings → Plugins
Search: Lombok
Install
```

### Enable Annotation Processing

```text
File
→ Settings
→ Build, Execution, Deployment
→ Compiler
→ Annotation Processors
```

Enable:

```text
✓ Enable annotation processing
```

Restart IntelliJ after enabling.

---

# Run Application

## Using Maven

```bash
mvn spring-boot:run
```

---

## Using IntelliJ

Run:

```text
TaskApplication.java
```

---

# API Endpoints

## Create Campaign

```http
POST /campaigns
```

### Form Data

| Key     | Type | Description |
|---------|------|-------------|
| name    | Text | Campaign name |
| channel | Text | EMAIL / SMS / PUSH_NOTIFICATION |
| file    | File | CSV file |

---

## Get All Campaigns

```http
GET /campaigns
```

---

## Get Campaign By ID

```http
GET /campaigns/{campaignId}
```

---

## Retry Failed Notifications

```http
POST /campaigns/{campaignId}/retry
```

---

# CSV Format

Example CSV:

```csv
name,email,phone
John Doe,john@gmail.com,0123456789
Jane Smith,jane@gmail.com,0199999999
```

---

# Campaign Status Flow

```text
PENDING
   ↓
PROCESSING
   ↓
COMPLETED
```

If notification delivery fails:

```text
FAILED
```

Retry operation resets failed tenants back to:

```text
PENDING
```

---

# Example JSON Response

```json
{
  "campaignId": "8d4d6f8d-12aa-4f87-9d44-123456789abc",
  "name": "Promo Campaign",
  "status": "PENDING",
  "channel": "EMAIL",
  "createdAt": "2026-05-18T20:30:00"
}
```

---

# Common Issues

## Lombok Getter/Setter Errors

If methods like:

```text
getCampaignId()
setName()
```

appear red:

- Install Lombok plugin
- Enable annotation processing
- Rebuild project

---

## Database Connection Error

Check:

- Database is running
- Username/password are correct
- Database exists

---

# Future Improvements

- Async notification processing
- Email/SMS provider integration
- Authentication & authorization
- Frontend dashboard
- Notification scheduling
- Retry queue system

---

# Author

Marcus
