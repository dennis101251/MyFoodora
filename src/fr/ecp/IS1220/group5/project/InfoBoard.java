package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dennis101251 on 2016/12/5.
 */
public class InfoBoard implements Serializable{
    private ArrayList<String> messages = new ArrayList<>();
    private Boolean allMessagesAreRead = false;
    private String contactType = "email";
    private String email;
    private String phone;
    private Integer numberOfNewMeassages = 0;
    private Boolean isNotified = true;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getNumberOfNewMeassages(){
        return numberOfNewMeassages;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<String> getMessages() {
        numberOfNewMeassages = 0;
        return messages;
    }

    public void addMessage(String message){
        numberOfNewMeassages = numberOfNewMeassages + 1;
        this.messages.add(message);
    }

    public Boolean getAllMessagesAreRead() {
        return allMessagesAreRead;
    }

    public void setAllMessagesAreRead(Boolean allMessagesAreRead) {
        this.allMessagesAreRead = allMessagesAreRead;
    }

    public void setContactType_Email(){this.contactType = "email";}
    public void setContactType_Phone(){this.contactType = "phone";}

    public String getContactType(){
        return contactType;
    }

    public void setNotified_On(){
        this.isNotified = true;
    }

    public void setNotified_Off(){
        this.isNotified = false;
    }

    public Boolean isNotified(){
        return isNotified;
    }
}
