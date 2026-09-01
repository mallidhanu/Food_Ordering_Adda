# Food Adda - Restaurant Management System

A comprehensive Spring Boot-based restaurant management application with QR code scanning, menu management, order processing, and billing system with PostgreSQL database.

## Project Features

✅ **User Authentication & Authorization**
- User registration and login with JWT tokens
- Role-based access control (ADMIN, CUSTOMER, STAFF)
- Secure password encryption

✅ **Menu & Category Management**
- Dish/menu item management
- Vegetarian and Non-Vegetarian classification
- Category organization
- QR code generation for menu items
- Item availability status

✅ **Order Management**
- Create, retrieve, and track orders
- Order status management (PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED)
- Order item tracking
- Special instructions for dishes

✅ **Billing & Pricing**
- Dynamic pricing
- Discount management
- Tax calculation (5% default)
- Payment status tracking

✅ **QR Code Integration**
- Generate QR codes for menu items
- Encoded item information in QR codes

## Project Structure

```
food_adda/
├── src/
│   ├── main/
│   │   ├── java/com/food/food_adda/
│   │   │   ├── entity/              # JPA Entities
│   │   │   │   ├── User.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── MenuItem.java
│   │   │   │   ├── Order.java
│   │   │   │   └── OrderItem.java
│   │   │   ├── repository/          # Spring Data JPA Repositories
│   │   │   ├── service/             # Business Logic
│   │   │   ├── controller/          # REST Endpoints
│   │   │   ├── security/            # JWT & Security Config
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── config/              # Spring Configuration
│   │   │   ├── utility/             # Helper Classes
│   │   │   └── FoodAddaApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/init.sql
│   └── test/                        # Test Classes
├── pom.xml                          # Maven Dependencies
└── README.md                        # This file

```

## Technology Stack

### Backend
- **Framework:** Spring Boot 4.1.1
- **Language:** Java 21
- **Database:** PostgreSQL 15+
- **Security:** Spring Security + JWT
- **ORM:** Spring Data JPA (Hibernate)
- **QR Code:** ZXing (QR code library)
- **Build Tool:** Maven
- **JSON Web Token:** JJWT

### Dependencies
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-security
- postgresql driver
- jjwt (JWT tokens)
- zxing (QR code generation)
- lombok (Reduce boilerplate)
- spring-boot-starter-validation

## Prerequisites

- Java 21 or higher
- PostgreSQL 15 or higher
- Maven 3.6 or higher
- Git

## Installation & Setup

### 1. PostgreSQL Database Setup

```bash
# Create the database
createdb food_adda_db

# Or via psql
psql -U postgres
CREATE DATABASE food_adda_db;

# Run initialization script
psql -U postgres -d food_adda_db -f src/main/resources/db/init.sql
```

### 2. Update Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/food_adda_db
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

### 3. Update JWT Secret

Edit `src/main/resources/application.properties`:

```properties
jwt.secret=your_super_secure_secret_key_change_this_in_production
jwt.expiration=86400000  # 24 hours in milliseconds
```

### 4. Build & Run

```bash
# Navigate to project directory
cd food_adda

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Or use
java -jar target/food_adda-0.0.1-SNAPSHOT.jar
```

The application will start at: `http://localhost:8080/api`

## API Endpoints Documentation

### Authentication Endpoints

#### Register User
```
POST /api/users/register
Content-Type: application/json

{
    "email": "customer@example.com",
    "password": "password123",
    "fullName": "John Doe",
    "phone": "9876543210"
}

Response: 201 Created
{
    "success": true,
    "message": "User registered successfully",
    "data": {
        "userId": 1,
        "email": "customer@example.com",
        "fullName": "John Doe",
        "role": "CUSTOMER",
        "isActive": true
    }
}
```

#### Login
```
POST /api/users/login
Content-Type: application/json

{
    "email": "customer@example.com",
    "password": "password123"
}

Response: 200 OK
{
    "success": true,
    "message": "Login successful",
    "data": {
        "token": "eyJhbGciOiJIUzUxMiJ9...",
        "email": "customer@example.com",
        "fullName": "John Doe",
        "role": "CUSTOMER"
    }
}
```

### Category Endpoints

#### Create Category
```
POST /api/categories
Authorization: Bearer {token}
Content-Type: application/json

{
    "categoryName": "Biryani",
    "description": "Rice-based dishes",
    "dishType": "VEG"
}

Response: 201 Created
```

#### Get All Categories
```
GET /api/categories
Response: 200 OK
```

#### Get Categories by Dish Type
```
GET /api/categories/type/VEG
Response: 200 OK
```

### Menu Item Endpoints

#### Create Menu Item
```
POST /api/menu/items
Authorization: Bearer {token}
Content-Type: application/json

{
    "itemName": "Veg Fried Rice",
    "description": "Delicious vegetarian fried rice",
    "categoryId": 1,
    "price": 250.00,
    "dishType": "VEG",
    "preparationTime": 15,
    "isAvailable": true
}

Response: 201 Created
```

#### Get All Menu Items
```
GET /api/menu/items
Response: 200 OK
```

#### Get Menu Items by Category
```
GET /api/menu/items/category/{categoryId}
Response: 200 OK
```

#### Get Vegetarian Items
```
GET /api/menu/vegetarian
Response: 200 OK
```

#### Get Non-Vegetarian Items
```
GET /api/menu/non-vegetarian
Response: 200 OK
```

#### Update Menu Item
```
PUT /api/menu/items/{itemId}
Authorization: Bearer {token}
Content-Type: application/json
Response: 200 OK
```

