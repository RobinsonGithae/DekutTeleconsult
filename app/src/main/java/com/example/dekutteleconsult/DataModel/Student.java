package com.example.dekutteleconsult.DataModel;

public class Student extends User {

    private String fullName="";
    private String regNo="";
    private String yearOfStudy="";
    private String gender="";
    private String DOBirth="";
    private String email="";
    private String phoneNo="";



    public Student(String id, String Username, String imageURL, String status, String isDoctor, String fullName, String regNo, String yearOfStudy, String gender, String DOBirth, String email, String phoneNo) {
        super(id, Username, imageURL, status, email, isDoctor);
        this.fullName = fullName;
        this.regNo = regNo;
        this.yearOfStudy = yearOfStudy;
        this.gender = gender;
        this.DOBirth = DOBirth;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Student(String fullName, String regNo, String yearOfStudy, String gender, String DOBirth, String email, String phoneNo) {
        this.fullName = fullName;
        this.regNo = regNo;
        this.yearOfStudy = yearOfStudy;
        this.gender = gender;
        this.DOBirth = DOBirth;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Student(){
        //DEFAULT CONSTUCTOR
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(String yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDOBirth() {
        return DOBirth;
    }

    public void setDOBirth(String DOBirth) {
        this.DOBirth = DOBirth;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
