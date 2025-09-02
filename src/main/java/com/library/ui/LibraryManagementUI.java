package com.library.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LibraryManagementUI extends JFrame {

    public LibraryManagementUI(){
        setTitle("📚 Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null); //in the middle?

        //menu
        JMenuBar menuBar = new JMenuBar();

        //menu for admin
        JMenu adminMenu = new JMenu("Admin");
        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem logoutItem = new JMenuItem("Logout");
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

            //for adminService
            System.out.println("Log in as: " + username + ", password: " + password);
        }
    }
}
