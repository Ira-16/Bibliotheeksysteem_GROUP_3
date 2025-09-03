package com.library.repository;

import com.library.model.Admin;

public interface UserRepository <T>{
    void save(T user);

    default void delete(T user){
        throw new UnsupportedOperationException("Only for admin");
    }

    T findByUserName(String userName);
}

