# Information system project
This project is a distributed information system developed as part of the **Information Systems 1 course at the School of Electrical Engineering, University of Belgrade**. The system simulates an online marketplace where users can sell and purchase products through a client application while the backend is organized into multiple subsystems communicating asynchronously.

## System Overview

The system consists of the following components:

- **Client Application (Java SE)** – accepts user requests through a console or GUI interface and sends REST requests to the central server.
- **Central Server** – exposes REST endpoints and forwards requests to the appropriate subsystem.
- **Three independent subsystems** – responsible for managing different parts of the business logic and communicating exclusively via **JMS (Java Message Service)**.

The architecture separates responsibilities between services and demonstrates communication between distributed components using REST and messaging.

## Architecture

The system is divided into the following subsystems:

### Subsystem 1 – Users and Cities
Responsible for managing:
- users
- cities
- user credentials
- user balances

### Subsystem 2 – Products and Shopping
Responsible for:
- product categories
- products
- shopping carts
- wishlists

### Subsystem 3 – Orders and Transactions
Responsible for:
- orders
- order items
- payments
- transactions

Each subsystem maintains **its own database** and communicates with the central server using **JMS messaging**, enabling loose coupling between services.

## Key Features

The system supports the following functionalities:

- User authentication (login/logout)
- User management and address updates
- Category and product management
- Price changes and discounts
- Shopping cart operations
- Wishlist management
- Order creation and payment processing
- Transaction tracking
- Retrieval of users, orders, products, and system data

## Technologies Used

- Java (Java SE)
- REST API
- JMS (Java Message Service)
- MySQL
- NetBeans

## Database

Each subsystem has its own **MySQL database**, populated with test data to simulate real system usage.
