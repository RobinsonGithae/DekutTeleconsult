package com.example.dekutteleconsult.DataModel;

public class Chat {

    private String Sender=" ";
    private String Receiver=" ";
    private String message=" ";
    private boolean isseen;
    private String timestamp=" ";
    private String Chatid="";

    public Chat(String sender, String receiver, String message,boolean isseen, String time) {
        this.Sender = sender;
        this.Receiver = receiver;
        this.message = message;
        this.isseen=isseen;
        this.timestamp=time;
    }


    public Chat(String idchat, String sender, String receiver, String message,boolean isseen, String time) {
        this.Sender = sender;
        this.Receiver = receiver;
        this.message = message;
        this.isseen=isseen;
        this.timestamp=time;
        this.Chatid=idchat;
    }



    public Chat() {
        //default cnstructor
    }

    public String getChatid() {
        return Chatid;
    }

    public void setChatid(String chatid) {
        Chatid = chatid;
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
