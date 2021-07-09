package com.example.dekutteleconsult.Model;

public class Chat {

    private String Sender=" ";
    private String Receiver=" ";
    private String message=" ";
    private boolean isseen;

    public Chat(String sender, String receiver, String message,boolean isseen) {
        this.Sender = sender;
        this.Receiver = receiver;
        this.message = message;
        this.isseen=isseen;
    }

    public Chat() {
        //default cnstructor
    }

    public boolean isIsseen() {
        return isseen;
    }

    public void setIsseen(boolean isseen) {
        this.isseen = isseen;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
