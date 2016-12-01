package fr.ecp.IS1220.group5.project;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigDecimal;
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
    private User user = null;

    public MyFoodoraSystem() {

//        Load all registered the users
        retrieveUsers();
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

    public User getUser(String userName){
        for (User user : users.getUsers()){
            if (user.getName().equalsIgnoreCase(userName)){
                return user;
            }
        }

        return null;
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

    public void loginUser(String userName, String password) {

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
                }
                else {
                    System.out.println("Invalid password");
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
    }



    public void registerRestaurant(String name, String username, Coordinate address, String password) {
        User newRestaurant = new Restaurant(name, username, password, address);
        this.users.addUser(newRestaurant);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }


    public void registerCustomer(String firstName, String lastName, String username, Coordinate address, String password) {
        User newCustomer = new Customer(firstName, lastName, username, address, password);
        this.users.addUser(newCustomer);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }


    public void createItem(String itemName, BigDecimal price){
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Item item = new Item(itemName, price, ItemCategory.MainDish, ItemType.Standard);
            restaurant.addItem(item);

            System.out.println(item + " was successfully created!");

        } else {

            System.out.println("Your restaurant must be logged in to create a item.");

        }
    }

    public void createMeal(String mealName) {
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Meal meal = new Meal(mealName);
            restaurant.addMeal(meal);

            System.out.println(meal + " was successfully created!");

        } else {

            System.out.println("Your restaurant must be logged in to create a meal.");

        }
    }

    public void addDish2Meal(String itemName, String mealName) {
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Meal meal = restaurant.getMeal(mealName);
            Item item = restaurant.getItem(itemName);
            meal.addItem(item);

            System.out.println(item + " was successfully added to " + meal + "!");

        } else {

            System.out.println("Your restaurant must be logged in to add a dish to a meal.");

        }
    }

    public void saveMenu() {

        if (user instanceof Restaurant){

            users.saveUsers();

            System.out.println("Your menu was successfully saved!");

        } else {

            System.out.println("Your restaurant must be logged in to save the menu.");

        }

    }

    public void showMeal(String restaurantName, String mealName){
        Restaurant restaurant = (Restaurant) getUser(restaurantName);
        Meal meal = restaurant.getMeal(mealName);
        System.out.println(meal);
    }

    public void saveMeal(String mealName){

    }

    public void setMealPrice(String mealName){
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Meal meal = restaurant.getMeal(mealName);
            restaurant.setMealPrice(meal);

        } else {
            System.out.println("Your restaurant must be logged in to set the price of a meal.");
        }
    }

    public void setSpecialOffer(String mealName){

    }

    public void removeFromSpecialOffer(String mealName){

    }

    public void addDish(String dishName, String dishCategory, BigDecimal unitPrice){

    }

    public void addMeal2Order(String mealName){

    }

    public void endOrder(){

    }

    public void onDuty(String username){

    }

    public void offDuty(String username){

    }

    public void addContactInfo(String contactInfo){

    }

    public void associateCard(String userName, String cardType){

    }

    public void associateAgreement(String username, String agreement){

    }
}

