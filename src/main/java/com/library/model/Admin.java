package com.library.model;

public class Admin {
    private String userName;
    private String password;
    private String employeeId;

    public Admin(String userName, String password, String employeeId) {
        this.userName = userName;
        this.password = password;
        this.employeeId = employeeId;
    }

    public String getUserName() {return userName;}
    public String getEmployeeId() {return employeeId;}

    public boolean checkPassword(String inputPassword){
        return this.password.equals(inputPassword);
    }

}
