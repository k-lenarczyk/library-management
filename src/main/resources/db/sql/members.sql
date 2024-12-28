CREATE TABLE members (
    id INTEGER PRIMARY KEY UNIQUE,
    name TEXT NOT NULL,
    borrowedCount INTEGER NOT NULL
);

CREATE TABLE borrowHistory (
    memberId INTEGER,
    bookId INTEGER,
    borrowDate TEXT,
    returnDate TEXT,
    FOREIGN KEY (memberId) REFERENCES members (id)
);

CREATE TABLE currentlyBorrowed (
    memberId INTEGER,
    bookId INTEGER,
    borrowDate TEXT,
    returnDueDate TEXT,
    FOREIGN KEY (memberId) REFERENCES members (id)
);
