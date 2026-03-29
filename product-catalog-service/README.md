
# Product & Cart Service (Subsystem 2)

## 📌 Overview

The **Product & Cart Service** is responsible for managing **product catalog, categories, shopping cart, and wishlist** within the distributed e-commerce system.

It operates as an independent subsystem and communicates with the **Central Server** via **JMS (Java Message Service)**.

This subsystem represents the core business logic related to products and user interaction with items.

---

## 🏗️ Responsibilities

* Category management (creation and hierarchy)
* Product management (creation, update, pricing, discounts)
* Shopping cart management
* Wishlist management
* Providing product and cart data to other subsystems

---

## 🔄 Communication

The subsystem does **not expose REST endpoints directly**.
All communication is handled asynchronously via JMS.

Central Server → JMS → Subsystem 2
Subsystem 2 → JMS → Central Server

---

## ⚙️ Technologies Used

* Java
* JMS (Java Message Service)
* JPA (EclipseLink)
* MySQL
* GlassFish Server

---

## 🗄️ Database Structure

This subsystem uses its own schema (`podsistem2`) with the following main entities:

* **Kategorija (Category)**

  * IDKat (Primary Key)
  * naziv
  * IDKatNat (parent category – self-reference)

* **Artikal (Product)**

  * IDArt
  * naziv
  * opis
  * cena
  * popust
  * IDKat (category)
  * IDKor (owner/user)

* **Korpa (Cart)**

  * IDKor (Primary Key, one cart per user)
  * ukupna cena

* **StavkaKorpa (Cart Item)**

  * composite key (IDKor, IDArt)
  * kolicina

* **ListaZelja (Wishlist)**

  * IDKor (Primary Key)
  * datum

* **StavkaListeZelja (Wishlist Item)**

  * composite key (IDKor, IDArt)
  * vreme dodavanja

---

## 📡 Message Format

Subsystem processes string-based commands:

```id="hz7kq2"
COMMAND;arg1;arg2;...
```

Examples:

```id="a91x0d"
CREATE_CATEGORY;naziv;parentId
CREATE_PRODUCT;naziv;opis;cena;idKat;idKor
CHANGE_PRICE;idArt;newPrice;idKor
SET_DISCOUNT;idArt;discount;idKor

ADD_TO_CART;idKor;idArt;kolicina
REMOVE_FROM_CART;idKor;idArt
GET_CART;idKor

ADD_TO_WISHLIST;idKor;idArt
REMOVE_FROM_WISHLIST;idKor;idArt
GET_WISHLIST;idKor
```

---

## 🔑 Key Features

* Product catalog with hierarchical categories
* Support for product pricing and discounts
* One-to-one cart per user model
* Wishlist functionality with timestamps
* Composite keys for cart and wishlist items
* Asynchronous processing via JMS
* Separation of product and order logic (handled in another subsystem)

---

## 🚀 Running the Subsystem

1. Start GlassFish Server
2. Configure MySQL database (`podsistem2`)
3. Deploy the subsystem application
4. Ensure JMS resources are configured:

   * `podsistem2Queue`
   * `ServerReplyQueue`
5. Run the subsystem main listener

---

## ⚠️ Notes

* This subsystem does not handle payments or orders
* Cart is cleared after successful payment (triggered by Subsystem 3)
* Each user has exactly one cart (enforced by primary key)
* Communication is fully asynchronous

---

## 📚 Purpose

This subsystem demonstrates:

* product catalog management
* cart and wishlist logic
* use of composite primary keys in JPA
* modular backend design
* separation of concerns in distributed systems
