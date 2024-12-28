package com.librarymanagement.persistence;

import com.librarymanagement.model.Book;

import java.sql.*;
import java.time.LocalDate;

public class BookDAO {

  private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/db/library.db";

  public void addBook(Book book) {
    String query = "INSERT INTO books (title, author, genre, isbn, isAvailable, borrower, returnDueDate) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, book.getTitle());
      pstmt.setString(2, book.getAuthor());
      pstmt.setString(3, book.getGenre());
      pstmt.setString(4, book.getIsbn());
      pstmt.setBoolean(5, book.getAvailability());
      pstmt.setObject(6, (book.getBorrower() != null) ? book.getBorrower() : null, java.sql.Types.INTEGER);
      pstmt.setString(7, (book.getReturnDueDate() != null) ? book.getReturnDueDate().toString() : null);

      pstmt.executeUpdate();
      System.out.println("\nBook added successfully.\n");
    } catch (SQLException e) {
      System.out.println("\nError adding book.\n");
      e.printStackTrace();
    }
  }

  // Update existing book in database
  public void updateBook(Book book){
    String query = "UPDATE books SET title = ?, author = ?, genre = ?, isbn = ?, isAvailable = ?, borrower = ?, returnDueDate = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, book.getTitle());
      pstmt.setString(2, book.getAuthor());
      pstmt.setString(3, book.getGenre());
      pstmt.setString(4, book.getIsbn());
      pstmt.setBoolean(5, book.getAvailability());
      pstmt.setObject(6, book.getBorrower(), java.sql.Types.INTEGER);
      pstmt.setString(7, (book.getReturnDueDate() != null) ? book.getReturnDueDate().toString() : null);
      pstmt.setInt(8, book.getId());

      pstmt.executeUpdate();
      System.out.println("\nBook updated successfully.\n");
    } catch (SQLException e) {
      System.out.println("\nError updating book.\n");
      e.printStackTrace();
    }
  }

  public void deleteBook(Book book){
    String query = "DELETE FROM books WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setInt(1, book.getId());
      int rowsAffected = pstmt.executeUpdate();

      if(rowsAffected > 0){
        System.out.println("\nBook deleted successfully.\n");
      } else {
        System.out.println("\nBook not found.\n");
      }

    } catch (SQLException e) {
      System.out.println("\nError deleting book.\n");
      e.printStackTrace();
    }
  }

  public Book findBookById(int bookId) {
    String query = "SELECT id, title, author, genre, isbn, isAvailable, borrower, returnDueDate FROM books WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setInt(1, bookId);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        String title = rs.getString("title");
        String author = rs.getString("author");
        String genre = rs.getString("genre");
        String isbn = rs.getString("isbn");
        boolean isAvailable = rs.getBoolean("isAvailable");
        Integer borrower = rs.getInt("borrower");
        String returnDueDate = rs.getString("returnDueDate");

        LocalDate dueDate = (returnDueDate != null) ? LocalDate.parse(returnDueDate) : null;

        return new Book(bookId, title, author, genre, isbn, isAvailable, borrower, dueDate);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public Book findBookByTitle(String bookTitle){
    String query = "SELECT id, title, author, genre, isbn, isAvailable, borrower, returnDueDate FROM books WHERE " +
            "LOWER(title) = LOWER(?)";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, bookTitle);
      ResultSet rs = pstmt.executeQuery();

      if(rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String genre = rs.getString("genre");
        String isbn = rs.getString("isbn");
        boolean isAvailable = rs.getBoolean("isAvailable");
        Integer borrower = rs.getInt("borrower");
        String returnDueDate = rs.getString("returnDueDate");

        LocalDate dueDate = (returnDueDate != null) ? LocalDate.parse(returnDueDate) : null;

        return new Book(id, title, author, genre, isbn, isAvailable, borrower, dueDate);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return null;
  }

  public void viewAllBooks() {
    String query = "SELECT id, title, author, genre, isbn, isAvailable FROM books";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      System.out.printf("%-3s | %-18s | %-18s | %-10s | %-14s | %-9s%n", "ID", "Title", "Author", "Genre", "ISBN",
              "Available");
      System.out.println("--------------------------------------------------------------------------------------");

      while (rs.next()) {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        String genre = rs.getString("genre");
        String isbn = rs.getString("isbn");
        boolean isAvailable = rs.getBoolean("isAvailable");

        System.out.printf("%-3d | %-18s | %-18s | %-10s | %-14s | %-9s%n",
                id, title, author, genre, isbn, isAvailable ? "Yes" : "No");
      }

    } catch (SQLException e) {
      System.out.println("Error fetching books.");
      e.printStackTrace();
    }
  }

  public void viewOverdueBooks(){
    String query = "SELECT memberId, bookId, returnDueDate FROM currentlyBorrowed WHERE " +
            "returnDueDate < ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, LocalDate.now().toString());
      ResultSet rs = pstmt.executeQuery();

      System.out.printf("%-3s | %-13s | %-10s | %-11s%n", "ID", "Expected Date", "Days since", "Borrower ID");
      System.out.println("----------------------------------------------");

      while (rs.next()) {
        int memberId = rs.getInt("memberId");
        int bookId = rs.getInt("bookId");
        String returnDueDate = rs.getString("returnDueDate");

        LocalDate dueDate = LocalDate.parse(returnDueDate);
        int daysSince = dueDate.until(LocalDate.now()).getDays();

        System.out.printf("%-3d | %-13s | %-10d | %-11d%n",
                bookId, returnDueDate, daysSince, memberId);
      }

    } catch (SQLException e) {
      System.out.println("\nError fetching overdue books.\n");
      e.printStackTrace();
    }
  }

  public void borrowBook(int bookId, int memberId) {
    String query = "INSERT INTO currentlyBorrowed (memberId, bookId, borrowDate, returnDueDate) VALUES (?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setInt(1, memberId);
      pstmt.setInt(2, bookId);
      pstmt.setString(3, LocalDate.now().toString());
      pstmt.setString(4, LocalDate.now().plusDays(14).toString());
      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected > 0) {
        System.out.println("\nBook borrowed successfully.\n");
      } else {
        System.out.println("\nError borrowing book.\n");
      }
    } catch (SQLException e) {
      System.out.println("\nError borrowing book.\n");
      e.printStackTrace();
    }
  }

  public void returnBook(int bookId, int memberId){
    String q1 = "SELECT * FROM currentlyBorrowed WHERE bookId = ?";
    String q2 = "INSERT INTO borrowHistory (memberId, bookId, borrowDate, returnDate) VALUES (?, ?, ?, ?)";
    String q3 = "DELETE FROM currentlyBorrowed WHERE bookId = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL); PreparedStatement pstmt1 =
            conn.prepareStatement(q1); PreparedStatement pstmt2 =
            conn.prepareStatement(q2); PreparedStatement pstmt3 = conn.prepareStatement(q3)) {
      pstmt1.setInt(1, bookId);
      ResultSet rs = pstmt1.executeQuery();
      if(!rs.next()){
        System.out.println("\nThis book is not borrowed.\n");
        return;
      }

      pstmt2.setInt(1, rs.getInt("memberId"));
      pstmt2.setInt(2, rs.getInt("bookId"));
      pstmt2.setString(3, rs.getString("borrowDate"));
      pstmt2.setString(4, LocalDate.now().toString());
      int rowsAffected = pstmt2.executeUpdate();
      if(rowsAffected == 0){
        System.out.println("\nError adding book to borrow history.\n");
        return;
      }

      pstmt3.setInt(1, bookId);
      rowsAffected = pstmt3.executeUpdate();
      if(rowsAffected == 0){
        System.out.println("\nError returning book.\n");
        return;
      }

      System.out.println("\nBook returned successfully.\n");
    } catch (SQLException e) {
      System.out.println("\nError returning book.\n");
      e.printStackTrace();
    }
  }

  public void viewCurrentlyBorrowed(int memberId){
    String q1 = "SELECT bookId, returnDueDate FROM currentlyBorrowed WHERE memberId = ?";
    String q2 = "SELECT title, author FROM books WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL); PreparedStatement pstmt1 =
            conn.prepareStatement(q1); PreparedStatement pstmt2 = conn.prepareStatement(q2)) {

      pstmt1.setInt(1, memberId);
      ResultSet rs1 = pstmt1.executeQuery();
      pstmt2.setInt(1, rs1.getInt("bookId"));
      ResultSet rs2 = pstmt2.executeQuery();

      System.out.printf("%-3s | %-18s | %-18s | %-12s%n", "ID", "Title", "Author", "Return Due");
      System.out.println("-----------------------------------------------------------");

      while (rs1.next()) {
        int bookId = rs1.getInt("bookId");
        String title = rs2.getString("title");
        String author = rs2.getString("author");
        String returnDueDate = rs1.getString("returnDueDate");

        System.out.printf("%-3d | %-18s | %-18s | %-12s%n",
                bookId, title, author, returnDueDate);
      }

    } catch (SQLException e) {
      System.out.println("\nError fetching currently borrowed books.\n");
      e.printStackTrace();
    }
  }

  public void viewBorrowingHistory(int memberId){
    String q1 = "SELECT bookId, borrowDate, returnDate FROM borrowHistory WHERE memberId = ?";
    String q2 = "SELECT title, author FROM books WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL); PreparedStatement pstmt1 =
            conn.prepareStatement(q1); PreparedStatement pstmt2 = conn.prepareStatement(q2)) {

      pstmt1.setInt(1, memberId);
      ResultSet rs1 = pstmt1.executeQuery();

      System.out.printf("%-18s | %-18s | %-12s | %-12s%n", "Title", "Author", "Borrow Date", "Return Date");
      System.out.println("-----------------------------------------------------------");

      while (rs1.next()) {
        pstmt2.setInt(1, rs1.getInt("bookId"));
        ResultSet rs2 = pstmt2.executeQuery();

        String bookTitle = rs2.getString("title") != null ? rs2.getString("title") : "Unknown";
        String bookAuthor = rs2.getString("author") != null ? rs2.getString("author") : "Unknown";
        String borrowDate = rs1.getString("borrowDate");
        String returnDate = rs1.getString("returnDate");

        System.out.printf("%-18s | %-18s | %-12s | %-12s%n",
                bookTitle, bookAuthor, borrowDate, returnDate);
      }

    } catch (SQLException e) {
      System.out.println("\nError fetching borrowing history.\n");
      e.printStackTrace();
    }
  }
}
