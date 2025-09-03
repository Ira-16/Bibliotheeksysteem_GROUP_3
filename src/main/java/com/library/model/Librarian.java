package com.library.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class Librarian {
    private String userName;
    private String password;
    private String employeeId;

    public Librarian(){}
    public Librarian(String userName, String password, String employeeId) {
        this.userName = userName;
        this.password = password;
        this.employeeId = employeeId;
    }

    public String getUserName() {return userName;}
    public String getEmployeeId() {return employeeId;}

    public boolean checkPassword(String inputPassword){
        return Objects.equals(this.password, hashPassword(inputPassword));
    }
    private String hashPassword(String password){
        try{
            MessageDigest mb = MessageDigest.getInstance("SHA-256");
            byte[] hashed = mb.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashed){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e){
            throw new RuntimeException("SHA-256 not available");
        }
    }
}
