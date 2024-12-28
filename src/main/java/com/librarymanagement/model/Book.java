package com.librarymanagement.model;

import java.time.chrono.ChronoLocalDate;

public class Book {

  private final int id;
  private String title;
  private String author;
  private String genre;
  private final String isbn;
  private boolean isAvailable;
  private Integer borrower;
  private ChronoLocalDate returnDueDate;

  public Book(int id, String title, String author, String genre, String isbn, boolean isAvailable,
              Integer borrower, ChronoLocalDate returnDueDate) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.genre = genre;
    this.isbn = isbn;
    this.isAvailable = isAvailable;
    this.borrower = borrower;
    this.returnDueDate = returnDueDate;
  }

  public int getId(){
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author){
    this.author = author;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getIsbn() {
    return isbn;
  }

  public boolean getAvailability() {
    return isAvailable;
  }

  public void setAvailability(boolean isAvailable) {
    this.isAvailable = isAvailable;
  }

  public Integer getBorrower(){
    return borrower;
  }

  public void setBorrower(Integer borrower){
    this.borrower = borrower;
  }

  public ChronoLocalDate getReturnDueDate(){
    return returnDueDate;
  }

  public void setReturnDueDate(ChronoLocalDate returnDueDate){
    this.returnDueDate = returnDueDate;
  }

}
