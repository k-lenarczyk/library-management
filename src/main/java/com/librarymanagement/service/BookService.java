package com.librarymanagement.service;

import com.librarymanagement.model.Book;
import com.librarymanagement.persistence.BookDAO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BookService {
  private final BookDAO bookDAO;
  private final Scanner scanner;

  public BookService() {
    this.bookDAO = new BookDAO();
    this.scanner = new Scanner(System.in);
  }

  public void addBook(){
    System.out.println("Enter Book Title: ");
    String title = scanner.nextLine();
    System.out.println("Enter Book Author: ");
    String author = scanner.nextLine();
    System.out.println("Enter Book Genre: ");
    String genre = scanner.nextLine();
    System.out.println("Enter Book ISBN: ");
    String isbn = scanner.nextLine();

    Book book = new Book(-1, title, author, genre, isbn, true, null, null);
    bookDAO.addBook(book);
  }

  public void updateBook(){
    System.out.println("Enter the id of the book you want to update: ");
    int bookId = scanner.nextInt();
    scanner.nextLine();

    Book bookToUpdate = bookDAO.findBookById(bookId);
    if(bookToUpdate == null) {
      System.out.println("Book not found.");
      return;
    }

    System.out.println("Enter new Title: ");
    String newTitle = scanner.nextLine();
    if(!newTitle.isEmpty()) {
      bookToUpdate.setTitle(newTitle);
    }
    System.out.println("Enter new Author: ");
    String newAuthor = scanner.nextLine();
    if(!newAuthor.isEmpty()) {
      bookToUpdate.setAuthor(newAuthor);
    }
    System.out.println("Enter new Genre: ");
    String newGenre = scanner.nextLine();
    if(!newGenre.isEmpty()) {
      bookToUpdate.setGenre(newGenre);
    }

    bookDAO.updateBook(bookToUpdate);
  }

  public void deleteBook() {
    System.out.print("Enter the id of the book you want to delete: ");
    int bookId = scanner.nextInt();
    scanner.nextLine();

    Book bookToDelete = bookDAO.findBookById(bookId);
    if(bookToDelete == null) {
      System.out.println("Book not found.");
      return;
    }
      bookDAO.deleteBook(bookToDelete);
  }

  public void viewAllBooks(){
    System.out.println();
    bookDAO.viewAllBooks();
  }

  public void viewOverdueBooks(){
    System.out.println();
    bookDAO.viewOverdueBooks();
  }

  // Search for book by title
  public void searchBook(){
    System.out.println("\nEnter the title of the book:");
    String searchedBook = scanner.nextLine();

    Book book = bookDAO.findBookByTitle(searchedBook);
    if(book == null) {
      System.out.println("Book not found.");
      return;
    }

    System.out.printf("%n%-3s | %-18s | %-18s | %-10s | %-9s%n", "ID", "Title", "Author", "Genre", "Available");
    System.out.println("-----------------------------------------------------------------------");
    System.out.printf("%-3d | %-18s | %-18s | %-10s | %-9s%n",
            book.getId(), book.getTitle(), book.getAuthor(), book.getGenre(), book.getAvailability() ? "Yes" : "No");
  }

  public void borrowBook(int memberId){
    System.out.println("Enter the title of the book you want to borrow: ");
    try {
      String bookTitle = scanner.nextLine();

      Book bookToBorrow = bookDAO.findBookByTitle(bookTitle);
      if (bookToBorrow == null) {
        System.out.println("Book not found.");
        return;
      }

      if (!bookToBorrow.getAvailability()) {
        System.out.println("The book isn't currently available.");
        return;
      }

      bookToBorrow.setAvailability(false);
      bookToBorrow.setBorrower(memberId);
      bookDAO.updateBook(bookToBorrow);
      bookDAO.borrowBook(bookToBorrow.getId(), memberId);
    } catch (InputMismatchException e) {
      System.out.println("\nInvalid input. Please enter a valid id.\n");
      scanner.nextLine();
    }
  }

  public void returnBook(int memberId){
    System.out.println("Enter the title of the book you want to return: ");
    String bookTitle = scanner.nextLine();

    Book bookToReturn = bookDAO.findBookByTitle(bookTitle);
    if(bookToReturn == null){
      System.out.println("Book not found.");
      return;
    } else if (bookToReturn.getBorrower() != memberId) {
      System.out.println("This book is not borrowed by you.");
      return;
    }
    bookToReturn.setAvailability(true);
    bookToReturn.setBorrower(null);
    bookToReturn.setReturnDueDate(null);
    bookDAO.returnBook(bookToReturn.getId(), memberId);
    bookDAO.updateBook(bookToReturn);
  }

  public void viewCurrentlyBorrowed(int memberId){
    System.out.println();
    bookDAO.viewCurrentlyBorrowed(memberId);
  }

  public void viewBorrowingHistory(int memberId){
    System.out.println();
    bookDAO.viewBorrowingHistory(memberId);
  }
}
