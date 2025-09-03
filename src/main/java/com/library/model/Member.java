package com.library.model;

public class Member {
    private String membershipId; //everywhere we have to use Long or String
    private String name;
    private int age;
    private String contactDetails;

    public Member(){}
    // Constructor
    public Member(String membershipId, String name, int age, String contactDetails) {
        this.membershipId = membershipId;
        this.name = name;
        this.age = age;
        this.contactDetails = contactDetails;
    }

    // Getters and Setters
    public String getMembershipId() {return membershipId;}
    public void setMembershipId(String membershipId) {this.membershipId = membershipId;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {
        if(age<0) throw new IllegalArgumentException("Age can not be negative!"); //logical
        this.age = age;}

    public String getContactDetails() {return contactDetails;}
    public void setContactDetails(String contactDetails) {this.contactDetails = contactDetails;}

    @Override
    public String toString() {
        return "Member Info: " + membershipId + " - " + name + ", " + age +
                " years old. Contact Details: " + contactDetails;
    }
}
