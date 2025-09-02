package com.library;

import com.library.model.Admin;
import com.library.repository.AdminRepository;
import com.library.service.AdminService;

public class Main {
    public static void main(String[] args) {
        AdminRepository adminRepository = new AdminRepository();
        AdminService adminService = new AdminService(adminRepository);

        Admin admin = new Admin("Vika", "bebe123","V123");
        adminRepository.save(admin);

        adminService.login("Vika", "bebe12");
        adminService.login("Vika", "bebe123");

        adminService.assignAdminToLibrary("Vika");
        adminService.isLoggedIn();
        adminService.logOut();
        adminService.isLoggedIn();
        }
    }
