package com.library.service;

import com.library.model.Admin;
import com.library.repository.AdminRepository;

public class AdminService{
    public Admin logInAdmin;
    public AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }
    public void login(String userName, String password){
        Admin admin = adminRepository.findByUserName(userName);
        if(admin!=null && admin.checkPassword(password)) {
            this.logInAdmin = admin;
            System.out.println("Login successful. Welcome, " + userName + "!");
            return;
        }
        System.out.println("Incorrect password or user name! Try again!");
    }
    public void assignAdminToLibrary(String name){
        if(logInAdmin == null){
            System.out.println("Access denied. Please log in first.");
            return;
        }
        System.out.println("Admin: " + logInAdmin.getUserName() +
                " assigned to library.");
    }
    public void logOut(){
        if(logInAdmin != null){
            System.out.println("Goodbye, " + logInAdmin.getUserName() + "!");
            logInAdmin = null;
        }
    }
    public void isLoggedIn(){
        if(logInAdmin != null) {
            System.out.println("You are online");
        }else{
            System.out.println("You are offline");
        }
    }

    public void manageLibraryDetails(){}
}
