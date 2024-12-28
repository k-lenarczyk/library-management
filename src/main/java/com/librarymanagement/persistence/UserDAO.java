package com.librarymanagement.persistence;

import com.librarymanagement.model.Member;

import java.sql.*;

public class UserDAO {

  private static final String DATABASE_URL = "jdbc:sqlite:src/main/resources/db/library.db";

  public Member findMemberById(int memberId) {
    String query = "SELECT * FROM members WHERE id = ?";
    try (Connection connection = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = connection.prepareStatement(query)) {
      pstmt.setInt(1, memberId);
      ResultSet rs = pstmt.executeQuery();
      if (rs.next()) {
        return new Member(rs.getInt("id"), rs.getString("name"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  public void addMember(Member member) {
    String query = "INSERT INTO members (name) VALUES (?)";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setString(1, member.getName());

      pstmt.executeUpdate();
      System.out.println("\nMember registered successfully.\n");
    } catch (SQLException e) {
      System.out.println("\nError registering member.\n");
      e.printStackTrace();
    }
  }

  public void deleteMember(Member member){
    String query = "DELETE FROM members WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         PreparedStatement pstmt = conn.prepareStatement(query)) {

      pstmt.setInt(1, member.getId());
      int rowsAffected = pstmt.executeUpdate();

      if(rowsAffected > 0){
        System.out.println("\nMember deleted successfully.\n");
      } else {
        System.out.println("\nMember not found.\n");
      }

    } catch (SQLException e) {
      System.out.println("\nError deleting member.\n");
      e.printStackTrace();
    }
  }

  public void viewAllMembers() {
    String query = "SELECT id, name FROM members";

    try (Connection conn = DriverManager.getConnection(DATABASE_URL);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

      System.out.printf("%-3s | %-18s%n", "ID", "Member Name");
      System.out.println("----------------------");

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        System.out.printf("%-3d | %-18s%n", id, name);
      }

    } catch (SQLException e) {
      System.out.println("Error fetching members.");
      e.printStackTrace();
    }
  }

}
