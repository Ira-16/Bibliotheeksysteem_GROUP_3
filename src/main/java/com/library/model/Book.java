package com.library.model;

import java.util.Objects;

public class Book {
    private String String;
    private String author;
    private String publishedYear;
    private String ISBN;
    private int availableCopies;

    public Book() {
    }

    public Book(String string, String author, String publishedYear, String ISBN, int availableCopies) {
        String = string;
        this.author = author;
        this.publishedYear = publishedYear;
        this.ISBN = ISBN;
        this.availableCopies = availableCopies;
    }

    public String getString() {
        return String;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishedYear() {
        return publishedYear;
    }

    public String getISBN() {
        return ISBN;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setString(String string) {
        String = string;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishedYear(String publishedYear) {
        this.publishedYear = publishedYear;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }


    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Book book)) return false;
        return availableCopies == book.availableCopies && Objects.equals(String, book.String) && Objects.equals(author, book.author) && Objects.equals(publishedYear, book.publishedYear) && Objects.equals(ISBN, book.ISBN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(String, author, publishedYear, ISBN, availableCopies);
    }

    @Override
    public String toString() {
        return "Book{" +
                "String='" + String + '\'' +
                ", author='" + author + '\'' +
                ", publishedYear='" + publishedYear + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", availableCopies=" + availableCopies +
                '}';
    }
}
