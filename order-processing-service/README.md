
# Order & Transaction Service (Subsystem 3)

## 📌 Overview

The **Order & Transaction Service** is responsible for handling **orders, payments, and financial transactions** within the distributed e-commerce system.

It operates as an independent subsystem and communicates with the **Central Server** via **JMS (Java Message Service)**.

This subsystem represents the final stage of the business workflow, where user carts are converted into orders and transactions are recorded.

---

## 🏗️ Responsibilities

* Order creation and management
* Payment processing
* Transaction recording
* Managing order items and pricing
* Providing order and transaction data to other components

---

## 🔄 Communication

The subsystem does **not expose REST endpoints directly**.
All communication is performed asynchronously via JMS.

Central Server → JMS → Subsystem 3
Subsystem 3 → JMS → Central Server

---

## ⚙️ Technologies Used

* Java
* JMS (Java Message Service)
* JPA (EclipseLink)
* MySQL
* GlassFish Server

---

## 🗄️ Database Structure

This subsystem uses its own schema (`podsistem3`) with the following main entities:

* **Narudzbina (Order)**

  * IDNar (Primary Key)
  * IDKor (User)
  * IDGra (City)
  * ukupna_cena
  * vreme (timestamp)
  * adresa

* **StavkaNarudzbina (Order Item)**

  * composite key (IDNar, IDArt)
  * kolicina
  * jedinicnaCena

* **Transakcija (Transaction)**

  * IDTra (Primary Key)
  * IDNar (Order)
  * suma
  * vreme

---

## 📡 Message Format

Subsystem processes string-based commands:

```id="l8x2nv"
COMMAND;arg1;arg2;...
```

Examples:

```id="p4k1zo"
CREATE_ORDER;idKor;idGra;adresa
GET_ORDERS;idKor
GET_ALL_ORDERS

PAYMENT;idKor;idArt:kolicina:cena|idArt:kolicina:cena
GET_TRANSACTIONS
```

---

## 💳 Payment Workflow

1. Central Server requests cart data from Subsystem 2
2. Cart items are formatted into a string:

```id="q8rmz7"
idArt:kolicina:cena|...
```

3. PAYMENT request is sent to this subsystem
4. Order is created with all items
5. Transaction is recorded
6. Response is returned to Central Server
7. Cart is cleared in Subsystem 2

---

## 🔑 Key Features

* Order creation based on cart data
* Transaction tracking for each order
* One-to-one relationship between order and transaction
* Composite keys for order items
* Timestamp-based tracking of orders and payments
* Asynchronous processing via JMS
* Integration with other subsystems (especially cart service)

---

## 🚀 Running the Subsystem

1. Start GlassFish Server
2. Configure MySQL database (`podsistem3`)
3. Deploy the subsystem application
4. Ensure JMS resources are configured:

   * `podsistem3Queue`
   * `ServerReplyQueue`
5. Run the subsystem main listener

---

## ⚠️ Notes

* This subsystem depends on data from Subsystem 2 (cart)
* It does not manage product or user data directly
* Each order results in exactly one transaction
* Designed for clear separation between business domains

---

## 📚 Purpose

This subsystem demonstrates:

* order lifecycle management
* payment processing logic
* transaction handling in distributed systems
* inter-service communication
* data consistency across subsystems
