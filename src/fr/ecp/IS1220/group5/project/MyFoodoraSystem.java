package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.fidelity.BasicFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.LotteryFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.PointFidelityCard;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.*;
import fr.ecp.IS1220.group5.project.util.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <b>MyFoodoraSystem is the class managing the whole system.</b>
 * <p>
 *     This class stores:
 *</p>
 *     <ul>
 *         <li>The registered users (managers, restaurants, customers and couriers)</li>
 *         <li>The history of completed orders</li>
 *         <li>Profit-related information</li>
 *     </ul>
 *
 *
 * @author alexandre01, dennis101251
 * @version 2.0
 */
public class MyFoodoraSystem {

    /**
     * The list that stores the list of registered users.
      */
    protected Userlist users = new Userlist();

    /**
     * The list of orders terminated
     */
    protected ArrayList<Order> orders = new ArrayList<Order>();

    /**
     *The fixed service fee that adds to the order price
     * @see Manager#changeServiceFee(BigDecimal)
     * @see Financial
     */
    public BigDecimal service_fee = new BigDecimal("0.95");

    /**
     * The ratio of markup of the order price
     *@see Manager#changeMarkup_percentage(BigDecimal)
     *@see Financial
     */
    public BigDecimal markup_percentage = new BigDecimal("0.1");

    /**
     * the price of delivery per kilometer which the system should pay for the courier
     * @see Courier
     * @see Manager#changeDelivery_cost(BigDecimal)
     * @see Financial
     */
    public BigDecimal delivery_cost_price = new BigDecimal("1.0");

    //User
    /**
     * The user which has been logged in
     */
    protected User currentUser = null;

    /**
     * The restaurant chosen by the customer
     */
    protected Restaurant currentRestaurant = null;

    /**
     * temporary order stored in the system
     */
    protected Order currentOrder = null;

    //Current financial
    protected BigDecimal total_income = new BigDecimal("0");
    protected BigDecimal total_delivery_cost = new BigDecimal("0");
    protected BigDecimal total_profit = new BigDecimal("0");
    protected BigDecimal target_profit = new BigDecimal("0");
    protected BigDecimal averageIncomePerCustomer = new BigDecimal(0);

    //Only used for the ManagerDashboard
    protected BigDecimal tmpService_fee = new BigDecimal("0");
    protected BigDecimal tmpMarkup_percentage = new BigDecimal("0");
    protected BigDecimal tmpDelivery_cost_price = new BigDecimal("0");

    private static MyFoodoraSystem onlySystem;

    /**
     * <b>Delivery policy: </b>
     * <ul>
     *     <li>fastest delivery policy</li>
     *     <li>fair-occupation delivery policy</li>
     * </ul>
     * <p>by default: fast delivery</p>
     *
     */
    protected String deliveryPolicy = "fastDelivery";

    /**
     * target profit policy
     */
    protected int profitPolicy = 0;

    /**
     * The constructor of the system
     *
     * initialize the system from the serialization file
     *
     * @see MyFoodoraSystem#retrieveUsers()
     * @see MyFoodoraSystem#retrieveOrders()
     * @see MyFoodoraSystem#retrieveFinancial()
     */
    public MyFoodoraSystem(){
        retrieveUsers();
        retrieveOrders();
        retrieveFinancial();

        //Create the initial manager (if not already exists)
        if (getUser("ceo") == null) {
            User initialManager = new Manager("Mark", "ceo", "123456789", "Zuckerberg");
            this.users.addUser(initialManager);
            this.users.saveUsers();
        }

//        System.out.println(orders.toString());
//        System.out.println(users.toString());
        System.out.println("=================================");
        System.out.println("       init successfully         ");
        System.out.println("=================================");
    }

    /**
     * Use the singleton pattern
     */
    public static MyFoodoraSystem getIstanceOfSysteme(){
        if (onlySystem == null){
            onlySystem = new MyFoodoraSystem();
            return onlySystem;
        }
        else {
            return onlySystem;
        }
    }

    public void cleanHistory(){
        users = new Userlist();
        orders = new ArrayList<>();

        currentUser = null;
        currentRestaurant = null;
        currentOrder = null;

        System.out.println("Your system has been reinitialized");
    }

    public BigDecimal getMarkup_percentage() {
        return markup_percentage;
    }

    public BigDecimal getDelivery_cost_price() {
        return delivery_cost_price;
    }

