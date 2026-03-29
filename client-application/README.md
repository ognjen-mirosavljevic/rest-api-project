
# Client Application – Distributed E-Commerce System

## 📌 Overview

The **Client Application** is the user-facing component of the distributed e-commerce system.
It provides an interface for interacting with the **Central Server** through a set of REST API calls.

The application allows users to perform various operations such as authentication, product browsing, cart management, wishlist manipulation, and order-related actions.

---

## 🏗️ Responsibilities

* Provides user interaction through a graphical interface
* Sends requests to the Central Server using REST
* Displays responses returned by the backend system
* Maintains session-related information for the logged-in user
* Enables access to supported system functionalities

---

## 🔄 Communication Flow

User → Client Application → REST API (Central Server) → JMS → Subsystems

---

## ⚙️ Technologies Used

* Java
* Swing GUI
* HTTP/REST communication
* Central Server API

---

## 🔑 Key Features

* User login and registration
* Display of cities, users, categories, and products
* Product creation and modification
* Cart management
* Wishlist management
* Order overview
* Transaction overview
* Payment initiation through the backend system

---

## 🚀 Supported Functionalities

The client application supports operations such as:

* User authentication
* Creating users
* Viewing cities
* Creating cities
* Viewing categories
* Creating categories
* Viewing products
* Creating products
* Changing product price
* Setting discounts
* Adding products to cart
* Removing products from cart
* Adding products to wishlist
* Removing products from wishlist
* Viewing cart and wishlist
* Viewing user orders
* Viewing all orders
* Viewing transactions
* Performing payment

---

## 🧩 Session Management

The application keeps track of the currently logged-in user through session-related data such as:

* user ID
* username
* role

This enables authorization-aware operations and simplifies interaction with the backend services.

---

## 📡 Communication Details

The client communicates with the Central Server using **HTTP requests**.
The server then forwards the requests to the appropriate subsystem through JMS-based communication.

Typical request types include:

* `GET`
* `POST`

Responses are handled and presented to the user in a readable format through the GUI.

---

## ⚠️ Notes

* The client application does not directly communicate with subsystem databases
* All backend operations are performed through the Central Server
* Authorization rules are enforced by the backend system
* GUI is designed primarily for functional interaction and testing

---

## 📚 Purpose

This component demonstrates:

* client-server communication
* REST API consumption
* GUI-based interaction with distributed backend systems
* session handling in desktop applications
