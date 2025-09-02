package com.library.repository;

import com.library.model.Admin;

public interface AdminRepository {
    void save(Admin admin);

    void delete(Admin admin);

    Admin findByUserName(String userName);
}

