package com.example.dekutteleconsult.Model;

public class Emergency {


  // private String userid;
   private Double latitude;
   private Double longitude;
   private String emergencystatus;
    private String emergencyID;

    public Emergency(Double latitude, Double longitude, String emergencystatus,String emergencyID) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.emergencystatus = emergencystatus;
        this.emergencyID=emergencyID;
    }

    public Emergency() {
    }

    public String getEmergencyID() {
        return emergencyID;
    }

    public void setEmergencyID(String emergencyID) {
        this.emergencyID = emergencyID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getEmergencystatus() {
        return emergencystatus;
    }

    public void setEmergencystatus(String emergencystatus) {
        this.emergencystatus = emergencystatus;
    }
}

