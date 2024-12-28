package com.librarymanagement.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite {
  public static Connection connect(){
    Connection conn = null;
    try{
      String url = "jdbc:sqlite:src/main/resources/db/library.db";
      conn = DriverManager.getConnection(url);
      System.out.println("Connection to SQLite has been established.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
    return conn;
  }

  public static void disconnect(Connection conn){
    if(conn != null){
      try{
        conn.close();
        System.out.println("Connection to SQLite has been closed.");
      } catch (SQLException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
