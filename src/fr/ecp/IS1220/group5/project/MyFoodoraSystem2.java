package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Alexandre on 25/11/2016.
 */
public class MyFoodoraSystem2 {
    private Userlist users = new Userlist();
    private ArrayList<Order> orders;
    private double service_fee;
    private double markup_percentage;
    private double delivery_cost;
    Scanner scanner = new Scanner(System.in);

    public MyFoodoraSystem2() {

//        Load all registered the users
        retrieveUsers();
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

    public void registerUser() throws IOException {
        //Use factory methods to produce a new User

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        do {
            System.out.println("Which kind of USER you want to register? \n" +
                    "Customer:      (1) \n" +
                    "Manager:       (2) \n" +
                    "Courier:       (3) \n" +
                    "Restaurant:    (4) \n" +
                    "Enter the number to continue, press (N) to return the main menu");

            str = br.readLine();

            //Switch to different userFactory
            if (str.equals("1")){

                //Customer
                CustomerFactory customerFactory = new CustomerFactory();
                User newCustomer = customerFactory.createUser();
                if (newCustomer == null){
                    //User cancel the registering process
                    continue;
                }
                else{
                    this.users.addUser(newCustomer);
                    System.out.println("You have been registered successfully!");
                    System.out.println("======================================");
                    br.close();
                    break;
                }
            }
            else if (str.equals("2")){

                //Manager
                ManagerFactory managerFactory = new ManagerFactory();
                User newManager = managerFactory.createUser();
                if (newManager == null){
                    //User cancel the registering process
                    continue;
                }
                else{
                    this.users.addUser(newManager);
                    System.out.println("You have been registered successfully!");
                    System.out.println("======================================");
                    br.close();
                    break;
                }
            }
            else if (str.equals("3")){

                //Courier
                CourierFactory courierFactory = new CourierFactory();
                User newCourier = courierFactory.createUser();
                if (newCourier == null){
                    //User cancel the registering process
                    continue;
                }
                else{
                    this.users.addUser(newCourier);
                    System.out.println("You have been registered successfully!");
                    System.out.println("======================================");
                    br.close();
                    break;
                }
            }
            else if (str.equals("4")){

                //Restaurant
                RestaurantFactory restaurantFactory = new RestaurantFactory();
                User newRestaurant = restaurantFactory.createUser();
                if (newRestaurant == null){
                    //User cancel the registering process
                    continue;
                }
                else{
                    this.users.addUser(newRestaurant);
                    System.out.println("You have been registered successfully!");
                    System.out.println("======================================");
                    br.close();
                    break;
                }
            }
            else if (str.equalsIgnoreCase("N")){
                //User cancel the registering process
                System.out.println(" *** >> Cancel the process  *** ");
                System.out.println("================================");
                break;
            }
            else {
                System.out.println(" *** >> Invalid typing *** ");
                System.out.println("===========================");
                continue;
            }

        }while (!str.equals("N"));


    }

    public void registerCustomer(String firstName, String lastName, String username, Coordinate address, String password)  {
        User newCustomer = new Customer(firstName, lastName, username, address, password);
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

        File file = new File("/Users/dennis101251/IdeaProjects/MyFoodora/tmp/financial.ser");
        if (file.exists()){
            try {
                FileInputStream fileIn = new FileInputStream("/Users/dennis101251/IdeaProjects/MyFoodora/tmp/financial.ser");
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

    public void loginUser(String userName, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {

        boolean isFound = false;
        User myUser = null;
        for (User user: users.getUsers()
                ) {
            if (user.getUsername().equals(userName)){
                myUser = user;
                isFound = true;
                break;
            }
        }

        if (isFound){
            if (PasswordHash.validatePassword(password,myUser.getPassword())){
                System.out.println("You have entered myFoodora!");
            }
            else {
                System.out.println("Invalid password");
            }
        }
        else {
            System.out.println("User: " + userName + " is not found in system");
        }

    }

    public static void main(String[] args) {

        System.out.println("Welcome to MyFoodora!");

        MyFoodoraSystem2 myFoodoraSystem2 = new MyFoodoraSystem2();
        myFoodoraSystem2.run();

    }


    public void run(){
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                case "login":
                    try {
                        loginUser(commands[1], commands[2]);
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                    break;
                case "createMeal":
                    break;
                case "addDish2Meal":
                    break;
                case "showMeal":
                    break;
                case "saveMeal":
                    break;
                case "setMealPrice":
                    break;
                case "setSpecialOffer":
                    break;
                case "removeFromSpecialOffer":
                    break;
                case "addDish":
                    break;
                case "addMeal2Order":
                    break;
                case "endOrder":
                    break;
                case "registerClient":
                    break;
                case "registerCourrier":
                    break;
                case "onDuty":
                    break;
                case "offDuty":
                    break;
                case "addContactInfo":
                    break;
                case "associateCard":
                    break;
                case "associateAgreement":
                    break;
                case "registerRestaurant":
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

    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
