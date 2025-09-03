package com.library.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.model.Book;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBookRepository implements BookRepository {

    private final File file = new File("books.json");
    private final ObjectMapper mapper = new ObjectMapper();

    private final Map<String, Book> storage = new HashMap<>();

    public InMemoryBookRepository(){
        load();
    }

    private void load(){
        if(file.exists()){
            try{
                List<Book> list = mapper.readValue(file, new TypeReference<List<Book>>() {});
                storage.clear();
                list.forEach((b->storage.put(b.getISBN(), b)));
            }catch(IOException e){
                throw new RuntimeException("Error! Can not download list of books");
            }
        }
    }

    private void saveFile(){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, storage.values());
        }catch (IOException e){
            throw new RuntimeException("Error! Can not save books");
        }
    }
    @Override
    public void save(Book book) {
        storage.put(book.getISBN(), book);
        saveFile();
    }

    @Override
    public void delete(Book book) {
        storage.remove(book.getISBN());
        saveFile();
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
    public List<Book> findByYear(int year) {
        return storage.values().stream()
                .filter(b->b.getPublishedYear() == year)
                .toList();
    }


    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}

