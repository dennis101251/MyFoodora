package fr.ecp.IS1220.group5.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alexandre on 25/11/2016.
 */
public class MyFoodoraSystem {
    private Userlist users = new Userlist();
    private ArrayList<Order> orders;
    private double service_fee;
    private double markup_percentage;
    private double delivery_cost;
    private Scanner scanner = new Scanner(System.in);
    private User user;

    public MyFoodoraSystem() {

//        Load all registered the users
        retrieveUsers();
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

    public void addUser(User user) {
        this.users.addUser(user);
    }

    public void removeUser(String userName) throws UserNotFoundException {
        boolean isFound = false;
        User myUser = null;
        for (User user : users.getUsers()) {
            if (user.getUsername().equals(userName)) {
                myUser = user;
                isFound = true;
                break;
            }
        }

        if (isFound) {
            users.removeUser(myUser);
        } else {
            System.out.println("User: " + userName + " is not found in system");
        }

    }

    public void retrieveOrders() {

        //Verify whether the Order file exists

        File file = new File("/tmp/orders.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("tmp/orders.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);

                orders = (ArrayList<Order>) in.readObject();

                in.close();
                fileIn.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(">> There is no Order in system");
        }

    }

    public void retrieveUsers() {
        //The problem is that maybe there is no ser file at the beginning
        //Modify in userlist class

        this.users.retrieveUsers();

    }

    public void retrieveFinancial() {

        Financial financial = null;

        File file = new File("tmp/financial.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("tmp/financial.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);

                financial = (Financial) in.readObject();

                in.close();
                fileIn.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            service_fee = financial.service_fee;
            markup_percentage = financial.markup_percentage;
            delivery_cost = financial.delivery_cost;
        } else {
            System.out.println(">> There is no Financial in system");
        }
    }

    public User loginUser(String userName, String password) {

        boolean isFound = false;
        User myUser = null;
        for (User user: users.getUsers()) {
            if (user.getUsername().equals(userName)){
                myUser = user;
                isFound = true;
                break;
            }
        }

        if (isFound){
            try {
                if (PasswordHash.validatePassword(password,myUser.getPassword())){
                    this.user = myUser;
                    System.out.println("You have entered myFoodora!");
                    return myUser;
                }
                else {
                    System.out.println("Invalid password");
                    return null;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("User: " + userName + " is not found in system");
        }
        return null;
    }

    public static void main(String[] args) {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
    }
}

