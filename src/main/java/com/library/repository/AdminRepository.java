package com.library.repository;

import com.library.model.Admin;

public interface AdminRepository {
    void save(Admin user);

   void delete(Admin user);

    Admin findByUserName(String userName);
}

