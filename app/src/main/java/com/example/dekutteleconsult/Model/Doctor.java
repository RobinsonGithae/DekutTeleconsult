package com.example.dekutteleconsult.Model;

import java.util.Date;

public class Doctor extends User {
    private String DoctorID;
  private   String Designation;

    public Doctor(String id, String Username, String imageURL, String status, String email, String isDoctor, String doctorID, String designation) {
        super(id, Username, imageURL, status, email, isDoctor);
        DoctorID = doctorID;
        Designation = designation;
    }

    public String getDoctorID() {
        return DoctorID;
    }

    public void setDoctorID(String doctorID) {
        DoctorID = doctorID;
    }

    public String getDesignation() {
        return Designation;
    }

    public void setDesignation(String designation) {
        Designation = designation;
    }
}
