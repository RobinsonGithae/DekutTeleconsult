package com.example.dekutteleconsult.Model;

import java.util.Date;

public class User {


    private String id;
    private  String Username;
    private String imageURL;
    private  String status;
    //private String Email;
    //private Date DateOfBirth;
    private String isDoctor;

    public User(String id, String Username, String imageURL, String status,String isDoctor) {
        this.id = id;
        this.Username = Username;
        this.imageURL = imageURL;
        this.status=status;
        this.isDoctor=isDoctor;
        //this.Email = email;
        //DateOfBirth = dateOfBirth;
    }

    public User() {
        //default constructor
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public  String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsDoctor() {
        return isDoctor;
    }

    public void setIsDoctor(String isDoctor) {
        this.isDoctor = isDoctor;
    }

    //end of user model
}

