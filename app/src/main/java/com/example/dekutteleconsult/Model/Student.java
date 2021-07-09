package com.example.dekutteleconsult.Model;

public class Student extends User {

    private String fullName="";
    private String regNo="";
    private String yearOfStudy="";
    private String gender="";
    private String DOBirth="";
    private String email="";
    private String phoneNo="";



    public Student(String id, String Username, String imageURL, String status, String isDoctor, String fullName, String regNo, String yearOfStudy, String gender, String DOBirth, String email, String phoneNo) {
        super(id, Username, imageURL, status, isDoctor);
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
}
