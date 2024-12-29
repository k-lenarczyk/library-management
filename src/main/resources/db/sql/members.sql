CREATE TABLE members (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL
);

CREATE TABLE borrowHistory (
    memberId INTEGER,
    bookId INTEGER,
    borrowDate TEXT,
    returnDate TEXT,
    FOREIGN KEY (memberId) REFERENCES members (id),
    FOREIGN KEY (bookId) REFERENCES books (id)
);

CREATE TABLE currentlyBorrowed (
    memberId INTEGER,
    bookId INTEGER,
    borrowDate TEXT,
    returnDueDate TEXT,
    FOREIGN KEY (memberId) REFERENCES members (id),
    FOREIGN KEY (bookId) REFERENCES books (id)
);
