package com.example.dekutteleconsult.DataModel;

public class Medicine {

    private String drugName;
    private String drugType;
    private String drugDoseUnit;
    private String drugDosage;
    private String drugIntakeDuration;
    private String drugInstruction;


    public Medicine(String drugName, String drugType, String drugDoseUnit, String drugDosage, String drugIntakeDuration, String drugInstruction) {
        this.drugName = drugName;
        this.drugType = drugType;
        this.drugDoseUnit = drugDoseUnit;
        this.drugDosage = drugDosage;
        this.drugIntakeDuration = drugIntakeDuration;
        this.drugInstruction = drugInstruction;
    }

    public Medicine() {
    }


    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugDoseUnit() {
        return drugDoseUnit;
    }

    public void setDrugDoseUnit(String drugDoseUnit) {
        this.drugDoseUnit = drugDoseUnit;
    }

    public String getDrugDosage() {
        return drugDosage;
    }

    public void setDrugDosage(String drugDosage) {
        this.drugDosage = drugDosage;
    }

    public String getDrugIntakeDuration() {
        return drugIntakeDuration;
    }

    public void setDrugIntakeDuration(String drugIntakeDuration) {
        this.drugIntakeDuration = drugIntakeDuration;
    }

    public String getDrugInstruction() {
        return drugInstruction;
    }

    public void setDrugInstruction(String drugInstruction) {
        this.drugInstruction = drugInstruction;
    }
}
