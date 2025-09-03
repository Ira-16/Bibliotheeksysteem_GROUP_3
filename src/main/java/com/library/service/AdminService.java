package com.library.service;

import com.library.model.Admin;
import com.library.model.repository.AdminRepository;

public class AdminService{
    private Admin logInAdmin;
    private final AdminRepository AdminRepository;

    public AdminService(AdminRepository adminRepository){
        this.AdminRepository = adminRepository;
    }

    public Admin getLogInAdmin() {return logInAdmin;}

    public void login(String userName, String password){
        Admin admin = AdminRepository.findByUserName(userName);
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
    public boolean isLoggedIn(){
        if(logInAdmin != null) {
            System.out.println("You are online");
            return true;
        }else{
            System.out.println("You are offline");
        }
        return false;
    }

    public void manageLibraryDetails(){
        if(logInAdmin == null){
            System.out.println("Access denied. Please log in first.");
            return;
        }
        System.out.println("Library details management is currently not implemented.");
    }
}
