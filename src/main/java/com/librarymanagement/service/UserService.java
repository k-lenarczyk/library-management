package com.librarymanagement.service;

import com.librarymanagement.model.Member;
import com.librarymanagement.persistence.UserDAO;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UserService {

  private final UserDAO userDAO;
  private final Scanner scanner;

  public UserService() {
    this.userDAO = new UserDAO();
    this.scanner = new Scanner(System.in);
  }

  // Authenticate a member
  public Member authenticateMember(int memberId){
    Member member = userDAO.findMemberById(memberId);
    if(member != null){
      System.out.println("\nWelcome " + member.getName() + "!");
      return member;
    } else {
      System.out.println("Invalid Member ID. Please try again.\n");
      return null;
    }
  }

  // Register a member
  public void registerMember(){
    System.out.println("\nEnter Member Name: ");
    String name = scanner.nextLine();
    if(name.isEmpty()){
      System.out.println("Name cannot be empty.");
      return;
    }

    Member member = new Member(-1, name);
    userDAO.addMember(member);

    if(member.getId() == -1) {
      System.out.println("\nMember registered successfully, but no ID was returned.\n");
      return;
    }
    System.out.println("Your Member ID is: " + member.getId() + "\nRemember it!\n");
  }

  // Delete a member
  public void deleteMember(){
    System.out.println("\nEnter id of member to be deleted: ");
    try {
      int memberId = scanner.nextInt();
      scanner.nextLine();

      Member member = userDAO.findMemberById(memberId);
      if(member == null) {
        System.out.println("Member not found.");
        return;
      }

      userDAO.deleteMember(member);
    } catch (InputMismatchException e) {
      System.out.println("Invalid input. Please enter a valid id.");
      scanner.nextLine();
      return;
    }
  }

  // View all members
  public void viewAllMembers(){
    System.out.println();
    userDAO.viewAllMembers();
  }
}
