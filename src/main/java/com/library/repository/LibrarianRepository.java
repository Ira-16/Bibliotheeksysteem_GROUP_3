package com.library.repository;

import com.library.model.Librarian;
import java.util.HashMap;
import java.util.Map;

public class LibrarianRepository implements UserRepository <Librarian>{

    private final Map<String, Librarian> librarian = new HashMap<>();
    @Override
    public void save(Librarian user) {
    }
    @Override
    public Librarian findByUserName(String userName) {
        return null;
    }
}
