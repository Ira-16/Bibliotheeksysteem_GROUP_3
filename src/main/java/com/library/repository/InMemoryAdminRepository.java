package com.library.repository;

import com.library.model.Admin;
import com.library.model.Librarian;
import com.library.model.Member;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAdminRepository implements UserRepository<Admin> {

    private final Map<String,Admin> admins = new HashMap<>();

    public void save(Admin admin){
        admins.put(admin.getUserName(), admin);
    }

    public Admin findByUserName(String userName){
        return admins.get(userName);
    }

    public void delete(Admin admin){
        admins.remove(admin.getUserName());
    }
    public void delete(Librarian librarian){

    }

    public void delete(Member member){

    }

}
