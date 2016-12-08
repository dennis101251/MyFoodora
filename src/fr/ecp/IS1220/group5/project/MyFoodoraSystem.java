package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.RoundingMode;

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
    private Userlist users = new Userlist();
    private ArrayList<Order> orders = new ArrayList<Order>();
    public double service_fee = 5;
    public double markup_percentage = 0.1;
    public double delivery_cost_price = 0.1;
    private Scanner scanner = new Scanner(System.in);




    /**
     *
     */
    public BigDecimal service_fee = new BigDecimal("0.90");

    /**
     *
     */
    public BigDecimal markup_percentage = new BigDecimal("0.1");

    /**
     *
     */
    public BigDecimal delivery_cost_price = new BigDecimal("1.0");


    private User currentUser = null;
    private Restaurant currentRestaurant = null;
    private Order currentOrder = null;

    private BigDecimal total_income = new BigDecimal("0");
    private BigDecimal total_delivery_cost = new BigDecimal("0");
    private BigDecimal total_profit = new BigDecimal("0");
    private BigDecimal target_profit = new BigDecimal("0");

    public MyFoodoraSystem() {

//        Load all registered the users
        retrieveUsers();
        retrieveOrders();
        System.out.println(orders.toString());
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

    /**
     * Returns the user with a given username (if he exists)
     *
     * @param userName The username of the searched user
     *
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
        System.out.println("didn't find " + userName);
        return null;
    }

    /**
     * Add a user to the list of registered users.
     * @param user the user to add in the list.
     */
    public void addUser(User user) {
        this.users.addUser(user);
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
     * Retrieves the stored orders from a .ser file and updates the orders variable
     *
     * @see MyFoodoraSystem#orders
     *
     */
    public void retrieveOrders() {

        //Verify whether the Order file exists

        File file = new File("tmp/orders.ser");

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

    /**
     * Saves the current users in a .ser file, in order to be retrieved in the future.
     *
     * @see MyFoodoraSystem#retrieveOrders()
     */
    public void saveOrders(){
        try {
            FileOutputStream fileOut = new FileOutputStream("tmp/orders.ser");
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
            delivery_cost_price = financial.delivery_cost;
        } else {
            System.out.println(">> There is no Financial in system");
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
                            System.out.println("=======================");
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
    public void registerRestaurant(String name, String username, Coordinate address, String password) {
        if (getUser(username) == null){
            User newRestaurant = new Restaurant(name, username, password, address);
            this.users.addUser(newRestaurant);
            System.out.println("You have been registered successfully!");
            System.out.println("======================================");
        }
        else{
            System.out.println("this username is not valid");
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
    public void registerCustomer(String firstName, String lastName, String username, String password, Coordinate address, String mail, String phone) {
        if (getUser(username) == null){
            User newCustomer = new Customer(firstName, lastName, username, password, address, mail, phone);
            this.users.addUser(newCustomer);
            System.out.println("You have been registered successfully!");
            System.out.println("======================================");
        }
        else{
            System.out.println("this username is not valid");
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
    public void registerManager(String name, String lastName, String username, String password){
        if (getUser(username) == null){
            User newManager = new Manager(name, username, password, lastName);
            this.users.addUser(newManager);
            System.out.println("You have been registered successfully!");
            System.out.println("======================================");
        }
        else{
            System.out.println("this username is not valid");
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
    private void calculateFinancial(){
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
     *
     * Enables a manger to disactivate the account of a given user.
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
     *
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
     */
    public void setMarkup_percentage(BigDecimal markup_percentage){
        if (currentUser instanceof Manager){
            if (markup_percentage.compareTo(new BigDecimal("1")) == 1){ //if markup_percentage > 1
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
     */
    public void setDelivery_cost(BigDecimal delivery_cost){
        if (currentUser instanceof Manager){
            if (!delivery_cost.equals(new BigDecimal("0"))){
                this.delivery_cost_price = delivery_cost;
                System.out.println(">> Current delivery cost: " + delivery_cost);
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
     *
     *
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
     *
     * Displays the average income per customer.
     * Only for managers.
     *
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
     *
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
     * determining the Service Fee to meet the target profit
     *
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

                    BigDecimal tmpService_fee = new BigDecimal("0");
                    BigDecimal tmpMarkup_percentage = new BigDecimal("0");
                    BigDecimal tmpDelivery_cost_price = new BigDecimal("0");

                    tmpDelivery_cost_price = BigDecimal.valueOf(delivery_cost_price);
                    tmpMarkup_percentage = BigDecimal.valueOf(markup_percentage);

                    BigDecimal tmp = new BigDecimal("0");
                    tmp = target_profit.add(sumDeliveryDistance.multiply(tmpDelivery_cost_price));
                    tmp = tmp.subtract(sumOrderPrice.multiply(tmpMarkup_percentage.add(BigDecimal.valueOf(1))));
                    tmpService_fee = tmp.divide(BigDecimal.valueOf(orders.size()), 3, RoundingMode.HALF_UP);

                    System.out.println("you have choose targetProfit_ServiceFee");
                    System.out.println("current parameters: ");
                    System.out.println(">> markup percentage: " + Percentage.display(BigDecimal.valueOf(markup_percentage)) );
                    System.out.println(">> delivery price: " + Money.display(BigDecimal.valueOf(delivery_cost_price)) );
                    System.out.println(">> service fee: " + Money.display(BigDecimal.valueOf(service_fee)));
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
     *
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

                    BigDecimal tmpService_fee = new BigDecimal("0");
                    BigDecimal tmpMarkup_percentage = new BigDecimal("0");
                    BigDecimal tmpDelivery_cost_price = new BigDecimal("0");

                    tmpDelivery_cost_price = BigDecimal.valueOf(delivery_cost_price);
                    tmpService_fee = BigDecimal.valueOf(service_fee);

                    BigDecimal tmp = new BigDecimal("0");
                    tmp = target_profit.add(sumDeliveryDistance.multiply(tmpDelivery_cost_price));
                    tmp = tmp.subtract(tmpService_fee.multiply(BigDecimal.valueOf(orders.size())));
                    tmpMarkup_percentage = tmp.divide(sumOrderPrice, 6, RoundingMode.HALF_UP).subtract(BigDecimal.valueOf(1));

                    NumberFormat percent = NumberFormat.getPercentInstance();
                    percent.setMaximumFractionDigits(3);

                    System.out.println("you have choose targetProfit_Markup");
                    System.out.println("current parameters: ");
                    System.out.println(">> markup percentage: " + Percentage.display(BigDecimal.valueOf(markup_percentage)) );
                    System.out.println(">> delivery price: " + Money.display(BigDecimal.valueOf(delivery_cost_price)) );
                    System.out.println(">> service fee: " + Money.display(BigDecimal.valueOf(service_fee)));
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
     *
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

                        BigDecimal tmpService_fee = new BigDecimal("0");
                        BigDecimal tmpMarkup_percentage = new BigDecimal("0");
                        BigDecimal tmpDelivery_cost_price = new BigDecimal("0");

                        tmpService_fee = BigDecimal.valueOf(service_fee);
                        tmpMarkup_percentage = BigDecimal.valueOf((markup_percentage));

                        BigDecimal tmp = new BigDecimal("0");
                        tmp = target_profit.subtract(tmpService_fee.multiply(BigDecimal.valueOf(orders.size())));
                        tmp = tmp.subtract(sumOrderPrice.multiply(tmpMarkup_percentage.add(BigDecimal.valueOf(1))));
                        tmp = tmp.divide(sumDeliveryDistance, 6, RoundingMode.HALF_UP);
                        tmpDelivery_cost_price = tmp.abs();

                        NumberFormat percent = NumberFormat.getPercentInstance();
                        percent.setMaximumFractionDigits(3);
                        System.out.println("========================================");
                        System.out.println("you have choose targetProfit_Markup");
                        System.out.println("========================================");
                        System.out.println("current parameters: ");
                        System.out.println(">> markup percentage: " + Percentage.display(BigDecimal.valueOf(markup_percentage)) );
                        System.out.println(">> delivery price: " + Money.display(BigDecimal.valueOf(delivery_cost_price)) );
                        System.out.println(">> service fee: " + Money.display(BigDecimal.valueOf(service_fee)));
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
                if (restaurants.size()>1){
                    for (int i = 0; i < restaurants.size() - 1; i++) {
                        if (restaurants.get(i).getIncome().doubleValue() > restaurants.get(i+1).getIncome().doubleValue()){
                            bestRestaurant = restaurants.get(i);
                        }
                        else {
                            bestRestaurant = restaurants.get(i+1);
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
                if (restaurants.size()>1){
                    for (int i = 0; i < restaurants.size() - 1; i++) {
                        if (restaurants.get(i).getIncome().doubleValue() < restaurants.get(i+1).getIncome().doubleValue()){
                            leastRestaurant = restaurants.get(i);
                        }
                        else {
                            leastRestaurant = restaurants.get(i+1);
                        }
                    }

                    System.out.println(">> The least is " + leastRestaurant.getName());
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
     *
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
     *
     * Enables a Customer to choose a restaurant by writing the restaurant's name.
     * By calling this method, the system creates an empty order for the customer.
     *
     * @param restaurant
     *
     * @see Restaurant
     */
    public void chooseRestaurant(String restaurant){
        if (currentUser instanceof Customer){
            boolean isFound = false;
            for (User user: users.getUsers()
                 ) {
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
     */
    public void endOrder(){
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
     *
     * <b>Shows the complete customer's history of orders.</b>
     *
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
     *
     * <b>Shows the fidelity program to which the customer is registered, and the total of collected points.</b>
     *  Only for customers.
     *
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
     *
     */
    public void checkInfoBoard(){
        if (currentUser instanceof Customer) {
            if (((Customer) currentUser).infoBoard.isNotified()){
                Integer num = ((Customer) currentUser).infoBoard.getNumberOfNewMeassages();
                ArrayList<String> messages = ((Customer) currentUser).infoBoard.getMessages();
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

    /**
     *
     * <b>Creates a new Item to the Restaurant's menu.</b>
     * Only for Restaurants
     *
     * @param itemName
     * @param price
     */
    public void createItem(String itemName, BigDecimal price){
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;
            Item item = new Item(itemName, price, ItemCategory.MainDish, ItemType.Standard);
            restaurant.addItem(item);

            System.out.println(item + " was successfully created!");

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
            Meal meal = new Meal(mealName, (Restaurant) currentUser);
            restaurant.addMeal(meal);

            System.out.println(meal + " was successfully created!");

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
            meal.addItem(item);

            System.out.println(item + " was successfully added to " + meal + "!");

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

    public void saveMeal(String mealName){

    }

    /**
     *
     * @param string
     */
    public void sendMessage(String string){
        if (currentUser instanceof Restaurant){
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

    /**
     *
     *
     *
     * @param meal
     */
    public void sendSpecialOffer(Meal meal){

        sendMessage("Meal of the week special offer: " + meal);
    }


    /**
     *
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

    public void setSpecialOffer(String mealName){

    }

    public void removeFromSpecialOffer(String mealName){

    }

    public void addDish(String dishName, String dishCategory, BigDecimal unitPrice){

    }

    /**
     *
     * @param username
     */
    public void onDuty(String username){

    }

    /**
     *
     * @param username
     */
    public void offDuty(String username){

    }

    /**
     *
     * @param contactInfo
     */
    public void addContactInfo(String contactInfo){

    }

    /**
     *
     * @param userName
     * @param cardType
     */
    public void associateCard(String userName, String cardType){

    }

    /**
     *
     * @param username
     * @param agreement
     */
    public void associateAgreement(String username, String agreement){

    }

}

