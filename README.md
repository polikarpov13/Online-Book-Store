# Online-Book-Store
## Project Description:
A Spring Boot Application that represents an Online Book Store. You can register, login,
choose a book that you would like to buy and add it to your shopping cart, get the information about the book, 
search book by AUTHOR, TITLE or ISBN parameters as USER. You can make CRUD operations with book as ADMIN.

List that provides entities that are used in this project:
## Entities:
1. Book - represents a book entity that is available in the store and contains fields such as AUTHOR, TITLE, PRICE 
etc.
2. CartItem - represents an item entity that is placed in the user's shopping cart
3. Category - represents a category of a certain book or books
4. Order - represents an order entity that belongs to user with the information of the order such as shipping address, 
order date, status etc.
5. OrderItem - represents an order item entity that is placed is the user's order
6. ShoppingCart - represents a user's shopping cart entity that contains List of cart items that user has already added
7. User - represents a user entity which includes fields such as FIRST NAME, LAST NAME, EMAIL, PASSWORD, 
SHIPPING ADDRESS etc.

## Technologies:
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- RESTful API

# API Endpoints:
## Authentication API:
- POST /auth/register: registration
- POST /auth/login: login

## Book API
- GET    /books: get all existing books
- GET    /books/{id}: get an existing book by ID
- GET    /books: search a book by certain parameters
- PUT    /books/{id}: update an existing book by ID
- POST   /books: create a new book entity
- DELETE /books/{id}: remove a book by ID

## Category API
- GET    /categories: get all existing categories
- GET    /categories/{id}: get an existing category by ID
- GET    /categories/{id}/books: get all existing books by category ID
- PUT    /categories/{id}: update an existing category by ID
- POST   /categories: create a new category entity
- DELETE /categories/{id}: remove a category by ID

## Order API
- GET   /orders: get all user's orders
- GET   /orders/{orderId}/items: get all order items by order ID
- GET   /orders/{orderId}/items/{itemId}: get order item by order ID and item ID
- PATCH /orders/{orderId}: update order status
- POST  /orders: place a new order

## Shopping Cart API
- GET    /cart: get user's shopping cart
- PUT    /cart/cart-items/{itemId}: update item's quantity by item ID in user's shopping cart
- POST   /cart: add item to user's shopping cart
- DELETE /cart/cart-items/{itemId}: remove an item from user's shopping cart

## Getting started:
- https://github.com/polikarpov13/Online-Book-Store.git

