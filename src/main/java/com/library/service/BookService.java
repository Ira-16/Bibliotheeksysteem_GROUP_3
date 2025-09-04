package com.library.service;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.repository.InMemoryBookRepository;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class BookService {

    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public static void main(String[] args) {
        BookRepository repo = new InMemoryBookRepository();
        BookService service = new BookService(repo);

        Book cleanCode = new Book("Clean Code", "Robert C. Martin", 2008, "9780132350884", 3);

        try {
            service.addBook(cleanCode);
            System.out.println("Book added successfully!");

            service.addBook(cleanCode);
            System.out.println("Second book added successfully!");
        } catch (IllegalArgumentException ex) {
            System.out.println("Error while adding book: " + ex.getMessage());
        }

        try {
            service.removeBook(cleanCode);
            System.out.println("Book removed successfully!");
        } catch (Exception e) {
            System.out.println("Error while removing: " + e.getMessage());
        }
    }

    public void addBook(Book book) {
        try {
            validateNewBook(book);
            if (repository.findByISBN(book.getISBN()).isPresent()) {
                throw new IllegalArgumentException("Book with this ISBN already exists: " + book.getISBN());
            }
            repository.save(book);
        } catch (IllegalArgumentException e) {
            System.out.println("Add failed: " + e.getMessage());
        }
    }

    public void removeBook(Book book) {
        try {
            repository.findByISBN(book.getISBN())
                    .orElseThrow(() -> new NoSuchElementException("Book not found: " + book.getISBN()));
            repository.delete(book);
        } catch (NoSuchElementException e) {
            System.out.println("Remove failed: " + e.getMessage());
        }
    }

    public void editBook(Book book) {
        try {
            repository.findByISBN(book.getISBN())
                    .orElseThrow(() -> new NoSuchElementException("Book not found: " + book.getISBN()));
            validateEditable(book);
            repository.save(book);
        } catch (IllegalArgumentException | NoSuchElementException e) {
            System.out.println("Edit failed: " + e.getMessage());
        }
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

    public Book searchBookByISBN(String isbn) {
        try {
            return repository.findByISBN(isbn)
                    .orElseThrow(() -> new NoSuchElementException("No book found with ISBN: \"" + isbn + "\""));
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
            return null;
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
        try {
            System.out.println("📋 All Books:");
            return repository.findAll();
        } catch (Exception e) {
            System.out.println("Error while listing all books: " + e.getMessage());
            return Collections.emptyList();
        }
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
