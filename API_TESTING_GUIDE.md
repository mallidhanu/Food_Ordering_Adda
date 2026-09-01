# API Testing Guide - Food Adda Restaurant Management System

This guide provides curl commands for testing all API endpoints. Update the base URL and token as needed.

## Base URL
```
http://localhost:8080/api
```

## 1. USER AUTHENTICATION

### Register a New User
```bash
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer1@example.com",
    "password": "password123",
    "fullName": "Raj Kumar",
    "phone": "9876543210"
  }'
```

### Login User (Get JWT Token)
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "customer1@example.com",
    "password": "password123"
  }'
```

**Save the token from response for authenticated requests:**
```
export TOKEN="your_jwt_token_here"
```

### Get User Details
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 2. CATEGORY MANAGEMENT

### Create a Category
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "categoryName": "Biryani",
    "description": "Fragrant rice dishes cooked with meat/vegetables",
    "dishType": "VEG"
  }'
```

### Create Non-Veg Category
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "categoryName": "Non-Veg Curries",
    "description": "Delicious non-vegetarian curries",
    "dishType": "NON_VEG"
  }'
```

### Get All Categories
```bash
curl -X GET http://localhost:8080/api/categories
```

### Get Category by ID
```bash
curl -X GET http://localhost:8080/api/categories/1
```

### Get Categories by Type (VEG/NON_VEG)
```bash
curl -X GET http://localhost:8080/api/categories/type/VEG
```

### Update Category
```bash
curl -X PUT http://localhost:8080/api/categories/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "categoryName": "Biryani - Updated",
    "description": "Updated description",
    "dishType": "VEG"
  }'
```

### Delete Category
```bash
curl -X DELETE http://localhost:8080/api/categories/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 3. MENU ITEM MANAGEMENT

### Create Menu Item
```bash
curl -X POST http://localhost:8080/api/menu/items \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "itemName": "Veg Biryani",
    "description": "Aromatic basmati rice cooked with fresh vegetables",
    "categoryId": 1,
    "price": 250.00,
    "dishType": "VEG",
    "preparationTime": 25,
    "isAvailable": true,
    "imageUrl": "https://example.com/veg-biryani.jpg"
  }'
```

### Create Another Menu Item (Non-Veg)
```bash
curl -X POST http://localhost:8080/api/menu/items \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "itemName": "Chicken Biryani",
    "description": "Tender chicken pieces with fragrant basmati rice",
    "categoryId": 2,
    "price": 350.00,
    "dishType": "NON_VEG",
    "preparationTime": 30,
    "isAvailable": true
  }'
```

### Get All Available Menu Items
```bash
curl -X GET http://localhost:8080/api/menu/items
```

### Get Menu Item by ID
```bash
curl -X GET http://localhost:8080/api/menu/items/1
```

### Get Menu Items by Category
```bash
curl -X GET http://localhost:8080/api/menu/items/category/1
```

### Get All Vegetarian Items
```bash
curl -X GET http://localhost:8080/api/menu/vegetarian
```

### Get All Non-Vegetarian Items
```bash
curl -X GET http://localhost:8080/api/menu/non-vegetarian
```

### Update Menu Item
```bash
curl -X PUT http://localhost:8080/api/menu/items/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "itemName": "Veg Biryani - Special",
    "description": "Special recipe with exotic spices",
    "categoryId": 1,
    "price": 300.00,
    "dishType": "VEG",
    "preparationTime": 30,
    "isAvailable": true
  }'
```

### Delete Menu Item
```bash
curl -X DELETE http://localhost:8080/api/menu/items/1 \
  -H "Authorization: Bearer $TOKEN"
```

---

## 4. ORDER MANAGEMENT

### Create Order
```bash
curl -X POST http://localhost:8080/api/orders/users/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
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
    "orderNotes": "Please deliver before 7 PM"
  }'
```

### Get User Orders
```bash
curl -X GET http://localhost:8080/api/orders/users/1/all \
  -H "Authorization: Bearer $TOKEN"
```

### Get Order by ID
```bash
curl -X GET http://localhost:8080/api/orders/1 \
  -H "Authorization: Bearer $TOKEN"
```

### Get Orders by Status
```bash
# Status options: PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED
curl -X GET http://localhost:8080/api/orders/status/PENDING \
  -H "Authorization: Bearer $TOKEN"
```

### Update Order Status
```bash
# Status: PENDING, CONFIRMED, PREPARING, READY, DELIVERED, CANCELLED
curl -X PUT http://localhost:8080/api/orders/1/status/CONFIRMED \
  -H "Authorization: Bearer $TOKEN"
```

### Update Payment Status
```bash
# Payment Status: PENDING, COMPLETED, FAILED, REFUNDED
curl -X PUT http://localhost:8080/api/orders/1/payment/COMPLETED \
  -H "Authorization: Bearer $TOKEN"
```

### Cancel Order
```bash
curl -X DELETE http://localhost:8080/api/orders/1/cancel \
  -H "Authorization: Bearer $TOKEN"
```

---

## WORKFLOW EXAMPLE

### Complete ordering workflow:

```bash
# 1. Register user
curl -X POST http://localhost:8080/api/users/register \
  -H "Content-Type: application/json" \
  -d '{"email": "newuser@example.com", "password": "pass123", "fullName": "Ramesh", "phone": "9999999999"}'

# 2. Login to get token
TOKEN=$(curl -s -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{"email": "newuser@example.com", "password": "pass123"}' | grep -o '"token":"[^"]*' | cut -d'"' -f4)

# 3. Create a category
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"categoryName": "Starters", "description": "Appetizers", "dishType": "VEG"}'

# 4. Create menu items
curl -X POST http://localhost:8080/api/menu/items \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"itemName": "Samosa", "categoryId": 1, "price": 50.00, "dishType": "VEG", "preparationTime": 5, "isAvailable": true}'

# 5. View all menu items
curl -X GET http://localhost:8080/api/menu/items

# 6. Place an order
curl -X POST http://localhost:8080/api/orders/users/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{"orderItems": [{"itemId": 1, "quantity": 2}], "discountAmount": 0.0}'

# 7. Check order status
curl -X GET http://localhost:8080/api/orders/1 -H "Authorization: Bearer $TOKEN"

# 8. Update order to CONFIRMED
curl -X PUT http://localhost:8080/api/orders/1/status/CONFIRMED -H "Authorization: Bearer $TOKEN"

# 9. Update payment to COMPLETED
curl -X PUT http://localhost:8080/api/orders/1/payment/COMPLETED -H "Authorization: Bearer $TOKEN"
```

---

## Error Responses

### 400 Bad Request
```json
{
  "success": false,
  "message": "Email already registered",
  "data": null
}
```

### 401 Unauthorized
```json
{
  "success": false,
  "message": "Invalid or expired token",
  "data": null
}
```

### 404 Not Found
```json
{
  "success": false,
  "message": "User not found",
  "data": null
}
```

---

## Response Headers
```
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

## Notes
- Replace `$TOKEN` with your actual JWT token
- Replace IDs (1, 2, etc.) with actual IDs from your database
- Tax is automatically calculated at 5% of total amount
- All timestamps are in ISO 8601 format
- Use "Bearer " prefix before token in Authorization header

---

**Last Updated:** August 31, 2026
