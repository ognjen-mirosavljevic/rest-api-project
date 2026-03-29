# Distributed E-Commerce Backend System

## 📌 Overview

This project represents a **distributed e-commerce backend system** designed using a **modular, event-driven architecture**.

The system consists of a **Central REST Server** and multiple **independent subsystems** that communicate asynchronously using **JMS (Java Message Service)**.

It simulates a real-world backend with clear separation of concerns, scalability, and loosely coupled components.

---

## 🏗️ System Architecture

The system is divided into four main components:

### 🔹 Central Server

* Exposes REST API (JAX-RS)
* Handles all client requests
* Routes requests to subsystems via JMS
* Aggregates responses

---

### 🔹 User & City Service (Subsystem 1)

* User registration and authentication
* Role management
* City management
* Balance handling

---

### 🔹 Product & Cart Service (Subsystem 2)

* Category and product management
* Shopping cart logic
* Wishlist functionality
* Product pricing and discounts

---

### 🔹 Order & Transaction Service (Subsystem 3)

* Order creation and tracking
* Payment processing
* Transaction history
* Order item management

---

## 🔄 Communication Flow

Client → Central Server (REST) → JMS → Subsystems
Subsystems → JMS → Central Server → Client

---

## ⚙️ Technologies Used

* Java
* JAX-RS (REST API)
* JMS (asynchronous messaging)
* GlassFish Server
* MySQL
* JPA (EclipseLink)
* Swing (Client Application)

---

## 🧩 Key Features

* Distributed system with **multiple independent subsystems**
* **Event-driven architecture** using JMS
* Centralized REST API gateway
* **Database per subsystem** (data isolation)
* User authentication and role-based access
* Product catalog with categories and discounts
* Cart and wishlist management
* Order processing and transaction tracking
* End-to-end payment workflow

---

## 📡 Communication Protocol

Subsystem communication is implemented using a string-based format:

```id="gk0yq2"
COMMAND;arg1;arg2;...
```

Example:

```id="g7g7n0"
GET_CART;1
PAYMENT;1;2:3:500|5:1:300
```

---

## 💳 Payment Flow

1. Client sends payment request via REST
2. Central Server retrieves cart from Subsystem 2
3. Cart items are formatted and sent to Subsystem 3
4. Subsystem 3:

   * Creates order
   * Records transaction
5. Central Server clears cart in Subsystem 2
6. Response is returned to the client

---

## 🚀 How to Run

1. Start GlassFish Server
2. Set up MySQL databases:

   * `podsistem1`
   * `podsistem2`
   * `podsistem3`
3. Deploy all components:

   * Central Server
   * Subsystem 1
   * Subsystem 2
   * Subsystem 3
4. Configure JMS resources:

   * `podsistem1Queue`
   * `podsistem2Queue`
   * `podsistem3Queue`
   * `ServerReplyQueue`
5. Start subsystem listeners
6. Run client application or use Postman

---

## 📁 Project Structure

```id="c9v2n3"
rest-api-project/
│
├── central-server/
├── user-management-service/
├── product-catalog-service/
├── order-processing-service/
├── client-app/
└── README.md
```

---

## ⚠️ Notes

* Each subsystem has its own database schema
* Communication is fully asynchronous via JMS
* Subsystems are loosely coupled and independently deployable
* Central Server acts as the single entry point

---

## 📚 Purpose

This project demonstrates:

* distributed system design
* event-driven architecture
* REST + JMS integration
* modular backend development
* real-world e-commerce workflow simulation

---

## 🔥 Highlights (for CV)

* Designed and implemented a distributed backend system with asynchronous messaging
* Built modular services with clear separation of concerns
* Implemented full e-commerce workflow (users, products, cart, orders, payments)
* Integrated REST API with JMS-based communication
