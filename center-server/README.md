
# Central Server – Distributed E-Commerce System

## 📌 Overview

The **Central Server** represents the core component of the distributed system.
It exposes a **RESTful API** to clients and coordinates communication between multiple independent subsystems using **JMS (Java Message Service)**.

This component acts as a **middleware layer**, handling request routing, response aggregation, and system orchestration.

---

## 🏗️ Responsibilities

* Exposes REST endpoints for client communication
* Validates and processes incoming requests
* Forwards requests to appropriate subsystems via JMS
* Receives and aggregates responses from subsystems
* Maintains system-wide coordination logic

---

## 🔄 Communication Flow

Client → REST API (Central Server) → JMS → Subsystems
Subsystems → JMS → Central Server → Response to Client

---

## ⚙️ Technologies Used

* Java
* JAX-RS (REST API)
* JMS (Java Message Service)
* GlassFish Server
* MySQL
* JPA (EclipseLink)

---

## 📡 Request Format

Communication with subsystems is implemented using a **string-based protocol**:

```
COMMAND;arg1;arg2;...
```

Example:

```
GET_CART;1
PAYMENT;1;2:3:500|5:1:300
```

---

## 🔑 Key Features

* Centralized API entry point
* Asynchronous communication via JMS
* Decoupled subsystem architecture
* Scalable and modular design
* Supports user, product, cart, and order workflows

---

## 🚀 Running the Server

1. Start GlassFish Server
2. Deploy the central server application
3. Ensure all JMS queues are configured:

   * `podsistem1Queue`
   * `podsistem2Queue`
   * `podsistem3Queue`
   * `ServerReplyQueue`
4. Run subsystem services (listeners)
5. Send requests via REST client (Postman or GUI)

---

## ⚠️ Notes

* The server does not contain business logic of subsystems
* Each subsystem has its own database
* Communication is asynchronous and loosely coupled
* Responses may depend on subsystem availability

---

## 📚 Purpose

This component demonstrates:

* Distributed system design
* Event-driven architecture using JMS
* RESTful service integration
* Separation of concerns in backend systems
