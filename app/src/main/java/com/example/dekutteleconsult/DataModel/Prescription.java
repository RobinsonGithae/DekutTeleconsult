package com.example.dekutteleconsult.DataModel;

public class Prescription {
    private String prescriptionID;
    private String patientID;
    private String patientName;
    private String IssuingDate;
    private int uptakeDuration;
    private int noOfMedines;


    public Prescription(String prescriptionID, String patientID, String patientName, String issuingDate, int uptakeDuration, int noOfMedines) {
        this.prescriptionID = prescriptionID;
        this.patientID = patientID;
        this.patientName = patientName;
        IssuingDate = issuingDate;
        this.uptakeDuration = uptakeDuration;
        this.noOfMedines = noOfMedines;
    }

    public Prescription() {
    }

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getIssuingDate() {
        return IssuingDate;
    }

    public void setIssuingDate(String issuingDate) {
        IssuingDate = issuingDate;
    }

    public int getUptakeDuration() {
        return uptakeDuration;
    }

    public void setUptakeDuration(int uptakeDuration) {
        this.uptakeDuration = uptakeDuration;
    }

    public int getNoOfMedines() {
        return noOfMedines;
    }

    public void setNoOfMedines(int noOfMedines) {
        this.noOfMedines = noOfMedines;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}