    public Restaurant getCurrentRestaurant() {
        return currentRestaurant;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public BigDecimal getService_fee() {
        return service_fee;
    }

    /**
     * Returns the user with a given username (if he exists)
     *
     * @param userName The username of the searched user
     *
     * @return The user, as a User instance
     *
     * @see User
     */
    public User getUser(String userName){
        for (User user : users.getUsers()){
            if (user.getUsername().equalsIgnoreCase(userName)){
                System.out.println("found "+ userName);
                return user;
            }
        }
        System.out.println("didn't find " + userName + ", your username is valid");
        return null;
    }

    /**
     * get all clients(Customer Restaurant Courier)
     */
    public ArrayList<User> getAllClients(){

        ArrayList<User> userArrayList = new ArrayList<>();
        for (User user: this.users.getUsers()){
            if (!(user instanceof Manager)){
                userArrayList.add(user);
            }
        }
        return userArrayList;
    }

    /**
     * get the current User
     */
    public User getCurrentUser(){
        return currentUser;
    }

    /**
     * Add a user to the list of registered users.
     * @param user the user to add in the list.
     */
    public void addUser(User user) {
        this.users.addUser(user);
    }

    /**
     *Update user's state
     */
    public void updateUser(User user) throws UserNotFoundException {
        users.updateUser(user);
    }

    /**
     * Update order's state
     */
    public void updateOrder(Order order){
        orders.remove(order);
        orders.add(order);
        saveOrders();
    }

    /**
     * Add a user to system
     * @param order
     */
    public void addOrder(Order order){
        this.orders.add(order);
        saveOrders();
    }

    /**
     * Use order to find an order to edit
     * @param ID
     * @return
     */
    public Order findOrer(int ID){
        for (Order order: orders
             ) {
            if (order.getId() == ID){
                return order;
            }
        }
        System.out.println("This order is not found in the system");
        return null;
    }

    /**
     * Removes the user with a given name from the Userlist
     * @param userName
     * @throws UserNotFoundException
     *
     * @see MyFoodoraSystem#users
     */
    public void removeUser(String userName) throws UserNotFoundException {
        if (currentUser instanceof Manager){
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
        else {
            System.out.println("You must log in first");
        }

    }

    /**
     * save the user
     */
    public void saveUser(){
        users.saveUsers();
    }

    /**
     * get the number of all clients(Customer, Restaurant, Courier)
     */
    public int getNumOfAllCLients(){
        int num = 0;
        for (User user: users.getUsers()){
            if (!(user instanceof Manager)){
                num++;
            }
        }
        return num;
    }

    /**
     * Retrieves the stored orders from a .ser file and updates the orders variable
     *
     * @see MyFoodoraSystem#orders
     *
     */
    public void retrieveOrders() {

        //Verify whether the Order file exists

        File file = new File("src/tmp/orders.ser");

        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("src/tmp/orders.ser");
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

    /**
     * Saves the current users in a .ser file, in order to be retrieved in the future.
     *
     * @see MyFoodoraSystem#retrieveOrders()
     */
    public void saveOrders(){
        try {
            FileOutputStream fileOut = new FileOutputStream("src/tmp/orders.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(orders);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves the registered users in the users list.
     */
    public void retrieveUsers() {

        this.users.retrieveUsers();

    }

    /**
     * Retrieves the financal parameters. There are 3 variables:
     * <ul>
     *     <li>The service fee</li>
     *     <li>The markup percentage</li>
     *     <li>The delivery cost</li>
     * </ul>
     */
    public void retrieveFinancial() {

        Financial financial = null;

        File file = new File("src/tmp/financial.ser");
        if (file.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream("src/tmp/financial.ser");
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
            delivery_cost_price = financial.delivery_cost;
            profitPolicy = financial.profitPolicy;
        } else {
            System.out.println(">> There is no Financial in system");
        }
    }

    /**
     * save the financial parameter
     */
    public void saveFinancial(){
        try {
            FileOutputStream fileOut = new FileOutputStream("src/tmp/financial.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            Financial financial = new Financial();
            financial.service_fee = this.service_fee;
            financial.delivery_cost = this.delivery_cost_price;
            financial.markup_percentage = this.markup_percentage;
            financial.profitPolicy = this.profitPolicy;

            out.writeObject(financial);

            out.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * To login a user
     * @param userName the username of the user
    * @param password the password of the user
     */
    public void loginUser(String userName, String password) {

        boolean isFound = false;
        User myUser = null;

        if (currentUser == null){
            for (User user: users.getUsers()) {
                if (user.getUsername().equals(userName)){
                    myUser = user;
                    isFound = true;
                    break;
                }
            }

            if (isFound){
                if (myUser.getStatus()){
                    try {
                        if (PasswordHash.validatePassword(password,myUser.getPassword())){
                            this.currentUser = myUser;
                            System.out.println( myUser.getName() + ": welcome to myFoodora!");
                            System.out.println("==============================================");
                            loginInformation();
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
                    System.out.println("Your account has been disactivated, please contact Manager");
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

    /**
     * the information show at every time user login
     */
    public void loginInformation(){
        if (currentUser instanceof Customer ){
            checkInfoBoard();
        }
        else if (currentUser instanceof Restaurant){
            showOrdersOfRestaurant();
            System.out.println("==============================================");
        }
        else if (currentUser instanceof Manager){
            showHistoryOfOrder_System();
            System.out.println("==============================================");
        }
        else if (currentUser instanceof Courier){
            if (((Courier) currentUser).getNewOrderCondition()){
                System.out.println("You have a NEW order to deliver");
                System.out.println("accept/refuse");
            }
            else {
                System.out.println("There is no order to deliver");
            }
        }
        else {
            System.out.println("error");
        }
    }

    /**
     * Prints the currently logged in user.
     */
    public void showCurrentUser(){
        if (currentUser != null){
            System.out.println("Current User is: " + currentUser.getName());
        }
        else {
            System.out.println("there is no user connected in myFoodora");
        }
    }

    /**
     * Logs out the currently logged in user.
     */
    public void disconnectUser(){
        if (currentUser!=null){
            System.out.println(currentUser.getName() + " has logged out");
            this.currentUser = null;
        }
        else {
            System.out.println("there is no user connected in system");
        }
    }

    /**
     * Registers a new Restaurant user.
     *
     * @param name the name of the Restaurant.
     * @param username the username of the Restaurant.
     * @param address the address of the Restaurant.
     * @param password the password of the Restaurant.
     */
    public int registerRestaurant(String name, String username, Coordinate address, String password) {
        if (currentUser instanceof Manager){
            if (getUser(username) == null){
                User newRestaurant = new Restaurant(name, username, password, address);
                this.users.addUser(newRestaurant);
                System.out.println("You have been registered successfully!");
                System.out.println("======================================");
                return 0;
            }
            else{
                System.out.println("this username is not valid");
                return 1;
            }
        }
        else {
            System.out.println("Only a logged-in manager can register new users.");
            return 1;
        }
    }

    /**
     * Registers a new Customer user.
     *
     * @param firstName the first name of the Customer.
     * @param lastName the last name of the Customer.
     * @param username the username of the Customer.
     * @param password the password of the Customer.
     * @param address the address of the Customer.
     * @param mail the email of the Customer.
     * @param phone the phone nulmber of the Customer.
     */
    public int registerCustomer(String firstName, String lastName, String username, String password, Coordinate address, String mail, String phone) {
        if (currentUser instanceof Manager){
            if (getUser(username) == null){
                User newCustomer = new Customer(firstName, lastName, username, password, address, mail, phone);
                this.users.addUser(newCustomer);
                System.out.println("You have been registered successfully!");
                System.out.println("======================================");
                return 0;
            }
            else{
                System.out.println("this username is not valid");
                return 1;
            }
        }
        else {
            System.out.println("Only a logged-in manager can register new users.");
            return 1;
        }
    }

    /**
     * Registers a new Manager user.
     *
     * @param name the name of the Manager.
     * @param lastName the last name of the Manager.
     * @param username the username of the Manager.
     * @param password the password of the Manager.
     */
    public int registerManager(String name, String lastName, String username, String password){
        if (currentUser instanceof Manager){
            if (getUser(username) == null){
                User newManager = new Manager(name, username, password, lastName);
                this.users.addUser(newManager);
                System.out.println("You have been registered successfully!");
                System.out.println("======================================");
                return 0;
            }
            else{
                System.out.println("this username is not valid");
                return 1;
            }
        }
        else {
            System.out.println("Only a logged-in manager can register new users.");
            return 1;
        }

    }

    /**
     * Register a new Courier user.
     *
     * @param firstname the name of the Courier
     * @param lastname the last name of the Courier
     * @param username the username of the Courier
     * @param password the password of the Courier
     * @param address the current position of the Courier
     * @param phone the phone number of the Courier
     */
    public int registerCourier(String firstname, String lastname, String username, String password, Coordinate address, String phone){
        if (currentUser instanceof Manager){
            if (getUser(username) == null){
                User newCourier = new Courier(firstname,username,password,lastname,address,phone);
                this.users.addUser(newCourier);
                System.out.println("You have been registered successfully!");
                System.out.println("======================================");
                return 0;
            }
            else{
                System.out.println("this username is not valid");
                return 1;
            }
        }
        else {
            System.out.println("Only a logged-in manager can register new users.");
            return 1;
        }
    }

    /**
     * Computes the financial parameters:
     * <ul>
     *     <li>The total income</li>
     *     <li>The total delivery cost</li>
     *     <li>The total profit</li>
     * </ul>
     */
    protected void calculateFinancial(){
        //Total income
        BigDecimal money =new BigDecimal("0");
        for (Order order: orders
                ) {
            money = money.add(order.getTotal_price());
        }
        this.total_income = money;

        //Total delivery cost
        money = BigDecimal.valueOf(0);
        for (Order order: orders
                ) {
            money = money.add(order.getDelivery_cost());
        }
        this.total_delivery_cost = money;

        total_profit = total_income.subtract(total_delivery_cost);
    }

    /**
     *
     * Prints whether the user with given username exists or not in the database.
     * a method for manager to verify one user's existence
     *
     * @param userName the username of the searched user.
     */
    public void findUser(String userName){
        if (currentUser instanceof Manager){
            if (getUser(userName) == null){
                System.out.println("There is no " + userName +" in myFoodora");
            }
            else{
                System.out.println(">> " + userName + " has been found in myFoodora");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * get the list of registered customer
     */
    public void showAllCustomers(){
        if (currentUser instanceof Manager){
            ArrayList<Customer> customers = new ArrayList<>();
            for (User user: users.getUsers()){
                if (user instanceof Customer){
                    customers.add((Customer) user);
                }
            }
            if (customers != null){
                int i = 0;
                for (Customer customer: customers){
                    i++;
                    System.out.println( i + ") " + customer.getName());
                }
            }
            else {
                System.out.println("There is no customer in the system");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * get the list of registered restaurant
     */
    public void showAllRestaurants(){
        if (currentUser instanceof Manager){
            ArrayList<Restaurant> restaurants = new ArrayList<>();
            for (User user: users.getUsers()){
                if (user instanceof Restaurant){
                    restaurants.add((Restaurant) user);
                }
            }
            if (restaurants != null){
                int i = 0;
                for (Restaurant restaurant: restaurants){
                    i++;
                    System.out.println( i + ") " + restaurant.getName());
                }
            }
            else {
                System.out.println("There is no restaurant in the system");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * get the list of registered courier
     */
    public void showAllCouriers(){
        if (currentUser instanceof Manager){
            ArrayList<Courier> couriers = new ArrayList<>();
            for (User user: users.getUsers()){
                if (user instanceof Courier){
                    couriers.add((Courier) user);
                }
            }
            if (couriers != null){
                int i = 0;
                for (Courier courier: couriers){
                    i++;
                    System.out.println( i + ") " + courier.getName());
                }
            }
            else {
                System.out.println("There is no restaurant in the system");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Enables a manger to disactivate the account of a given user.
     * This user can not login but the account still remains in the system
     *
     * @param userName the username of the user to disactivate.
     * @throws UserNotFoundException
     */
    public void disactivateUser(String userName) throws UserNotFoundException {
        if (currentUser instanceof Manager){
            if (getUser(userName) == null){
                System.out.println("There is no " + userName +" in myFoodora");
            }
            else{
                users.disactivateUser(getUser(userName));
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Enables a manger to activate the account of a given user.
     * This user can use his account again
     *
     * @param userName the username of the user to activate.
     * @throws UserNotFoundException
     */
    public void activateUser(String userName) throws UserNotFoundException {
        if (currentUser instanceof Manager){
            if (getUser(userName) == null){
                System.out.println("There is no " + userName +" in myFoodora");
            }
            else{
                users.activateUser(getUser(userName));
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Sets the service fee.
     * Settable only by <b>managers</b>.
     *
     * @param service_fee
     *
     * @see MyFoodoraSystem#service_fee
     */
    public void setService_fee(BigDecimal service_fee){
        if (currentUser instanceof Manager){
            if  (!service_fee.equals(new BigDecimal("0"))){
                this.service_fee = service_fee;
                System.out.println(">> Current service fee: " + service_fee);
            }
            else {
                System.out.println("service fee should be positive");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Sets the markup percentage.
     * Settable only by managers.
     *
     * @param markup_percentage
     *
     * @see MyFoodoraSystem#markup_percentage
     */
    public void setMarkup_percentage(BigDecimal markup_percentage){
        if (currentUser instanceof Manager){
            if (markup_percentage.compareTo(new BigDecimal("1")) == -1){ //if markup_percentage > 1

                this.markup_percentage = markup_percentage;
                System.out.println(">> Current markup percentage: " + markup_percentage);
            }
            else {
                System.out.println("markup percentage should be larger than 1");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Sets the delivery cost.
     * Settable only by managers.
     *
     * @param delivery_cost
     *
     * @see MyFoodoraSystem#delivery_cost_price
     */
    public void setDelivery_cost(BigDecimal delivery_cost){
        if (currentUser instanceof Manager){
            if (!delivery_cost.equals(new BigDecimal("0"))){
                this.delivery_cost_price = delivery_cost;
                System.out.println(">> Current delivery price per km: " + delivery_cost);
            }
            else {
                System.out.println("delivery cost should be positive");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *  Displays the total income.
     *  Only for managers.
     */
    public void totalIncome(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_income));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *  Displays the total Delivery cost.
     *  Only for managers.
     */
    public void totalDeliveryCost(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_delivery_cost));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *  Displays the total profit.
     *  Only for managers.
     */
    public void totalProfit(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_profit));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Displays the history of orders.
     * Only for managers.
     */
    public void showHistoryOfOrder_System(){
        if (currentUser instanceof Manager){
            if (!orders.isEmpty()){
                for (int i = 0; i < orders.size(); i++) {
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

    /**
     * Displays the average income per customer.
     * Only for managers.
     */
    public void averageIncomePerCustomer(){
        if (currentUser instanceof Manager){
            if (!orders.isEmpty()){
                Integer numberOfCustomer = 0;
                for (User user: users.getUsers()
                     ) {
                    if (user instanceof Customer){
                        if (!((Customer) user).getHistoryOfOrder().isEmpty()){
                            numberOfCustomer = numberOfCustomer +1;
                        }
                    }
                }
                if (numberOfCustomer > 0){
                    averageIncomePerCustomer = total_income.divide(BigDecimal.valueOf(numberOfCustomer));
                    System.out.println(Money.display(total_income.divide(BigDecimal.valueOf(numberOfCustomer))));
                }
                else {
                    System.out.println("there is no customer in the system");
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

    /**
     * set target profit before determining the parameters
     * @param target_profit
     */
    public void setTarget_profit(Double target_profit){
        if (currentUser instanceof Manager){
            this.target_profit = BigDecimal.valueOf(target_profit);
            System.out.println(">> Current profit: " + Money.display(total_profit));
            System.out.println(">> Target profit: " + Money.display(BigDecimal.valueOf(target_profit)));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * set the policy
     * @param policy 0:Service fee
     *               1:markup percentage
     *               2:delivery cost
     */
    public void setProfitPolicy(int policy){
        if (currentUser instanceof Manager){
            if (policy == 0|| policy== 1|| policy== 2){
                profitPolicy = policy;
            }
            else {
                System.out.println("invalid input");
            }
        }
        else {
            System.out.println("you must log in first");
        }
    }

    /**
     * get the current profit policy
     * @return the name of policy
     */
    public String getProfitPolicy(){
        if (currentUser instanceof Manager){
            switch (profitPolicy){
                case 0:
                    System.out.println("by Service fee");
                    return "by Service fee";
//                    break;
                case 1:
                    System.out.println("by Markup Percentage");
                    return "by Markup Percentage";
//                    break;
                case 2:
                    System.out.println("by Delivery Cost");
                    return "by Delivery Price";
//                    break;
                default:
                    System.out.println("error");
            }
        }
        else {
            System.out.println("you must log in first");
        }
        return "error";
    }

    /**
     * get the parameter according the chosen profit policy
     */
    public String[] getParameter(){
        if (currentUser instanceof Manager){
            String[] para = new String[3];
            switch (profitPolicy){
                case 0:
                    determineService_fee();
                    break;
                case 1:
                    determineMarkup_Percentage();
                    break;
                case 2:
                    determineDelivery_Cost();
                    break;
                default:
                    System.out.println("error");
            }
            para[0] = tmpService_fee.toString();
            para[1] = tmpMarkup_percentage.toString();
            para[2] = tmpDelivery_cost_price.toString();
            return para;
        }
        else {
            System.out.println("you must log in first");
        }
        return null;
    }

    /**
     * determining the Service Fee to meet the target profit
     */
    public void determineService_fee(){
        if (currentUser instanceof Manager){
            if (!orders.isEmpty()){
                if (target_profit != BigDecimal.valueOf(0)){
                    BigDecimal sumOrderPrice = new BigDecimal("0");
                    BigDecimal sumDeliveryDistance = new BigDecimal("0");

                    for (Order order: orders
                         ) {
                        sumOrderPrice = sumOrderPrice.add(order.getOrder_price());
                    }

                    for (Order order: orders
                         ) {
                        sumDeliveryDistance = sumDeliveryDistance.add(order.getDelivery_distance());
                    }


                    tmpDelivery_cost_price = delivery_cost_price;
                    tmpMarkup_percentage = markup_percentage;

                    BigDecimal tmp = new BigDecimal("0");
                    tmp = target_profit.add(sumDeliveryDistance.multiply(tmpDelivery_cost_price));
                    tmp = tmp.subtract(sumOrderPrice.multiply(tmpMarkup_percentage.add(BigDecimal.valueOf(1))));
                    tmpService_fee = tmp.divide(BigDecimal.valueOf(orders.size()), 3, RoundingMode.HALF_UP);

                    System.out.println("========================================");
                    System.out.println("you have choose targetProfit_ServiceFee");
                    System.out.println("========================================");
                    System.out.println("current parameters: ");
                    System.out.println(">> markup percentage: " + Percentage.display(markup_percentage) );
                    System.out.println(">> delivery price: " + Money.display(delivery_cost_price) );
                    System.out.println(">> service fee: " + Money.display(service_fee));
                    System.out.println("========================================");
                    System.out.println("In order to meet the target profit: " + Money.display(target_profit));
                    System.out.println(">> service fee should be: " + Money.display(tmpService_fee));
                }
                else {
                    System.out.println("You haven't set target profit yet");
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

    /**
     * determining the Markup percentage to meet the target profit
     */
    public void determineMarkup_Percentage(){
        if (currentUser instanceof Manager){
            if (!orders.isEmpty()){
                if (target_profit != BigDecimal.valueOf(0)){
                    BigDecimal sumOrderPrice = new BigDecimal("0");
                    BigDecimal sumDeliveryDistance = new BigDecimal("0");

                    for (Order order: orders
                            ) {
                        sumOrderPrice = sumOrderPrice.add(order.getOrder_price());
                    }

                    for (Order order: orders
                            ) {
                        sumDeliveryDistance = sumDeliveryDistance.add(order.getDelivery_distance());
                    }

                    tmpDelivery_cost_price = delivery_cost_price;
                    tmpService_fee = service_fee;

                    BigDecimal tmp = new BigDecimal("0");
                    tmp = target_profit.add(sumDeliveryDistance.multiply(tmpDelivery_cost_price));
                    tmp = tmp.subtract(tmpService_fee.multiply(BigDecimal.valueOf(orders.size())));
                    tmpMarkup_percentage = tmp.divide(sumOrderPrice, 6, RoundingMode.HALF_UP).subtract(BigDecimal.valueOf(1));

                    NumberFormat percent = NumberFormat.getPercentInstance();
                    percent.setMaximumFractionDigits(3);

                    System.out.println("========================================");
                    System.out.println("you have choose targetProfit_Markup");
                    System.out.println("========================================");
                    System.out.println("current parameters: ");
                    System.out.println(">> markup percentage: " + Percentage.display(markup_percentage) );
                    System.out.println(">> delivery price: " + Money.display(delivery_cost_price) );
                    System.out.println(">> service fee: " + Money.display(service_fee));
                    System.out.println("========================================");
                    System.out.println("In order to meet the target profit: " + Money.display(target_profit));
                    System.out.println(">> Markup percentage should be: " + Percentage.display(tmpMarkup_percentage));
                }
                else {
                    System.out.println("You haven't set target profit yet");
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

    /**
     * determining the Delivery Cost to meet the target profit
     */
    public void determineDelivery_Cost(){
        if (currentUser instanceof Manager){
            if (!orders.isEmpty()){
                if (target_profit != BigDecimal.valueOf(0)){
                    if (total_income.doubleValue() < target_profit.doubleValue()){
                        System.out.println("Total income is less than target profit");
                        System.out.println("Can't apply TargetProfit_DeliveryCost");
                    }
                    else {
                        BigDecimal sumOrderPrice = new BigDecimal("0");
                        BigDecimal sumDeliveryDistance = new BigDecimal("0");

                        for (Order order: orders
                                ) {
                            sumOrderPrice = sumOrderPrice.add(order.getOrder_price());
                        }

                        for (Order order: orders
                                ) {
                            sumDeliveryDistance = sumDeliveryDistance.add(order.getDelivery_distance());
                        }

                        tmpService_fee = service_fee;
                        tmpMarkup_percentage = markup_percentage;

                        BigDecimal tmp = new BigDecimal("0");
                        tmp = target_profit.subtract(tmpService_fee.multiply(BigDecimal.valueOf(orders.size())));
                        tmp = tmp.subtract(sumOrderPrice.multiply(tmpMarkup_percentage.add(BigDecimal.valueOf(1))));
                        tmp = tmp.divide(sumDeliveryDistance, 6, RoundingMode.HALF_UP);
                        tmpDelivery_cost_price = tmp.abs();

                        NumberFormat percent = NumberFormat.getPercentInstance();
                        percent.setMaximumFractionDigits(3);
                        System.out.println("========================================");
                        System.out.println("you have choose targetProfit_DeliveryCost");
                        System.out.println("========================================");
                        System.out.println("current parameters: ");
                        System.out.println(">> markup percentage: " + Percentage.display(markup_percentage) );
                        System.out.println(">> delivery price: " + Money.display(delivery_cost_price) );
                        System.out.println(">> service fee: " + Money.display(service_fee));
                        System.out.println("========================================");
                        System.out.println("In order to meet the target profit: " + Money.display(target_profit));
                        System.out.println(">> Delivery price should be: " + Money.display(tmpDelivery_cost_price));
                    }
                }
                else {
                    System.out.println("You haven't set target profit yet");
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

    /**
     * determining the most selling restaurant
     *
     * @see Restaurant#getIncome()
     */
    public void mostSellingRestaurant(){
        if (currentUser instanceof Manager){
            boolean isFound = false;
            for (User user: users.getUsers()
                 ) {
                if (user instanceof Restaurant){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                Restaurant bestRestaurant = null;
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                for (User user: users.getUsers()){
                    if (user instanceof Restaurant){
                        restaurants.add((Restaurant) user);
                    }
                }
                BigDecimal incomeBest = BigDecimal.valueOf(0);
                BigDecimal incomeTmp = BigDecimal.valueOf(0);

                if (restaurants.size()>1){
                    for (int i = 0; i < restaurants.size(); i++) {
                        if (bestRestaurant == null){
                            incomeBest = restaurants.get(i).getIncome();
                            bestRestaurant = restaurants.get(i);
                        }
                        else {
                            incomeTmp = restaurants.get(i).getIncome();
                            if (incomeTmp.doubleValue() > incomeBest.doubleValue()){
                                incomeBest = incomeTmp;
                                bestRestaurant = restaurants.get(i);
                            }
                        }
                    }

                    System.out.println(">> The best is " + bestRestaurant.getName());
                    System.out.println(">> income: " + Money.display(bestRestaurant.getIncome()));
                }
                else {
                    System.out.println("You only have one restaurant:" + restaurants.get(0).getName());
                }
            }
            else {
                System.out.println("There is no restaurant in myFoodora");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * determining the least selling restaurant
     *
     * @see Restaurant#getIncome()
     */
    public void leastSellingRestaurant(){
        if (currentUser instanceof Manager){
            boolean isFound = false;
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Restaurant){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                Restaurant leastRestaurant = null;
                ArrayList<Restaurant> restaurants = new ArrayList<>();
                for (User user: users.getUsers()){
                    if (user instanceof Restaurant){
                        restaurants.add((Restaurant) user);
                    }
                }
                BigDecimal incomeBest = BigDecimal.valueOf(0);
                BigDecimal incomeTmp = BigDecimal.valueOf(0);

                if (restaurants.size()>1){
                    for (int i = 0; i < restaurants.size(); i++) {
                        if (leastRestaurant == null){
                            incomeBest = restaurants.get(i).getIncome();
                            leastRestaurant = restaurants.get(i);
                        }
                        else {
                            incomeTmp = restaurants.get(i).getIncome();
                            if (incomeTmp.doubleValue() < incomeBest.doubleValue()){
                                incomeBest = incomeTmp;
                                leastRestaurant = restaurants.get(i);
                            }
                        }
                    }

                    System.out.println(">> The best is " + leastRestaurant.getName());
                    System.out.println(">> income: " + Money.display(leastRestaurant.getIncome()));
                }
                else {
                    System.out.println("You only have one restaurant:" + restaurants.get(0).getName());
                }
            }
            else {
                System.out.println("There is no restaurant in myFoodora");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * determine the most active courier
     *
     * @see Courier#deliveredOrdersCounter
     */
    public void mostActiveCourier(){
        if (currentUser instanceof Manager){
            boolean isFound = false;
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Courier){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                Courier bestCourier = null;
                ArrayList<Courier> couriers = new ArrayList<>();
                for (User user: users.getUsers()){
                    if (user instanceof Courier){
                        couriers.add((Courier) user);
                    }
                }
                int best = 0;
                int tmp = 0;

                if (couriers.size()>1){
                    for (int i = 0; i < couriers.size(); i++) {
                        if (bestCourier == null){
                            best = couriers.get(i).getDeliveredOrdersCounter();
                            bestCourier = couriers.get(i);
                        }
                        else {
                            tmp = couriers.get(i).getDeliveredOrdersCounter();
                            if (tmp > best){
                                best = tmp;
                                bestCourier = couriers.get(i);
                            }
                        }
                    }

                    System.out.println(">> The best is " + bestCourier.getName());
                    System.out.println(">> Number: " + bestCourier.getDeliveredOrdersCounter());
                }
                else {
                    System.out.println("You only have one restaurant:" + couriers.get(0).getName());
                }
            }
            else {
                System.out.println("There is no courier in myFoodora");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * determine the least active courier
     *
     * @see Courier#deliveredOrdersCounter
     */
    public void leastActiveCourier(){
        if (currentUser instanceof Manager){
            boolean isFound = false;
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Courier){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                Courier bestCourier = null;
                ArrayList<Courier> couriers = new ArrayList<>();
                for (User user: users.getUsers()){
                    if (user instanceof Courier){
                        couriers.add((Courier) user);
                    }
                }
                int best = 0;
                int tmp = 0;

                if (couriers.size()>1){
                    for (int i = 0; i < couriers.size(); i++) {
                        if (bestCourier == null){
                            best = couriers.get(i).getDeliveredOrdersCounter();
                            bestCourier = couriers.get(i);
                        }
                        else {
                            tmp = couriers.get(i).getDeliveredOrdersCounter();
                            if (tmp < best){
                                best = tmp;
                                bestCourier = couriers.get(i);
                            }
                        }
                    }

                    System.out.println(">> The least active is " + bestCourier.getName());
                    System.out.println(">> Number: " + bestCourier.getDeliveredOrdersCounter());
                }
                else {
                    System.out.println("You only have one restaurant:" + couriers.get(0).getName());
                }
            }
            else {
                System.out.println("There is no courier in myFoodora");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * set the delivery policy
     */
    public void setDeliveryPolicy(String policy){
        if (currentUser instanceof Manager){
            if (policy.equalsIgnoreCase("fastDelivery")){
                this.deliveryPolicy = "fastDelivery";
                System.out.println("You have changed the delivery policy as: fastDelivery");
            }
            else if ((policy.equalsIgnoreCase("fairOccupationDelivery"))){
                this.deliveryPolicy = "fairOccupationDelivery";
                System.out.println("You have changed the delivery policy as: fairOccupationDelivery");
            }
            else {
                System.out.println("invalid input argument");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * set the profit policy
     */
    public void setProfitPolicy(String policy){
        if (currentUser instanceof Manager){
            if (policy.equalsIgnoreCase("serviceFee")){
                this.profitPolicy = 0;
                System.out.println("You have changed the profit policy as: serviceFee");
            }
            else if ((policy.equalsIgnoreCase("markupPercentage"))){
                this.profitPolicy = 1;
                System.out.println("You have changed the profit policy as: markupPercentage");
            }
            else if ((policy.equalsIgnoreCase("deliveryCost"))){
                this.profitPolicy = 2;
                System.out.println("You have changed the profit policy as: deliveryCost");
            }
            else {
                System.out.println("invalid input argument");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * private method
     *
     * Get the list of available courier
     * the list can be null if there is no available couriers
     *
     * @param order
     * @return a list of available courier
     */
    public ArrayList<Courier> getAvailableCourier(Order order){
        ArrayList<Courier> couriers = new ArrayList<>();
        for (User user: users.getUsers()){
            if (user instanceof Courier){
                if (((Courier) user).getWorkingState()){
                    if (!order.isCourierHasBeenNotified((Courier) user)){
                        if (!((Courier) user).getNewOrderCondition()){
                            couriers.add((Courier) user);
                        }
                    }
                }
            }
        }
        return couriers;
    }

    /**
     * private method
     *
     * find a courier according to the fastDelivery policy
     * return null if there is no appropriate courier
     *
     * @param availableCouriers
     * @param order to get the restaurant connected in this order
     *
     */
    public Courier findCourier_FastDelivery(ArrayList<Courier> availableCouriers, Order order){
        Restaurant restaurant = order.getRestaurant();
        Courier bestCourier = null;
        Double distanceBest = Double.valueOf(0);
        Double distanceTmp = Double.valueOf(0);
        for (int i = 0; i < availableCouriers.size(); i++) {
            if (bestCourier == null){
                bestCourier = availableCouriers.get(i);
            }
            else {
                distanceBest = Coordinate.getDistance(bestCourier.getPosition(),restaurant.getAddress());
                distanceTmp = Coordinate.getDistance(availableCouriers.get(i).getPosition(),restaurant.getAddress());
                if (distanceTmp < distanceBest){
                    bestCourier = availableCouriers.get(i);
                }
            }
        }
        return bestCourier;
    }

    /**
     * private method
     *
     * find a courier according to the FairOccupationDelivery policy
     * return null if there is no appropriate courier
     *
     * @param availableCouriers
     * @param order to get the restaurant connected in this order
     *
     */
    public Courier findCourier_FairOccupationDelivery(ArrayList<Courier> availableCouriers, Order order){
        Restaurant restaurant = order.getRestaurant();
        Courier bestCourier = null;
        int counter = 0;

        for (int i = 0; i < availableCouriers.size(); i++) {
            if (bestCourier == null){
                bestCourier = availableCouriers.get(i);
                counter = availableCouriers.get(i).getDeliveredOrdersCounter();
            }
            else {
                if (availableCouriers.get(i).getDeliveredOrdersCounter() < counter){
                    bestCourier = availableCouriers.get(i);
                    counter = bestCourier.getDeliveredOrdersCounter();
                }
            }
        }
        return bestCourier;
    }

    /**
     * delegate the order to a courier
     * use strategy pattern to apply different policies
     *
     * @param order the order that you try to delegate
     */
    public void delegateOrder2Courier(Order order) throws UserNotFoundException {
        Courier courier = null;
        if (deliveryPolicy.equalsIgnoreCase("fastDelivery")){
            courier = findCourier_FastDelivery(getAvailableCourier(order),order);
        }
        else if (deliveryPolicy.equalsIgnoreCase("fairOccupationDelivery")){
            courier = findCourier_FairOccupationDelivery(getAvailableCourier(order),order);
        }
        else {
            System.out.println("error");
        }
        if (courier != null){
            courier.setNewOrder(order);
            updateUser(courier);
            order.addCourier2DemandeHistory(courier);
            updateOrder(order);
        }
        else {
            System.out.println("there is no available courier in myFoodora");
        }
    }

    /**
     * Adapt to the professor's new requirement
     *
     * reuse the method delegateOrder2Courier(Order order)
     */
    public void findDeliverer(int orderID) {
        if(currentUser instanceof Restaurant){
            //Find the order instance by the name
            Order newOrder = null;
            for (Order order: orders
                 ) {
                if (order.getId() == orderID){
                    newOrder = order;
                }
            }
            //Deal with the order
            if (newOrder != null){
                if (!newOrder.getDeliveryState()){
                    try {
                        delegateOrder2Courier(newOrder);
                    } catch (UserNotFoundException e) {
                        System.out.println("There is no available courier in the system");
                    }
                }
                else {
                    System.out.println("This order has been delegated");
                }
            }
            else {
                System.out.println("Didn't find any correspond order: " + orderID);
            }
        }
        else {
            System.out.println("You have to log in first!");
        }
    }

    /**
     * show the available restaurants for the customer
     */
    public void showRestaurant(){
        if (currentUser instanceof Customer){
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

    /**
     * Enables a Customer to choose a restaurant by writing the restaurant's name.
     * And the system creates an empty order for the customer at the same time.
     *
     * @param restaurant
     *
     * @see Restaurant
     */
    public void chooseRestaurant(String restaurant){
        if (currentUser instanceof Customer){
            boolean isFound = false;
            for (User user: users.getUsers()) {
                if (user.getName().equalsIgnoreCase(restaurant) && user instanceof Restaurant){
                    isFound = true;
                    currentRestaurant = (Restaurant) user;
                    currentOrder = new Order(currentRestaurant,(Customer) currentUser, delivery_cost_price, markup_percentage, service_fee);
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

    /**
     *
     * <b>Shows to the Customer the menu of the currently selected restaurant.</b>
     *
     * @see Restaurant
     */
    public void showMenu(){
        if (currentUser instanceof Customer){
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

    /**
     *
     * <b>Adds a given meal to the customer's order.</b>
     *
     * @param mealName
     * @param quantity
     *
     * @see Customer
     * @see Meal
     * @see Order
     */
    public void addMeal2Order(String mealName, Integer quantity){
        if (currentUser instanceof Customer){
            if (currentOrder != null){
                boolean isFound = false;
                for (Meal meal: currentRestaurant.getMeals()
                     ) {
                    if (meal.getName().equalsIgnoreCase(mealName)){
                        isFound = true;

                        for (int i = 0; i < quantity; i++) {
                            currentOrder.addMeal(meal);
                        }

                        System.out.println("you have added " + mealName + " x" + quantity +" successfully");

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

    /**
     *<b>Adds an item to the customer's order.</b>
     *
     * @param itemName
     * @param quantity
     *
     * @see Customer
     * @see Order
     * @see Item
     */
    public void addItem2Order(String itemName, Integer quantity){
        if (currentUser instanceof Customer){
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

    /**
     *
     * <b>Shows the customer's current order (if not empty).</b>
     *
     * @see Customer
     * @see Order
     */
    public void showOrder(){
        if (currentUser instanceof Customer){
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

    /**
     * <b>Ends an customer's order, applies the fidelity program and send the order to the restaurant.</b>
     *
     * <ul>
     *     <li>apply the fidelity program</li>
     *     <li>send the order to the restaurant</li>
     *     <li>save the order to </li>
     *     <li>save the order to the history of system</li>
     *     <li>delegate this order to an available courier</li>
     *     <li>save the order to the history of the customer</li>
     *     <li>clean the current order</li>
     * </ul>
     *
     */
    public void endOrder() throws UserNotFoundException {
        if (currentUser instanceof Customer){
            if (currentOrder != null){
                if (!currentOrder.isEmpty()){

                    //Show the detail of order first
                    currentOrder.showOrder();
                    System.out.println("Total: " + Money.display(currentOrder.getTotal_price()));

                    //apply Fidelity discount
                    currentOrder.applyFidelityDiscount();
                    System.out.println("Total (after Fidelity discount): " + Money.display(currentOrder.getTotal_price()));

                    //send the order to the restaurant
                    currentRestaurant.addOrder(currentOrder);

                    //save the order to the history of system
                    this.orders.add(currentOrder);
                    calculateFinancial();
                    saveOrders();

                    //distribute the order to courier
//                    System.out.println(getAvailableCourier(currentOrder).toString());
                    delegateOrder2Courier(currentOrder);

                    //save the order to the history of customer
                    ((Customer) currentUser).addOrderToHistory(currentOrder);
                    users.saveUsers();

                    //clean current order
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

    /**
     * <b>Shows the complete customer's history of orders.</b>
     * Only for customers.
     */
    public void showHistoryOfOrder_Customer(){
        if (currentUser instanceof Customer){
            if (!((Customer) currentUser).getHistoryOfOrder().isEmpty()){
                for (int i = 0; i < ((Customer) currentUser).getHistoryOfOrder().size(); i++) {
                    System.out.println(">> " + (i+1) + ") Order");
                    ((Customer) currentUser).getHistoryOfOrder().get(i).showOrder();
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

    /**
     * <b>Shows the fidelity program to which the customer is registered, and the total of collected points.</b>
     *  Only for customers.
     */
    public void showFidelityCard(){
        if (currentUser instanceof Customer){
            System.out.println(((Customer) currentUser).getFidelityCard().getFidelityCardName());
            System.out.println(((Customer) currentUser).getFidelityCard().getPoints() + " points");
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *
     * @param cardType
     */
    public void registerFidelityCard(String cardType) throws UserNotFoundException {
        if (currentUser instanceof Customer){
            if (cardType.equalsIgnoreCase("BasicFidelityCard") ){
                ((Customer) currentUser).setFidelityCard(new BasicFidelityCard());
                System.out.println("you have registered << BasicFidelityCard>> ");
                updateUser(currentUser);
            }
            else if (cardType.equalsIgnoreCase("LotteryFidelityCard")){
                ((Customer) currentUser).setFidelityCard(new LotteryFidelityCard());
                System.out.println("you have registered << LotteryFidelityCard>> ");
                updateUser(currentUser);
            }
            else if (cardType.equalsIgnoreCase("PointFidelityCard")){
                ((Customer) currentUser).setFidelityCard(new PointFidelityCard());
                System.out.println("you have registered << PointFidelityCard>> ");
                updateUser(currentUser);
            }
            else {
                System.out.println("invalid input");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * for the currently logged on myFoodora manager to associate a fidelity card to a user with given name
     *
     */
    public void associateCard(String userName, String cardType) throws UserNotFoundException {
        if (currentUser instanceof Manager){
            User user = getUser(userName);
            if (user instanceof Customer){
                if (cardType.equalsIgnoreCase("basicFidelityCard") ){
                    ((Customer) user).setFidelityCard(new BasicFidelityCard());
                    System.out.println("you have registered << BasicFidelityCard>> ");
                    updateUser(user);
                }
                else if (cardType.equalsIgnoreCase("LotteryFidelityCard")){
                    ((Customer) user).setFidelityCard(new LotteryFidelityCard());
                    System.out.println("you have registered << LotteryFidelityCard>> ");
                    updateUser(user);
                }
                else if (cardType.equalsIgnoreCase("PointFidelityCard")){
                    ((Customer) user).setFidelityCard(new PointFidelityCard());
                    System.out.println("you have registered << PointFidelityCard>> ");
                    updateUser(user);
                }
                else {
                    System.out.println("invalid input");
                }
            }
            else {
                System.out.println(userName + "can't apply this command");
            }
        }
        else {
            System.out.println("you have to log in first");
        }
    }

    /**
     *
     * Changes the notification status of a customer.
     * <ul>
     *     <li>On: to receive messages from MyFooddora</li>
     *     <li>Off: to disable notifications.</li>
     * </ul>
     *
     * @param string "on" if the customer wants to be notified, "off" if he doesn't.
     */
    public void setNotified(String string){
        if (currentUser instanceof Customer) {
            if (string.equalsIgnoreCase("on")){
                ((Customer) currentUser).infoBoard.setNotified_On();
                System.out.println("You can receive messages from Myfoodora by " + ((Customer) currentUser).infoBoard.getContactType());
            }
            else if (string.equalsIgnoreCase("off")){
                ((Customer) currentUser).infoBoard.setNotified_Off();
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

    /**
     * show the info board every time the customer login
     * customer can also check the info board later
     */
    public void checkInfoBoard(){
        if (currentUser instanceof Customer) {
            if (((Customer) currentUser).infoBoard.isNotified()){
                Integer num = ((Customer) currentUser).infoBoard.getNumberOfNewMeassages();
                ArrayList<Message> messages = ((Customer) currentUser).infoBoard.getMessages();
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
                            System.out.println(">> new message: " + messages.get(i).showMessage());
                        }
                        else {
                            System.out.println(">> " + messages.get(i).showMessage());
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

    public void deleteMessage(int index){
        if (currentUser instanceof Customer){
            if (((Customer) currentUser).infoBoard.isNotified()){
                ArrayList<Message> messages = ((Customer) currentUser).infoBoard.getMessages();
                if (messages.isEmpty()){
                    System.out.println("You have no message");
                }
                else if (index > messages.size()){
                    System.out.println("invalid input(out of bound)");
                }
                else {
                    ((Customer) currentUser).infoBoard.deleteMessage(index);
                    users.saveUsers();
                }
            }
            else {
                System.out.println("You can't receive message from myFoodora");
                System.out.println("Please set infoBoard notified on");
            }        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *
     * <b>Creates a new Item to the Restaurant's menu.</b>
     * Only for Restaurants
     *
     * @param itemName
     * @param price
     */
    public void createItem(String itemName, BigDecimal price) {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getItem(itemName) != null){ //This item already exists

                System.out.println("Error: this item's name already exists.");

            } else {
                Item item = null;
                try {
                    item = new Item(itemName, price, ItemCategory.MainDish, FoodType.Standard);
                } catch (EmptyNameException e) {
                    System.out.println("The item's name must not be empty.");
                }
                restaurant.addItem(item);

                System.out.println(item + " was successfully created!");
            }


        } else {

            System.out.println("Your restaurant must be logged in to create a item.");

        }
    }

    public void createItem(String itemName, BigDecimal price, ItemCategory itemCategory, FoodType foodType)  {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getItem(itemName) != null){ //This item already exists

                System.out.println("Error: this item's name already exists.");

            } else {

                Item item = null;
                try {
                    item = new Item(itemName, price, itemCategory, foodType);
                } catch (EmptyNameException e) {
                    System.out.println("The item's name must not be empty.");
                }
                restaurant.addItem(item);

                System.out.println(item + " was successfully created!");

            }


        } else {

            System.out.println("Your restaurant must be logged in to create a item.");

        }
    }

    /**
     *
     * <b>Creates a new meal to the Restaurant's menu.</b>
     * Only for Restaurants.
     *
     * @param mealName the name of the new meal.
     */
    public void createMeal(String mealName) {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getMeal(mealName) != null){ //This meal already exists

                System.out.println("Error: this meal's name already exists.");

            } else {

                Meal meal = new Meal(mealName, (Restaurant) currentUser);
                restaurant.addMeal(meal);

                System.out.println(meal + " was successfully created!");
            }

        } else {

            System.out.println("Your restaurant must be logged in to create a meal.");

        }
    }

    public void createMeal(String mealName, MealCategory mealCategory, FoodType foodType) {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getMeal(mealName) != null){ //This meal already exists

                System.out.println("Error: this meal's name already exists.");

            } else {

                Meal meal = null;
                try {
                    meal = new Meal(mealName, (Restaurant) currentUser, mealCategory, foodType);
                } catch (EmptyNameException e) {

                    System.out.println("The item's name must not be empty.");

                }
                restaurant.addMeal(meal);

                System.out.println(meal + " was successfully created!");

            }


        } else {

            System.out.println("Your restaurant must be logged in to create a meal.");

        }
    }

    /**
     *
     * <b>to add an item to an existing meal.</b>
     * Only for Restaurants.
     *
     * @param itemName
     * @param mealName
     */
    public void addDish2Meal(String itemName, String mealName) {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;
            Meal meal = restaurant.getMeal(mealName);
            Item item = restaurant.getItem(itemName);
            try {
                meal.addItem(item);
                System.out.println(item + " was successfully added to " + meal + "!");

            } catch (IncompatibleFoodTypeException e) {
                System.out.println("The category of this item isn't compatible with the meal's one.");
            } catch (TooManyItemsException e) {
                System.out.println("There is already an item of this type OR there are too many items in this meal.");
            }



        } else {

            System.out.println("Your restaurant must be logged in to add a dish to a meal.");

        }
    }

    /**
     *
     * <b>To save a Restaurant's menu.</b>
     * Only for Restaurants.
     */
    public void saveMenu() {

        if (currentUser instanceof Restaurant){

            users.saveUsers();

            System.out.println("Your menu was successfully saved!");

        } else {

            System.out.println("Your restaurant must be logged in to save the menu.");

        }

    }

    /**
     *
     * <b>To show the content of given Restaurant's mel</b>
     *
     * @param restaurantName the name of the given Restaurant.
     * @param mealName the name of the given meal.
     */
    public void showMeal(String restaurantName, String mealName){
        Restaurant restaurant = (Restaurant) getUser(restaurantName);
        Meal meal = restaurant.getMeal(mealName);
        System.out.println(meal);
    }

    /**
     * Enables a restaurant to send a message to customers who have set notifications "on".
     * Only for restaurants.
     *
     * @param title the title of the message
     * @param message the content of the message
     */
    public void sendMessage(String title, String message){
        if (currentUser instanceof Restaurant){
            for (User user: users.getUsers()
                 ) {
                if (user instanceof Customer) {
                    if (((Customer) user).infoBoard.isNotified()){
                        Message newMessage = new Message(title, currentUser.getName(), message);
                        ((Customer) user).infoBoard.addMessage(newMessage);
                    }
                }
            }
            users.saveUsers();
        } else {
            System.out.println("You have to log in");
        }
    }

    /**
     * Show all the orders of a restaurant
     */
    public void showOrdersOfRestaurant(){
        if (currentUser instanceof Restaurant){
            if (!((Restaurant) currentUser).getOrders().isEmpty()){
                for (int i = 0; i < ((Restaurant) currentUser).getOrders().size(); i++) {
                    System.out.println(">> " + (i+1) + ") Order");
                    ((Restaurant) currentUser).getOrders().get(i).showOrder();
                }
            }
            else {
                System.out.println("Your order is empty");
            }
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }

    /**
     * set the general discount for all the meal
     * @param GenericDiscountFactor
     */
    public void setGenericDiscountFactor(BigDecimal GenericDiscountFactor){
        if (currentUser instanceof Restaurant){
            if (GenericDiscountFactor.doubleValue() < 1 && GenericDiscountFactor.doubleValue() > 0){
                ((Restaurant) currentUser).setGenericDiscountFactor(GenericDiscountFactor);
            }
            else {
                System.out.println("invalid input");
            }
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }

    /**
     * set the special offer discount factor
     * @param specialDiscountFactor
     */
    public void setSpecialDiscountFactor(BigDecimal specialDiscountFactor){
        if (currentUser instanceof Restaurant){
            if (specialDiscountFactor.doubleValue() < 1 && specialDiscountFactor.doubleValue() > 0){
                ((Restaurant) currentUser).setSpecialDiscountFactor(specialDiscountFactor);

            }
            else {
                System.out.println("invalid input");
            }
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }

    /**
     * set the special offer for a meal
     * @param mealName
     * @see Restaurant
     */
    public void setSpecialOffer(String mealName){
        if (currentUser instanceof Restaurant){
            Meal meal = ((Restaurant) currentUser).getMeal(mealName);
            if (meal != null){
                meal.setMealOfTheWeek(true);
                ((Restaurant) currentUser).updateMeal(meal);
                sendMessage("Meal of week", " << " + mealName + " >> has been set as the meal of week");
            }
            else {
                System.out.println("didn't find >> " + mealName);
            }
        }
        else {
            System.out.println("Your restaurant must log in.");
        }
    }

    /**
     * cancel the special offer for a meal
     * @param mealName
     */
    public void removeFromSpecialOffer(String mealName){
        if (currentUser instanceof Restaurant){
            Meal meal = ((Restaurant) currentUser).getMeal(mealName);
            meal.setMealOfTheWeek(false);
            ((Restaurant) currentUser).updateMeal(meal);
            System.out.println(meal.getName() + "has been removed from the special offer");
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }

    /**
     * Not developed, maybe will be deleted in the future
     */
    public void addDish(String dishName, String dishCategory, BigDecimal unitPrice){

    }

    /**
     * get the list of all meals from given orders
     *
     * @param orders
     */
    public ArrayList<Food> getAllMeals(ArrayList<Order> orders){
        ArrayList<Food> allMeals = new ArrayList<>();

        for (Order order: orders
             ) {
            for (Meal meal: order.getMeals()
                 ) {
                allMeals.add(meal);
            }
        }
        return allMeals;
    }

    /**
     * get the list of all items from given orders
     *
     * @param orders
     */
    public ArrayList<Food> getAllItems(ArrayList<Order> orders){
        ArrayList<Food> allItems = new ArrayList<>();

        for (Order order: orders
                ) {
            for (Item item: order.getItems()
                    ) {
                allItems.add(item);
            }
        }
        return allItems;
    }

    /**
     * get the quantity of each kind of meal or item
     *
     * @param foods the list of foods
     * @return the list of foods and their quantities
     *
     */
    public ArrayList<Sort> getQuantityOfFoods(ArrayList<Food> foods){
        ArrayList<Sort> sortFoods = new ArrayList<>();
        ArrayList<Food> addedFoods = new ArrayList<>();

        for (Food foods1: foods){
            boolean isFound = false;
            for (Food food : addedFoods){
                if (food.getName().equalsIgnoreCase(foods1.getName())){
                    isFound = true;
                    break;
                }
            }
            if (isFound){
                for (Sort sort: sortFoods){
                    if ((sort.getType()).getName().equalsIgnoreCase(foods1.getName())){
                        sort.addQuantity();
                    }
                }
            }
            else {
                //if this a new meal, then add to the addedmeals list and new
                addedFoods.add(foods1);
                if (foods1 instanceof Meal){
                    sortFoods.add(new SortMeal((Meal) foods1));
                }
                else if (foods1 instanceof Item){
                    sortFoods.add(new SortItem((Item) foods1));
                }
            }
        }
        return sortFoods;
    }

    /**
     * sort ordered meals in ascending sequence
     */
    public void sortMostMeal(){
        if (currentUser instanceof Manager){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllMeals(orders));
            Collections.sort(sortMeal, new SortByQuantityDown());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else if (currentUser instanceof Restaurant){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllMeals(((Restaurant) currentUser).getOrders()));
            Collections.sort(sortMeal, new SortByQuantityDown());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else {
            System.out.println("Your must log in first");
        }
    }

    /**
     * sort ordered meals in descend sequence
     */
    public void sortLeastMeal(){
        if (currentUser instanceof Manager){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllMeals(orders));
            Collections.sort(sortMeal, new SortByQuantityUp());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else if (currentUser instanceof Restaurant){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllMeals(((Restaurant) currentUser).getOrders()));
            Collections.sort(sortMeal, new SortByQuantityUp());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else {
            System.out.println("Your must log in first");
        }
    }

    /**
     * sort ordered items in ascending sequence
     */
    public void sortMostItem(){
        if (currentUser instanceof Manager){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllItems(orders));
            Collections.sort(sortMeal, new SortByQuantityDown());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else if (currentUser instanceof Restaurant){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllItems(((Restaurant) currentUser).getOrders()));
            Collections.sort(sortMeal, new SortByQuantityDown());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else {
            System.out.println("Your must log in first");
        }
    }

    /**
     * sort ordered items in descend sequence
     */
    public void sortLeasItem(){
        if (currentUser instanceof Manager){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllItems(orders));
            Collections.sort(sortMeal, new SortByQuantityUp());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else if (currentUser instanceof Restaurant){
            ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllItems(((Restaurant) currentUser).getOrders()));
            Collections.sort(sortMeal, new SortByQuantityUp());
            for (Sort sortmeal: sortMeal
                    ) {
                System.out.println((sortmeal.getType()).getName() + "     " + sortmeal.getQuantity());
            }
        }
        else {
            System.out.println("Your must log in first");
        }
    }

    /**
     * set up the state of the courier
     */
    public void onDuty(){
        if (currentUser instanceof Courier){
            ((Courier) currentUser).setState_OnDuty();
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *set up the state of the courier
     */
    public void offDuty(){
        if (currentUser instanceof Courier){
            ((Courier) currentUser).setState_OffDuty();
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Accept the order
     * and update the states of courier and order
     */
    public void accept() throws UserNotFoundException {
        if (currentUser instanceof Courier){
            if ( ((Courier) currentUser).getNewOrderCondition()){
                Order order = ((Courier) currentUser).getNewOrder();
                order.setDeliveryStateAsFinished();
                order.setCourier((Courier) currentUser);
                updateOrder(order);

                ((Courier) currentUser).addDeliveredOrdersCounter();
                ((Courier) currentUser).changePosition(order.getCustomer().getAddress());
                ((Courier) currentUser).addOrder2History(order);
                ((Courier) currentUser).removeNewOrder();
                ((Courier) currentUser).addDeliveryIncome(order.getDelivery_cost());
                updateUser(currentUser);
                System.out.println("Order" + order.getId() + " has been accepted by "+ currentUser.getName());
            }
            else {
                System.out.println("you don't have an order to accept");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * Refuse the order
     * and redelegate the order to other couriers
     */
    public void refuse() throws UserNotFoundException {
        if (currentUser instanceof Courier){
            if ( ((Courier) currentUser).getNewOrderCondition()){
                Order order = ((Courier) currentUser).getNewOrder();
                delegateOrder2Courier(order);

                ((Courier) currentUser).addOrder2RefuseList(order);
                ((Courier) currentUser).removeNewOrder();
                updateUser(currentUser);
                System.out.println("Order" + order.getId() + " has been refused by "+ currentUser.getName());
            }
            else {
                System.out.println("you don't have an order to accept");
            }
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     * change the position to where the courier wants
     *
     * @param coordinate new position
     */
    public void changePosition(Coordinate coordinate) throws UserNotFoundException {
        if (currentUser instanceof Courier){
            ((Courier) currentUser).changePosition(coordinate);
            updateUser(currentUser);
        }
        else {
            System.out.println("You must log in first");
        }
    }

    /**
     *
     * @param contactInfo
     */
    public void addContactInfo(String contactInfo){

    }

    /**
     *
     * @param username
     * @param agreement
     */
    public void associateAgreement(String username, String agreement){

    }

    public BigDecimal getTotal_income() {
        calculateFinancial();
        return total_income;
    }

    public BigDecimal getTotal_delivery_cost() {
        calculateFinancial();
        return total_delivery_cost;
    }

    public BigDecimal getTotal_profit() {
        calculateFinancial();
        return total_profit;
    }

    public BigDecimal getTarget_profit() {
        return target_profit;
    }

    public String getDeliveryPolicy() {
        return deliveryPolicy;
    }

    public BigDecimal getAverageIncomePerCustomer() {
        averageIncomePerCustomer();
        return averageIncomePerCustomer;
    }


}

