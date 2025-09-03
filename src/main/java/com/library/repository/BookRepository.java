package com.library.repository;

import com.library.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    void save(Book book);

    void delete(Book book);

    Optional<Book> findByISBN(String isbn);

    List<Book> findByTitle(String title);

    List<Book> findByAuthor(String author);

    List<Book> findByYear(int year);

    List<Book> findAll();
}
