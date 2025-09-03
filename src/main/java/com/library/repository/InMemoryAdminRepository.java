package com.library.repository;

import com.library.model.Admin;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAdminRepository implements AdminRepository {

    private Map<String,Admin> admins = new HashMap<>();

    public void save(Admin admin){
        admins.put(admin.getUserName(), admin);
    }

    public Admin findByUserName(String userName){
        return admins.get(userName);
    }

    public void delete(Admin admin){
        admins.remove(admin.getUserName());
    }
}
