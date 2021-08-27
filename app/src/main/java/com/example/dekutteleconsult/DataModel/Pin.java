package com.example.dekutteleconsult.DataModel;

public class Pin {
    private  String pinValue="";

    public Pin(String pinValue) {
        this.pinValue = pinValue;
    }

    public Pin() {
    }

    public String getPinValue() {
        return pinValue;
    }

    public void setPinValue(String pinValue) {
        this.pinValue = pinValue;
    }
}
