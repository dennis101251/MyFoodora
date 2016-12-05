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
    private ArrayList<Order> orders = new ArrayList<Order>();
    private double service_fee;
    private double markup_percentage;
    private double delivery_cost;
    private Scanner scanner = new Scanner(System.in);
    private User user = null;
    private Restaurant currentRestaurant = null;
    private Order currentOrder = null;

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

        if (user == null){
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
        else {
            System.out.println("you have to disconnect first");
        }
    }

    public User getCurrentUser(){
        return user;
    }

    public void disconnectUser(){
        System.out.println(user.getName() + " has logged out");
        this.user = null;
    }

    public void registerRestaurant(String name, String username, Coordinate address, String password) {
        User newRestaurant = new Restaurant(name, username, password, address);
        this.users.addUser(newRestaurant);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }

    public void registerCustomer(String firstName, String lastName, String username, String password, Coordinate address, String mail, String phone) {
        User newCustomer = new Customer(firstName, lastName, username, password, address, mail, phone);
        this.users.addUser(newCustomer);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }

    public void showRestaurant(){
        if (user instanceof Customer){
            ArrayList<Restaurant> allReastaurant= new ArrayList<>();
            for (User user: users.getUsers()
                 ) {
                if (user instanceof Restaurant){
                    allReastaurant.add((Restaurant) user);
                }
            }

            for (Restaurant restaurant: allReastaurant
                 ) {
                System.out.println(restaurant.getName());
            }
        }
        else {

            System.out.println("You must log in first");

        }
    }

    public void chooseRestaurant(String restaurant){
        if (user instanceof Customer){
            boolean isFound = false;
            for (User user: users.getUsers()
                 ) {
                if (user.getName().equalsIgnoreCase(restaurant) && user instanceof Restaurant){
                    isFound = true;
                    currentRestaurant = (Restaurant) user;
                    currentOrder = new Order(currentRestaurant);
                    System.out.println("you have entered: " + restaurant);
                    break;
                }
            }
            if (!isFound){
                System.out.println(restaurant + " is not found");
            }
        }
        else {

            System.out.println("You must log in first");

        }
    }

    //Show the all the available options to the customer
    public void showMenu(){
        if (user instanceof Customer){
            if (currentRestaurant != null){
                System.out.println(">> items");
                for (Item item: currentRestaurant.getItems()){
                    System.out.println(item.getName() +": "+ item.getPrice());
                }
                System.out.println(">> meals");
                for (Meal meal:currentRestaurant.getMeals()){
                    System.out.println(meal.getName() +": "+ meal.getPrice());
                }
            }
            else {
                System.out.println("you have to choose a restaurant first");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void addMeal2Order(String mealName, Integer quantity){
        if (user instanceof Customer){
            if (currentOrder != null){
                boolean isFound = false;
                for (Meal meal: currentRestaurant.getMeals()
                     ) {
                    if (meal.getName().equalsIgnoreCase(mealName)){
                        isFound = true;

                        for (int i = 0; i < quantity; i++) {
                            currentOrder.addMeal(meal);
                        }

                        System.out.println("you have add " + mealName + " x" + quantity +" successfully");

                        System.out.println("Order: " + Money.display(currentOrder.getTotal_price()));
                        break;
                    }
                }
                if (!isFound){
                    System.out.println(">>" + mealName + " is not found");
                }
            }
            else {
                System.out.println("you have to choose a restaurant first");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void addItem2Order(String itemName, Integer quantity){
        if (user instanceof Customer){
            if (currentOrder != null){
                boolean isFound = false;
                for (Item item: currentRestaurant.getItems()
                        ) {
                    if (item.getName().equalsIgnoreCase(itemName)){
                        isFound = true;
                        for (int i = 0; i < quantity; i++) {
                            currentOrder.addItem(item);
                        }

                        System.out.println("you have add " + itemName +" x" + quantity + " successfully");
                        System.out.println("Order: " + Money.display(currentOrder.getTotal_price()));
                        break;
                    }
                }
                if (!isFound){
                    System.out.println(">>" + itemName + " is not found");
                }
            }
            else {
                System.out.println("you have to choose a restaurant first");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void showOrder(){
        if (user instanceof Customer){
            if (currentOrder != null){
                currentOrder.showOrder();
                System.out.println(Money.display(currentOrder.getTotal_price()));
            }
            else {
                System.out.println("you have to choose a restaurant first");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void endOrder(){
        if (user instanceof Customer){
            if (currentOrder != null){
                if (!currentOrder.isEmpty()){
                    currentOrder.showOrder();
                    System.out.println("Total: " + Money.display(currentOrder.getTotal_price()));
                    this.orders.add(currentOrder);
                    ((Customer) user).addOrderToHistory(currentOrder);
                    currentOrder = null;
                }
                else {
                    System.out.println("you have to choose an item first");
                }
            }
            else {
                System.out.println("you have to choose a restaurant first");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void showHistoryOfOrder_Customer(){
        if (user instanceof Customer){
            if (!((Customer) user).getHistoryOfOrder().isEmpty()){
                for (int i = 0; i < ((Customer) user).getHistoryOfOrder().size(); i++) {
                    System.out.println(">> " + (i+1) + ") Order");
                    ((Customer) user).getHistoryOfOrder().get(i).showOrder();
                }
            }
            else {
                System.out.println("Your history of order is empty");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void showHistoryOfOrder_System(){
        if (user instanceof Manager){
            if (!orders.isEmpty()){
                for (int i = 0; i < ((Customer) user).getHistoryOfOrder().size(); i++) {
                    System.out.println(">> " + (i+1) + ") Order");
                    orders.get(i).showOrder();
                }
            }
            else {
                System.out.println("The history of order is empty");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void setNotified(String string){
        if (user instanceof Customer) {
            if (string.equalsIgnoreCase("on")){
                ((Customer) user).infoBoard.setNotified_On();
                System.out.println("You can receive messages from Myfoodora by " + ((Customer) user).infoBoard.getContactType());
            }
            else if (string.equalsIgnoreCase("off")){
                ((Customer) user).infoBoard.setNotified_Off();
                System.out.println("you will not receive any message from myFoodora");
            }
            else{
                System.out.println(">>invalid command");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void checkInfoBoard(){
        if (user instanceof Customer) {
            if (((Customer) user).infoBoard.isNotified()){
                Integer num = ((Customer) user).infoBoard.getNumberOfNewMeassages();
                ArrayList<String> messages = ((Customer) user).infoBoard.getMessages();
                if (messages.isEmpty()){
                    System.out.println("You have no message");
                }
                else {
                    if ( num > 1){
                        System.out.println("You have " + num + " new messages");
                    }
                    else {
                        System.out.println("You have " + num + " new message");
                    }
                    for (int i = 0; i < messages.size(); i++) {
                        if (i < num ){
                            System.out.println(">> new message: " + messages.get(i));
                        }
                        else {
                            System.out.println(">> " + messages.get(i));
                        }
                    }
                }
            }
            else {
                System.out.println("You can't receive message from myFoodora");
                System.out.println("Please set infoBoard notified on");
            }
        }
        else {
            System.out.println("You must log in first");
        }
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

    //
    public void sendMessage(String string){
        if (user instanceof Restaurant){
            for (User user: users.getUsers()
                 ) {
                if (user instanceof Customer) {
                    if (((Customer) user).infoBoard.isNotified()){
                        ((Customer) user).infoBoard.addMessage(string);
                    }
                }
            }
        } else {
            System.out.println("You have to log in");
        }
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

