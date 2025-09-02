package com.library;

import com.library.model.Admin;
import com.library.repository.AdminRepository;
import com.library.service.AdminService;
import com.library.ui.LibraryManagementUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LibraryManagementUI::new);
    }}