#### Delete Menu Item
```
DELETE /api/menu/items/{itemId}
Authorization: Bearer {token}
Response: 200 OK
```

### Order Endpoints

#### Create Order
```
POST /api/orders/users/{userId}
Authorization: Bearer {token}
Content-Type: application/json

{
    "orderItems": [
        {
            "itemId": 1,
            "quantity": 2,
            "specialInstructions": "Extra spicy"
        },
        {
            "itemId": 2,
            "quantity": 1,
            "specialInstructions": "Less salt"
        }
    ],
    "discountAmount": 50.00,
    "orderNotes": "Please deliver quickly"
}

Response: 201 Created
{
    "success": true,
    "message": "Order created successfully",
    "data": {
        "orderId": 1,
        "userId": 1,
        "totalAmount": 500.00,
        "taxAmount": 25.00,
        "finalAmount": 475.00,
        "status": "PENDING",
        "paymentStatus": "PENDING",
        "orderItems": [...]
    }
}
```

#### Get User Orders
```
GET /api/orders/users/{userId}/all
Authorization: Bearer {token}
Response: 200 OK
```

#### Get Order by ID
```
GET /api/orders/{orderId}
Authorization: Bearer {token}
Response: 200 OK
```

#### Get Orders by Status
```
GET /api/orders/status/{status}
Authorization: Bearer {token}

Status values: PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED
Response: 200 OK
```

#### Update Order Status
```
PUT /api/orders/{orderId}/status/{status}
Authorization: Bearer {token}
Response: 200 OK
```

#### Update Payment Status
```
PUT /api/orders/{orderId}/payment/{paymentStatus}
Authorization: Bearer {token}

Payment Status values: PENDING, COMPLETED, FAILED, REFUNDED
Response: 200 OK
```

#### Cancel Order
```
DELETE /api/orders/{orderId}/cancel
Authorization: Bearer {token}
Response: 200 OK
```

## Database Schema

### Users Table
- user_id (Primary Key)
- email (Unique)
- password
- full_name
- phone
- role (ADMIN, CUSTOMER, STAFF)
- is_active
- created_at, updated_at

### Categories Table
- category_id (Primary Key)
- category_name
- description
- dish_type
- is_active
- created_at, updated_at

### Menu Items Table
- item_id (Primary Key)
- item_name
- description
- category_id (Foreign Key)
- price
- image_url
- qr_code
- dish_type (VEG, NON_VEG)
- is_available
- preparation_time
- created_at, updated_at

### Orders Table
- order_id (Primary Key)
- user_id (Foreign Key)
- total_amount
- discount_amount
- tax_amount
- final_amount
- status
- payment_method
- payment_status
- created_at, updated_at, delivered_at

### Order Items Table
- order_item_id (Primary Key)
- order_id (Foreign Key)
- item_id (Foreign Key)
- quantity
- unit_price
- item_total
- special_instructions

## Security Features

1. **JWT Authentication**: Stateless authentication using JSON Web Tokens
2. **Password Encryption**: BCrypt password hashing
3. **Role-Based Access Control**: ADMIN, CUSTOMER, STAFF roles
4. **CORS Support**: Cross-Origin Resource Sharing enabled
5. **Validation**: Input validation on all endpoints

## Sample Data Insertion

You can insert sample data after the application starts:

```sql
-- Insert Categories
INSERT INTO categories (category_name, description, dish_type, is_active)
VALUES 
    ('Biryani', 'Delicious rice dishes', 'VEG', true),
    ('Curries', 'Spicy curries', 'NON_VEG', true),
    ('Desserts', 'Sweet dishes', 'VEG', true);

-- Insert Menu Items
INSERT INTO menu_items (item_name, description, category_id, price, dish_type, is_available, preparation_time)
VALUES 
    ('Veg Biryani', 'Aromatic vegetable biryani', 1, 250.00, 'VEG', true, 25),
    ('Chicken Biryani', 'Tender chicken with rice', 2, 350.00, 'NON_VEG', true, 30),
    ('Gulab Jamun', 'Sweet milk solids in syrup', 3, 100.00, 'VEG', true, 5);
```

## Configuration

### Application Properties Reference

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/food_adda_db
spring.datasource.username=postgres
spring.datasource.password=password
spring.datasource.driver-class-name=org.postgresql.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration=86400000

# Logging (Optional)
logging.level.root=INFO
logging.level.com.food.food_adda=DEBUG
```

## Troubleshooting

### Database Connection Issues
- Ensure PostgreSQL is running
- Check database credentials in application.properties
- Verify database `food_adda_db` exists

### JWT Token Errors
- Ensure token is passed in Authorization header: `Bearer {token}`
- Token may have expired (default: 24 hours)
- Re-login to get a new token

### Port Already in Use
- Change port in application.properties: `server.port=8081`

## Future Enhancements

- [ ] Payment Gateway Integration (Stripe, PayPal)
- [ ] Email Notifications
- [ ] SMS Notifications
- [ ] Advanced Analytics & Reports
- [ ] Table Reservation System
- [ ] Delivery Partner Management
- [ ] Rating & Review System
- [ ] Loyalty Program
- [ ] Mobile App (React Native/Flutter)
- [ ] Admin Dashboard
- [ ] Inventory Management
- [ ] Staff Management System

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Support

For issues and questions, please create an issue in the repository or contact support@foodadda.com

## Authors

- **Mallikarjuna** - Initial work

---

**Last Updated:** August 31, 2026
**Version:** 1.0.0
