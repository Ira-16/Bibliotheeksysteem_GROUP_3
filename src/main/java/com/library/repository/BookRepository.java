package com.library.repository;

import com.library.model.Book;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookRepository {
    private final Map<String, Book> byIsbn = new ConcurrentHashMap<>();

    public void save(Book b) {
        byIsbn.put(b.getISBN(), b);
    }

    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(byIsbn.get(isbn));
    }

    public List<Book> findAll() {
        return new ArrayList<>(byIsbn.values());
    }
}
