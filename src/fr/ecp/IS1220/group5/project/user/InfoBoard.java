package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.util.Message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <b>The class that displays and stores the notifications of a Customer.</b>
 *
 * @see Customer
 */
public class InfoBoard implements Serializable{

    /**
     * The stored message of the info-board.
     */
    protected ArrayList<Message> messages = new ArrayList<>();

    /**
     * where the Customer will receive his notification ("email" ord "phone").
     */
    protected String contactType = "email";

    /**
     * the email of the Customer/
     */
    protected String email;

    /**
     * the phone number of the Customer.
     */
    protected String phone;

    /**
     * the number of new messages. 0 if there isn't any new message.
     */
    protected Integer numberOfNewMeassages = 0;

    /**
     * the attribute to show whether this infobaord can be notified by restaurant
     */
    protected Boolean isNotified = true;

    /**
     * Returns the email of the Customer.
     * @return the email of the Customer.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the Customer.
     * @param email the email of the Customer.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the phone number of the Customer.
     * @return the phone number of the Customer.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Returns the number of new messages.
     * @return the number of new messages.
     */
    public Integer getNumberOfNewMeassages(){
        return numberOfNewMeassages;
    }

    /**
     * Sets the phone number of the Customer.
     * @param phone the phone number of the Customer.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Returns the list of all stored messages.
     * @return the list of all sored messages.
     */
    public ArrayList<Message> getMessages() {
        numberOfNewMeassages = 0;
        return messages;
    }

    /**
     * Adds a message to the info-board.
     * @param message the message to add to the info-board.
     */
    public void addMessage(Message message){
        numberOfNewMeassages = numberOfNewMeassages + 1;
        this.messages.add(message);
    }

    /**
     * Sets the contact type to "email"
     */
    public void setContactType_Email(){this.contactType = "email";}

    /**
     * Sets the contact type to "phone"
     */
    public void setContactType_Phone(){this.contactType = "phone";}

    /**
     * Returns the contact type ("email" or "phone")
     * @return the contact type ("email" or "phone")
     */
    public String getContactType(){
        return contactType;
    }

    /**
     * Enables ths system to send notifications to the Customers.
     */
    public void setNotified_On(){
        this.isNotified = true;
    }

    /**
     *  Turns off the notifications. The Customer won't receive messages any more.
     */
    public void setNotified_Off(){
        this.isNotified = false;
    }

    /**
     * @return the state of info board
     */
    public Boolean isNotified(){
        return isNotified;
    }

    /**
     * delete the message
     */
    public void deleteMessage(int index){
        messages.remove(index - 1);
//        System.out.println(messages.size());
    }

    /**
     * update the message
     */
    public void updateMessage(Message newMessage){
        messages.remove(newMessage);
        messages.add(newMessage);
    }

    /**
     * delete a message
     */
    public void deleteMessage(Message message){
        messages.remove(message);
    }
}
