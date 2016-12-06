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
    private ArrayList<Order> orders = new ArrayList<Order>();
    public double service_fee = 0;
    public double markup_percentage = 0;
    public double delivery_cost_price = 1;
    private Scanner scanner = new Scanner(System.in);
    private User currentUser = null;
    private Restaurant currentRestaurant = null;
    private Order currentOrder = null;
    private BigDecimal total_income = new BigDecimal("0");
    private BigDecimal total_delivery_cost = new BigDecimal("0");
    private BigDecimal total_profit = new BigDecimal("0");

    public MyFoodoraSystem() {

//        Load all registered the users
        retrieveUsers();
        retrieveOrders();
        System.out.println(orders.toString());
        System.out.println(users.toString());

        System.out.println("init successfully");
    }

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

    public void addUser(User user) {
        this.users.addUser(user);
    }

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

    public void retrieveUsers() {

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
            delivery_cost_price = financial.delivery_cost;
        } else {
            System.out.println(">> There is no Financial in system");
        }
    }

    /** System */
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

    public void showCurrentUser(){
        if (currentUser != null){
            System.out.println("Current User is: " + currentUser.getName());
        }
        else {
            System.out.println("there is no user connected in myFoodora");
        }
    }

    public void disconnectUser(){
        if (currentUser!=null){
            System.out.println(currentUser.getName() + " has logged out");
            this.currentUser = null;
        }
        else {
            System.out.println("there is no user connected in system");
        }
    }

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

    private void calculateFinancial(){
        //Total income
        BigDecimal money =new BigDecimal("0");
        for (Order order: orders
                ) {
            money = money.add(order.getActual_price());
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


    /** Manager */
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

    public void setService_fee(double service_fee){
        if (currentUser instanceof Manager){
            if  (service_fee > 0){
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

    public void setMarkup_percentage(double markup_percentage){
        if (currentUser instanceof Manager){
            if (markup_percentage > 1){
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

    public void setDelivery_cost(double delivery_cost){
        if (currentUser instanceof Manager){
            if (delivery_cost > 0){
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

    public void totalIncome(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_income));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void totalDeliveryCost(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_delivery_cost));
        }
        else {
            System.out.println("You must log in first");
        }
    }

    public void totalProfit(){
        if (currentUser instanceof Manager){
            calculateFinancial();
            System.out.println(Money.display(total_profit));
        }
        else {
            System.out.println("You must log in first");
        }
    }

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

    /** Customer */
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

    public void endOrder(){
        if (currentUser instanceof Customer){
            if (currentOrder != null){
                if (!currentOrder.isEmpty()){

                    //Show the detail of order first
                    currentOrder.showOrder();

                    //apply the fidelity card
                    currentOrder.applyFidelityCard(((Customer) currentUser).getFidelityCard().compute_discounted_price(currentOrder.getTotal_price()));
                    System.out.println("After apply fidelity card>> Total: " + Money.display(currentOrder.getActual_price()));

                    //add points to fidelity card
                    ((Customer) currentUser).addPoints(currentOrder.getActual_price().intValue());

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

    public void showFidelityCard(){
        if (currentUser instanceof Customer){
            System.out.println(((Customer) currentUser).getFidelityCard().getFidelityCardName());
            System.out.println(((Customer) currentUser).getFidelityCard().getPoints() + " points");
        }
        else {
            System.out.println("You must log in first");
        }
    }

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

    /** Restaurant */
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

    public void createMeal(String mealName) {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;
            Meal meal = new Meal(mealName);
            restaurant.addMeal(meal);

            System.out.println(meal + " was successfully created!");

        } else {

            System.out.println("Your restaurant must be logged in to create a meal.");

        }
    }

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

    public void saveMenu() {

        if (currentUser instanceof Restaurant){

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

    public void setMealPrice(String mealName){
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;
            Meal meal = restaurant.getMeal(mealName);
            restaurant.setMealPrice(meal);

        } else {
            System.out.println("Your restaurant must be logged in to set the price of a meal.");
        }
    }

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
    /** Courier */
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

