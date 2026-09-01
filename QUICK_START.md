# Food Adda - Quick Start Guide

## Project Overview

**Food Adda** is a complete restaurant management system built with Spring Boot and PostgreSQL. It includes:
- ✅ User authentication with JWT
- ✅ Menu and category management
- ✅ QR code generation for menu items
- ✅ Order management and billing
- ✅ Vegetarian/Non-Vegetarian classification
- ✅ Payment status tracking
- ✅ Role-based access control

---

## Step 1: Prerequisites

Make sure you have installed:

### Windows
```bash
# Java 21
java -version

# Maven
mvn -version

# PostgreSQL
# Download from: https://www.postgresql.org/download/windows/
```

### macOS
```bash
# Using Homebrew
brew install openjdk@21
brew install maven
brew install postgresql
```

### Linux (Ubuntu/Debian)
```bash
sudo apt update
sudo apt install openjdk-21-jdk maven postgresql postgresql-contrib
```

---

## Step 2: Database Setup

### Start PostgreSQL Service

**Windows (if not auto-starting):**
```bash
# Start PostgreSQL service
net start postgresql-15
```

**macOS:**
```bash
brew services start postgresql
```

**Linux:**
```bash
sudo service postgresql start
```

### Create Database

```bash
# Connect to PostgreSQL
psql -U postgres

# Or use password prompt
psql -U postgres -W

# In psql shell, create database
CREATE DATABASE food_adda_db;

# Exit psql
\q
```

### Run Database Initialization Script

```bash
# Navigate to project
cd food_adda

# Run SQL script
psql -U postgres -d food_adda_db -f src/main/resources/db/init.sql
```

Or if running from pgAdmin GUI:
1. Right-click on `food_adda_db` database
2. Open Query Tool
3. Open `src/main/resources/db/init.sql`
4. Execute

---

## Step 3: Configure Application

Edit `src/main/resources/application.properties`:

```properties
# Update database credentials
spring.datasource.url=jdbc:postgresql://localhost:5432/food_adda_db
spring.datasource.username=postgres
spring.datasource.password=your_postgres_password

# Update JWT secret (change in production!)
jwt.secret=MySuper$ecureJWTSecretKeyForProduction2024!

# Server port (default is fine)
server.port=8080
```

---

## Step 4: Build & Run Application

```bash
# Navigate to project directory
cd food_adda

# Clean and build
mvn clean install

# Run application
mvn spring-boot:run
```

**Wait for startup message:**
```
Started FoodAddaApplication in 5.234 seconds
```

**Application is ready at:** `http://localhost:8080/api`

---

## Step 5: Test the APIs

### Option A: Using cURL (Command Line)

```bash
# 1. Register a user
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test@123",
    "fullName": "Test User",
    "phone": "9876543210"
  }'

# 2. Login (save the token)
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "Test@123"
  }'

# 3. View all menu items (no auth required)
curl http://localhost:8080/api/menu/items
```

### Option B: Using Postman

