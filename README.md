# Bank Management - Practice Project 🎓

> [!NOTE]
> This is a **Practice Project** from my early days of learning Java. It explores core concepts like JDBC, File I/O, and OOP. It is **not** intended for production use!

Hey there! This is my very first Java project. I built this while I was just starting out and learning how things work in the world of Java development. 

So dont judge me too hard

It's a simple console-based application to manage bank accounts, but it was a massive learning experience for me.

## The Evolution 🚀
This project went through three stages: starting with simple in-memory storage, moving to JSON files, and finally landing on a MySQL database with JDBC.

## What I Learned 🧠
- **Java Basics**: Inheritance, list handling, and all those core concepts.
- **JSON & Jackson**: How to actually save data into files using the Jackson plugin.
- **JDBC**: Connecting a real database to my Java code.
- **JUnit**: Learning how to write basic tests to make sure things don't break.

## Features ✨
- Create a new account (with validation!)
- Check your balance
- Deposit and Withdraw money
- Secure(ish) login with PIN
- Transaction logs

## Tech Stack 🛠️
- **Java** (of course)
- **JDBC / MySQL** for the database
- **Jackson** for JSON handling
- **Dotenv** to keep credentials safe
- **JUnit 5** for some basic tests

## How to Run 🏃‍♂️

### Prerequisites
- Java 21+
- Maven
- MySQL Database

### Setup
1. Create a MySQL database named `bank_management`.
2. Run the SQL script (you might need to create the tables `user_data` and `account_logs` 
3. Configure your `.env` file in `bankmanagement/.env`:
   ```properties
   DB_URL=jdbc:mysql://localhost:3306/bank_management
   DB_USER=your_username
   DB_PASSWORD=your_password
   ```
4. Build and Run:
   ```bash
   cd bankmanagement
   mvn clean install
   mvn exec:java
   ```

---