package com.library.repository;

import com.library.model.Book;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {
    private final Map<String, Book> storage = new HashMap<>();
    @Override
    public void save(Book book) {
        storage.put(book.getISBN(), book);
    }

    @Override
    public void delete(Book book) {
        storage.remove(book.getISBN());
    }

    @Override
    public Optional<Book> findByISBN(String isbn) {
        return Optional.ofNullable(storage.get(isbn));
    }

    @Override
    public List<Book> findByTitle(String title) {
        String needle = title == null ? "" : title.toLowerCase();
        return storage.values().stream()
                .filter(b -> b.getTitle() != null && b.getTitle().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(Book::getTitle, Comparator.nullsLast(String::compareToIgnoreCase)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        String needle = author == null ? "" : author.toLowerCase();
        return storage.values().stream()
                .filter(b -> b.getAuthor() != null && b.getAuthor().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(Book::getAuthor, Comparator.nullsLast(String::compareToIgnoreCase)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}