1. Download [Postman](https://www.postman.com/downloads/)
2. Import collection from `API_TESTING_GUIDE.md` (or create endpoints manually)
3. Set base URL: `http://localhost:8080/api`
4. Add Authorization header: `Bearer {token}`

### Option C: Using VS Code REST Client Extension

1. Install "REST Client" extension
2. Create `test.http` file:

```http
### Register User
POST http://localhost:8080/api/users/register
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "Test@123",
  "fullName": "Test User",
  "phone": "9876543210"
}

### Login
POST http://localhost:8080/api/users/login
Content-Type: application/json

{
  "email": "test@example.com",
  "password": "Test@123"
}

### Get All Menu Items
GET http://localhost:8080/api/menu/items
```

3. Click "Send Request" above each endpoint

---

## Step 6: Common Issues & Solutions

### Issue 1: PostgreSQL Connection Error
```
ERROR: Connection refused
```

**Solution:**
```bash
# Check if PostgreSQL is running
psql -U postgres

# If not running:
# Windows: net start postgresql-15
# macOS: brew services start postgresql
# Linux: sudo service postgresql start

# Verify credentials in application.properties
```

### Issue 2: Port Already in Use
```
Address already in use: bind
```

**Solution:**
```bash
# Change port in application.properties
server.port=8081

# Or kill existing process
# Windows: netstat -ano | findstr :8080
#         taskkill /PID <PID> /F
# Linux/Mac: lsof -i :8080
#            kill -9 <PID>
```

### Issue 3: Database Not Found
```
FATAL: database "food_adda_db" does not exist
```

**Solution:**
```bash
# Recreate database
psql -U postgres -c "CREATE DATABASE food_adda_db;"
psql -U postgres -d food_adda_db -f src/main/resources/db/init.sql
```

### Issue 4: Maven Build Failure

**Solution:**
```bash
# Clear cache
mvn clean
rm -rf ~/.m2/repository/

# Try building again
mvn install
```

---

## Project Structure

```
food_adda/
├── src/
│   ├── main/
│   │   ├── java/com/food/food_adda/
│   │   │   ├── entity/          # Database models
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   ├── controller/      # REST endpoints
│   │   │   ├── dto/             # Data transfer objects
│   │   │   ├── security/        # JWT & Auth
│   │   │   ├── config/          # Spring config
│   │   │   ├── utility/         # Helper classes
│   │   │   └── FoodAddaApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/init.sql
│   └── test/
├── pom.xml
├── README.md
├── API_TESTING_GUIDE.md
└── QUICK_START.md (this file)
```

---

## Key API Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---|
| POST | `/users/register` | Register new user | No |
| POST | `/users/login` | Login user | No |
| POST | `/categories` | Create category | Yes |
| GET | `/categories` | Get all categories | No |
| POST | `/menu/items` | Create menu item | Yes |
| GET | `/menu/items` | Get all menu items | No |
| GET | `/menu/vegetarian` | Get veg items | No |
| GET | `/menu/non-vegetarian` | Get non-veg items | No |
| POST | `/orders/users/{userId}` | Create order | Yes |
| GET | `/orders/{orderId}` | Get order details | Yes |
| PUT | `/orders/{orderId}/status/{status}` | Update order status | Yes |

---

## Important Notes

### Security
- **JWT Tokens** expire in 24 hours (configurable in `application.properties`)
- **Passwords** are hashed with BCrypt
- **CORS** is enabled for all origins (change in production)
- **API Context** is `/api` (configurable)

### Database
- **Tables** are auto-created on first run (set by `spring.jpa.hibernate.ddl-auto=update`)
- **Indexes** are created for performance
- **5% Tax** is automatically calculated on orders

### Development Tips
- Enable SQL logging: Set `spring.jpa.show-sql=true` in properties
- Check logs in console for errors
- Use Postman or cURL for API testing
- Save JWT tokens in environment variables

---

## Next Steps

1. ✅ Setup database and run migrations
2. ✅ Build and start application
3. ✅ Test authentication endpoints
4. ✅ Create categories and menu items
5. ✅ Test order creation and management
6. ✅ Integrate with frontend (React, Vue, etc.)

---

## Production Deployment Checklist

- [ ] Change JWT secret to a strong random value
- [ ] Update database credentials
- [ ] Disable SQL logging (`spring.jpa.show-sql=false`)
- [ ] Set appropriate CORS origins
- [ ] Use environment variables for sensitive data
- [ ] Configure HTTPS/SSL certificates
- [ ] Set up database backups
- [ ] Configure application monitoring
- [ ] Load test the application
- [ ] Setup CI/CD pipeline

---

## Support & Documentation

- **Full API Docs:** See [README.md](README.md)
- **API Testing:** See [API_TESTING_GUIDE.md](API_TESTING_GUIDE.md)
- **Database Schema:** See [src/main/resources/db/init.sql](src/main/resources/db/init.sql)
- **Spring Boot Docs:** https://spring.io/projects/spring-boot
- **PostgreSQL Docs:** https://www.postgresql.org/docs/

---

**Happy Coding! 🍽️**

Last Updated: August 31, 2026
Version: 1.0.0
