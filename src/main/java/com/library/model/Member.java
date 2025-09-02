package com.library.model;

public class Member {
    private String name;
    private int age;
    private String membershipId;
    private String contactDetails;
    private String membershipType;
    public Member() {}

    public Member(String name, int age, String membershipId, String contactDetails, String membershipType) {
        this.name = name;
        this.age = age;
        this.membershipId = membershipId;
        this.contactDetails = contactDetails;
        this.membershipType = membershipType;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }
}
