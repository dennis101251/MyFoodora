package fr.ecp.IS1220.group5.project;

import java.io.*;
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
    private User user;

    public MyFoodoraSystem() {

//        Load all registered the users
        retrieveUsers();
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

    public void registerCustomer(String firstName, String lastName, String username, Coordinate address, String password)  {
        User newCustomer = new Customer(firstName, lastName, username, address, password);
        this.users.addUser(newCustomer);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }

    private void registerRestaurant(String name, String username, Coordinate address, String password) {
        User newCustomer = new Restaurant(name, username, password, address);
        this.users.addUser(newCustomer);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }

    public void registerCourier(String firstName, String lastName, String username, Coordinate position, String password)  {
        User newCustomer = new Courier(firstName, lastName, username, position, password);
        this.users.addUser(newCustomer);
        System.out.println("You have been registered successfully!");
        System.out.println("======================================");
    }

    public void retrieveOrders(){

        //Verify whether the Order file exists

        File file = new File("/tmp/orders.ser");
        if (file.exists()){
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
        }
        else {
            System.out.println(">> There is no Order in system");
        }

    }

    public void retrieveUsers(){
        //The problem is that maybe there is no ser file at the beginning
        //Modify in userlist class

        this.users.retrieveUsers();

    }

    public void retrieveFinancial(){

        Financial financial = null;

        File file = new File("tmp/financial.ser");
        if (file.exists()){
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
        }
        else {
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

    public static void main(String[] args) {

        System.out.println("Welcome to MyFoodora!");

        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        myFoodoraSystem.run();

    }


    public void run(){
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                case "login":
                    loginUser(commands[1], commands[2]);
                    break;
                case "createMeal":
                    createMeal(commands[1]);
                    break;
                case "addDish2Meal":
                    addDish2Meal(commands[1], commands[2]);
                    break;
                case "showMeal":
                    showMeal(commands[1]);
                    break;
                case "saveMeal":
                    saveMeal(commands[1]);
                    break;
                case "setMealPrice":
                    setMealPrice(commands[1]);
                    break;
                case "setSpecialOffer":
                    setSpecialOffer(commands[1]);
                    break;
                case "removeFromSpecialOffer":
                    removeFromSpecialOffer(commands[1]);
                    break;
                case "addDish":
                    addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                    break;
                case "addMeal2Order":
                    addMeal2Order(commands[1]);
                    break;
                case "endOrder":
                    endOrder();
                    break;
                case "registerCourrier":
                    break;
                case "onDuty":
                    onDuty(commands[1]);
                    break;
                case "offDuty":
                    offDuty(commands[1]);
                    break;
                case "addContactInfo":
                    addContactInfo(commands[1]);
                    break;
                case "associateCard":
                    associateCard(commands[1], commands[2]);
                    break;
                case "associateAgreement":
                    associateAgreement(commands[1], commands[2]);
                    break;
                case "registerRestaurant":
                    registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]),commands[4]);
                    break;
                case "notifySpecialOffer":
                    break;
                case "registerCustomer":
                    registerCustomer(commands[1], commands[2], commands[3], String2Coordinate(commands[4]), commands[5]);
                    break;
                case "help":
                    System.out.println("List of available commands:");
                    break;
                default:
                    System.out.println("Unvalid command. Type help to check the available commands.");
                    break;
            }

        }
    }

    public void createMeal(String mealName) {
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Meal meal = new Meal(mealName);
            restaurant.addMeal(meal);

            System.out.println(meal + " was successfully created!");

        } else {

            System.out.println("Your resurant must be logged in to create a meal.");

        }
    }

    public void addDish2Meal(String dishName, String mealName) {

    }

    public void showMeal(String mealName){

    }

    public void saveMeal(String mealName){

    }

    public void setMealPrice(String mealName){

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

    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
