package com.example.dekutteleconsult.DataModel;

public class Chat {

    private String Sender=" ";
    private String Receiver=" ";
    private String message=" ";
    private boolean isseen;
    private String timestamp=" ";

    public Chat(String sender, String receiver, String message,boolean isseen, String time) {
        this.Sender = sender;
        this.Receiver = receiver;
        this.message = message;
        this.isseen=isseen;
        this.timestamp=time;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
