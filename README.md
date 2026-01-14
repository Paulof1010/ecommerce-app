# Ecommerce Backend API

Spring Boot backend for an ecommerce system.  
Project under active development.

---

## Current Features

- Product catalog
- Shopping cart
- Cart → Order checkout flow
- Order lifecycle (DRAFT, PAID, SHIPPED, CANCELLED)
- Stock control
- Basic user and role model

---

## Tech Stack

- Java 25
- Spring Boot
- Maven

---

## Domain Model

- `User` – system users  
- `Role` – user roles  
- `Product` – products with stock and price  
- `ShoppingCart` – user cart  
- `CartItem` – product + quantity inside cart  
- `Order` – checkout result  
- `OrderItem` – snapshot of cart items  
- `OrderStatus` – order lifecycle state  

---

## Project Structure

src/main/java/com/paulo/ecommerceapp

├── config

├── controller

├── dto

├── exception

├── model

├── repository

├── security

└── service

---

