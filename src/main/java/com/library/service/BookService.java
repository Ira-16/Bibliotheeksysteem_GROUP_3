package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;


import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }


    public void addBook(Book book) {
        validateNewBook(book);
        if (repository.findByISBN(book.getISBN()).isPresent()) {
            throw new IllegalArgumentException("Book with this ISBN already exists: " + book.getISBN());
        }
        repository.save(book);
    }


    public void removeBook(Book book) {
        repository.findByISBN(book.getISBN())
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + book.getISBN()));
        repository.delete(book);
    }


    public void editBook(Book book) {
        repository.findByISBN(book.getISBN())
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + book.getISBN()));
        validateEditable(book);
        repository.save(book);
    }


    public List<Book> searchBooksByTitle(String title) {
        try {
            List<Book> books = repository.findByTitle(title);
            if (books.isEmpty()) {
                throw new NoSuchElementException("No books found with title containing: \"" + title + "\"");
            }
            return books;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }




    public List<Book> searchBooksByAuthor(String author) {
        try {
            List<Book> books = repository.findByAuthor(author);
            if (books.isEmpty()) {
                throw new NoSuchElementException("No books found by author: \"" + author + "\"");
            }
            return books;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }



//    public Book searchBookByISBN(String isbn) {
//        return repository.findByISBN(isbn)
//                .orElseThrow(() -> new NoSuchElementException("No books found with isbn containing: \"" + isbn + "\""));
//    }

    public Book searchBookByISBN(String isbn) {
        try {
            return repository.findByISBN(isbn)
                    .orElseThrow(() -> new NoSuchElementException("No book found with ISBN: \"" + isbn + "\""));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage()); // Replace with logger if needed
            return null; // Or throw a custom exception, or return a default Book
        }
    }



    public List<Book> searchByYear(int year) {
        try {
            List<Book> books = repository.findByYear(year);
            if (books.isEmpty()) {
                throw new NoSuchElementException("No books found published in the year: " + year);
            }

            return books;
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }



    public List<Book> listAll() {
        System.out.println("📋 All Books:");
        return repository.findAll();
    }

    private void validateNewBook(Book b) {
        if (b == null) throw new IllegalArgumentException("Book is null");
        if (isBlank(b.getISBN())) throw new IllegalArgumentException("ISBN is required");
        if (isBlank(b.getTitle())) throw new IllegalArgumentException("Title is required");
        if (isBlank(b.getAuthor())) throw new IllegalArgumentException("Author is required");
        if (b.getAvailableCopies() < 0) throw new IllegalArgumentException("Number of copies cannot be negative");
    }

    private void validateEditable(Book b) {
        if (b.getAvailableCopies() < 0) {
            throw new IllegalArgumentException("Number of copies cannot be negative");
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
