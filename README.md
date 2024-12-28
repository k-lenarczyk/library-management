# Library Management System

A Java-based Library Management System designed to showcase Java programming skills. This app manages 
books, members, and borrowing activities efficiently.

## Table of Contents
1. [Features](#features)
2. [Technologies Used](#technologies-used)
3. [Setup and Installation](#setup-and-installation)
4. [Usage Instructions](#usage-instructions)
5. [Sample Data](#sample-data)
6. [Project Structure](#project-structure)

---

## Features
- Manage books (add, update, delete, search).
- Manage members (registration, update, delete).
- Track borrowing and returning of books.
- Tracking overdue books.
- User-friendly console interface.

---

## Technologies Used
- **Programming Language**: Java
- **Database**: SQLite
- **Build Tool**: Maven
- **Frameworks**: Pure Java

---

## Setup and Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/k-lenarczyk/library-management.git

2. Open the project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
3. Configure the database connection in the config.properties file.
4. Build the project:
    ```bash
    mvn clean install
5. Run the application.

---

# Usage Instructions (Update)
1. Start the application:
    ```bash
    java -jar library-management-system.jar
2. Login with librarian credentials (Enter Id: 0).
3. Use the menus to perform operations:
   - Add or search books.
   - Register new members. 
   - Borrow or return books.

NOTE: The application is designed so that there CANNOT be multiple books of the same title!

---

## Sample Data

The repository contains a sample SQLite database (library.db) with the following tables:
- **books**: Contains sample book records.
- **members**: Contains sample member records.
- **borrowHistory**: Contains sample borrowing history records.
- **currentlyBorrowed**: Contains sample currently borrowed books.

---

# Project Structure (Update)

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com.librarymanagement/
│   │   │   ├── model/               # Data models (Book, Member)
│   │   │   ├── service/             # Business logic
│   │   │   ├── controller/          # Handle user inputs
│   │   │   ├── persistence/         # Data access layer
│   |   |   |── LibraryApp.java      # Main class
│   ├── resources/
│       ├── db/
|           ├── sql                  # Database Schemas
|           ├── library.db           # SQLite database
```

---