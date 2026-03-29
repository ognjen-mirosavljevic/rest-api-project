
# User & City Service (Subsystem 1)

## 📌 Overview

The **User & City Service** is responsible for managing **users and cities** within the distributed e-commerce system.
It operates as an independent subsystem and communicates with the **Central Server** via **JMS (Java Message Service)**.

This service handles user-related operations such as registration, authentication, and balance management, as well as city-related functionalities.

---

## 🏗️ Responsibilities

* User registration and authentication
* Managing user data (name, address, balance)
* Handling user roles (e.g., administrator)
* City creation and retrieval
* Providing user-related data to other subsystems

---

## 🔄 Communication

The subsystem does **not expose REST endpoints directly**.
Instead, it communicates asynchronously with the Central Server using JMS.

Central Server → JMS → Subsystem 1
Subsystem 1 → JMS → Central Server

---

## ⚙️ Technologies Used

* Java
* JMS (Java Message Service)
* JPA (EclipseLink)
* MySQL
* GlassFish Server

---

## 🗄️ Database Structure

This subsystem uses its own database schema (`podsistem1`) with the following main entities:

* **Korisnik (User)**

  * IDKor (Primary Key)
  * username
  * password
  * ime, prezime
  * stanjeNovca
  * adresa
  * IDGra (City)
  * IDUlo (Role)

* **Grad (City)**

  * IDGra
  * naziv

* **Uloga (Role)**

  * IDUlo
  * naziv (e.g., administrator, user)

---

## 📡 Message Format

Requests are received as string-based commands:

```id="m1z8k3"
COMMAND;arg1;arg2;...
```

Examples:

```id="9w2l4p"
REGISTER;username;password;ime;prezime;adresa;idGrad
LOGIN;username;password
ADD_MONEY;amount;userId;adminId
GET_USERS
GET_CITIES
```

---

## 🔑 Key Features

* Independent user management subsystem
* Role-based authorization (e.g., admin privileges)
* Balance management for users
* City management
* Asynchronous request handling via JMS
* Loose coupling with other system components

---

## 🚀 Running the Subsystem

1. Start GlassFish Server
2. Configure MySQL database (`podsistem1`)
3. Deploy the subsystem application
4. Ensure JMS resources are configured:

   * `podsistem1Queue`
   * `ServerReplyQueue`
5. Run the subsystem main listener

---

## ⚠️ Notes

* This subsystem does not communicate directly with clients
* All requests are routed through the Central Server
* Each subsystem has its own database (no direct sharing)
* Designed for modularity and scalability

---

## 📚 Purpose

This subsystem demonstrates:

* user management in distributed systems
* role-based access control
* database isolation per subsystem
* asynchronous communication using JMS
