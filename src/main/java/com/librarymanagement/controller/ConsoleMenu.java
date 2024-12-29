package com.librarymanagement.controller;

import com.librarymanagement.model.Member;
import com.librarymanagement.service.BookService;
import com.librarymanagement.service.UserService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleMenu {

  private static final Scanner scanner = new Scanner(System.in);
  private static final UserService userService = new UserService();
  private static final BookService bookService = new BookService();

  // Main menu
  public void displayMenu() {
    while(true) {
      System.out.println("Welcome to the Library Management System");
      System.out.println("1. Login");
      System.out.println("2. Register as Member");
      System.out.println("3. Exit Program");
      System.out.print("Enter your choice: ");

      int choice = scanner.nextInt();
      scanner.nextLine();

      switch(choice) {
        case 1:
          loginMenu();
          break;
        case 2:
          userService.registerMember();
          break;
        case 3:
          System.exit(0);
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void loginMenu() {
    System.out.print("\nEnter User ID: ");
    try{
      int userId = scanner.nextInt();
      scanner.nextLine();
      // If the id is 0 login as librarian
      if(userId == 0) {
        showLibrarianMenu();
      } else {
        Member member = userService.authenticateMember(userId);

        if(member != null){
          showMemberMenu(member);
        } else {
          System.out.println("Invalid Member ID. Please try again.\n");
        }
      }
    } catch (InputMismatchException e) {
      System.out.println("Invalid User ID. Please try again.\n");
      scanner.nextLine();
    }
  }

  private void showLibrarianMenu(){
    while(true) {
      System.out.println("\n[Librarian Menu]");
      System.out.println("1. Add Book");
      System.out.println("2. Update Book");
      System.out.println("3. Delete Book");
      System.out.println("4. View All Books");
      System.out.println("5. View Overdue Books");
      System.out.println("6. View All Members");
      System.out.println("7. Delete Member");
      System.out.println("8. Logout");
      System.out.print("Enter your choice: ");
      int choice;

      try {
        choice = scanner.nextInt();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid choice. Please try again.");
        scanner.nextLine();
        continue;
      }

      switch(choice) {
        case 1:
          bookService.addBook();
          break;
        case 2:
          bookService.updateBook();
          break;
        case 3:
          bookService.deleteBook();
          break;
        case 4:
          bookService.viewAllBooks();
          break;
        case 5:
          bookService.viewOverdueBooks();
          break;
        case 6:
          userService.viewAllMembers();
          break;
        case 7:
          userService.deleteMember();
          break;
        case 8:
          System.out.println("Logging out...\n");
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }

  private void showMemberMenu(Member member) {
    while (true) {
      System.out.println("\n[Member Menu]");
      System.out.println("1. Search Books");
      System.out.println("2. Borrow Book");
      System.out.println("3. Return Book");
      System.out.println("4. View Currently Borrowed Books");
      System.out.println("5. View Borrowing History");
      System.out.println("6. Logout");
      System.out.print("Enter your choice: ");
      int choice;

      try {
        choice = scanner.nextInt();
        scanner.nextLine();
      } catch (InputMismatchException e) {
        System.out.println("\nInvalid choice. Please try again.");
        scanner.nextLine();
        continue;
      }

      switch (choice) {
        case 1:
          bookService.searchBook();
          break;
        case 2:
          bookService.borrowBook(member.getId());
          break;
        case 3:
          bookService.returnBook(member.getId());
          break;
        case 4:
          bookService.viewCurrentlyBorrowed(member.getId());
          break;
        case 5:
          bookService.viewBorrowingHistory(member.getId());
          break;
        case 6:
          System.out.println("Logging out...\n");
          return;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
    }
  }
}
