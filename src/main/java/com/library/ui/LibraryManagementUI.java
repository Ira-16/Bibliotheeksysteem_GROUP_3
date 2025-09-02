package com.library.ui;

import com.library.model.Admin;
import com.library.repository.InMemoryAdminRepository;
import com.library.service.AdminService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LibraryManagementUI extends JFrame {
    private final AdminService adminService;
    private JMenuItem loginItem;
    private JMenuItem logoutItem;
    private JMenu adminMenu;

    public LibraryManagementUI(){
        setTitle("📚 Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null); //in the middle?

        //our admin
        InMemoryAdminRepository adminRepo = new InMemoryAdminRepository();
        adminRepo.save(new Admin("Victoria", "Vica123", "V001"));
        this.adminService = new AdminService(adminRepo);

        //menu
        JMenuBar menuBar = new JMenuBar();

        //menu for admin
        adminMenu = new JMenu("Admin");
        loginItem = new JMenuItem("Login");
        logoutItem = new JMenuItem("Logout");
        logoutItem.setEnabled(false);
        adminMenu.add(loginItem);
        adminMenu.add(logoutItem);


        //book menu
        JMenu bookMenu = new JMenu("Books");
        JMenuItem addBookItem = new JMenuItem("Add Book");
        JMenuItem searchBookItem = new JMenuItem("Search Book");

        //member menu
        JMenu memberMenu = new JMenu("Members");
        JMenuItem addMemberItem = new JMenuItem("Register Member");
        memberMenu.add(addMemberItem);

        //loan menu
        JMenu loanMenu = new JMenu("Loans");
        JMenuItem lendBookItem = new JMenuItem("Lend Book");
        JMenuItem returnBookItem = new JMenuItem("Return Book");
        loanMenu.add(lendBookItem);
        loanMenu.add(returnBookItem);

        //all menu's
        menuBar.add(adminMenu);
        menuBar.add(bookMenu);
        menuBar.add(memberMenu);
        menuBar.add(loanMenu);

        setJMenuBar(menuBar);

        //check later for login
        loginItem.addActionListener((ActionEvent e) -> showAdminLogin());

        logoutItem.addActionListener(e->{
            adminService.logOut();
            JOptionPane.showMessageDialog(this, "Admin logged out.");
            logoutItem.setEnabled(false);
        });


        setVisible(true);
    }
    private void showAdminLogin(){
        JPanel panel = new JPanel(new GridLayout(2,2));
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        panel.add(new JLabel("Username:"));
        panel.add(userField);
        panel.add(new JLabel("Password:"));
        panel.add(passField);

        int check = JOptionPane.showConfirmDialog(
                this, panel, "Admin Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if(check == JOptionPane.OK_OPTION){
            String username = userField.getText();
            String password = new String(passField.getPassword());

            adminService.login(username, password);

            if(adminService.isLoggedIn()){
                Admin admin = adminService.getLogInAdmin();
                JOptionPane.showMessageDialog(this, "Welcome, " + admin.getUserName() + "!");
                logoutItem.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect username or password", "Login Faild", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
