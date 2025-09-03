package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;

import java.util.*;


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
        return repository.findByTitle(title);
    }


    public List<Book> searchBooksByAuthor(String author) {
        return repository.findByAuthor(author);
    }


    public Book searchBookByISBN(String isbn) {
        return repository.findByISBN(isbn)
                .orElseThrow(() -> new NoSuchElementException("Book not found: " + isbn));
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
