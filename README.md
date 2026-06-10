# Topic-7-JDBC-
## Simple Student CRUD System Activity

A desktop-based CRUD (Create, Read, Update, Delete) application built using Java and PostgreSQL. This project demonstrates database connectivity using JDBC, allowing users to manage student records efficiently.

---

### 🚀 Features
- Full CRUD Operations: Add, view, update, and delete student records
- JDBC Connectivity: Direct connection to PostgreSQL database
- Simple & Clean Interface: Easy-to-use console or GUI-based design
- Structured Code: Organized packages for connection, model, and operations

---

### 🛠️ Tech Stack & Dependencies
- **Language:** Java 17+
- **Database:** PostgreSQL
- **Connectivity:** PostgreSQL JDBC Driver
- **Tools:** IntelliJ IDEA / NetBeans

---

### 📂 Project Structure

---

### 📋 Database Schema
Database name: `studentdb`

```sql
CREATE TABLE students (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    course VARCHAR(50)
);


---

✅ **After doing all this, your repo will look exactly like the example you showed — same style, same details, same professional look.**

Do you want me to copy-paste the exact README content you can use right now?
