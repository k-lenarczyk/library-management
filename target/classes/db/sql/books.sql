CREATE TABLE books (
    id INTEGER PRIMARY KEY UNIQUE,
    title TEXT NOT NULL,
    author TEXT NOT NULL,
    genre TEXT NOT NULL,
    isbn TEXT NOT NULL UNIQUE NOT NULL,
    isAvailable BOOLEAN NOT NULL,
    borrower INTEGER,
    returnDueDate TEXT,
    FOREIGN KEY (borrower) REFERENCES members (id)
)