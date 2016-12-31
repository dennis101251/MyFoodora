package fr.ecp.IS1220.group5.project.util;

import java.io.Serializable;

/**
 * Created by dennis101251 on 2016/12/31.
 */
public class Message implements Serializable{
    private String title;
    private String author;
    private String message;
    private boolean isStared = false;

    public Message(String title, String author, String message) {
        this.title = title;
        this.author = author;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String showMessage(){
        String string;

        string = "Title: " + title + "\n";
        string += "From: " + author + "\n";
        string += "Message: " + message ;
        return string;
    }

    public void star(){
        if (!isStared){
            isStared = true;
        }
        else {
            isStared = false;
        }
    }

    public boolean isStared() {
        return isStared;
    }
}
