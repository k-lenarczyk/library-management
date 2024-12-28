package com.librarymanagement;

import com.librarymanagement.controller.ConsoleMenu;
import com.librarymanagement.persistence.SQLite;

import java.sql.Connection;

public class LibraryApp {

  public static void main(String[] args){
    Connection conn = SQLite.connect();
    if(conn == null){
      System.out.println("Error connecting to database. Exiting program.");
      System.exit(1);
    }

    ConsoleMenu menu = new ConsoleMenu();
    menu.displayMenu();
    SQLite.disconnect(conn);
  }

}
