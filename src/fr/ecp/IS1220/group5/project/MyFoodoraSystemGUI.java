package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.GUI.CourierDashboard;
import fr.ecp.IS1220.group5.project.GUI.CustomerDashboard;
import fr.ecp.IS1220.group5.project.GUI.ManagerDashboard;
import fr.ecp.IS1220.group5.project.GUI.RestaurantDashboard;
import fr.ecp.IS1220.group5.project.GUI.customerDashboard.InfoBoardFrame;
import fr.ecp.IS1220.group5.project.exception.DuplicateNameException;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.*;
import fr.ecp.IS1220.group5.project.util.*;

import javax.swing.*;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * Override the method we have in MyFoodoraSystem
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class MyFoodoraSystemGUI extends MyFoodoraSystem{
    /**
     * use the singleton pattern
     */
    private static MyFoodoraSystemGUI myFoodoraSystemGUI;

    private MyFoodoraSystemGUI(){
        super();
    }

    public static MyFoodoraSystemGUI getInstance(){
        if (myFoodoraSystemGUI == null){
            myFoodoraSystemGUI = new MyFoodoraSystemGUI();
            return myFoodoraSystemGUI;
        }
        else {
            return myFoodoraSystemGUI;
        }
    }

    public void loginUser(String userName, String password, Login login) {
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
                            //Login successfully
                            this.currentUser = myUser;
                            System.out.println( myUser.getName() + ": welcome to myFoodora!");
                            System.out.println("==============================================");
                            login.dispose();
                            //show the login hint info
                            loginInformation();
                            creatDashboard(myUser);
                        }
                        else {
                            //Invalid password
                            System.out.println("Invalid password");
                            JOptionPane.showMessageDialog(new JFrame(),"Invalid password","Login",JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeySpecException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    //Deactivated
                    System.out.println("Your account has been disactivated, please contact Manager");
                    JOptionPane.showMessageDialog(new JFrame(),"You have been deactivated","Login",JOptionPane.ERROR_MESSAGE);
                }
            }
            else {
                //User not found
                System.out.println("User: " + userName + " is not found in system");
                JOptionPane.showMessageDialog(new JFrame(),"User: " + userName + " is not found in system","Login",JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            //Forget to disconnect
            System.out.println("you have to disconnect first");
            JOptionPane.showMessageDialog(new JFrame(),"you have to disconnect first","Login",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loginInformation(){
//        System.out.println("login information");
        String message = "Welcome: "+ myFoodoraSystemGUI.getCurrentUser().getName() + "\n";

        if (currentUser instanceof Customer){
            if (((Customer) currentUser).infoBoard.isNotified()){
                Integer num = ((Customer) currentUser).infoBoard.getNumberOfNewMeassages();
                ArrayList<Message> messages = ((Customer) currentUser).infoBoard.getMessages();
                if (messages.isEmpty()){
                    message += "You have no message";
                    System.out.println("You have no message");
                }
                else {
                    if ( num > 1){
                        message += "You have " + num + " new messages";
//                        JOptionPane.showMessageDialog(new JFrame(), "You have " + num + " new messages", "NEW Message",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("You have " + num + " new messages");
                    }
                    else {
                        message += "You have " + num + " new message";
//                        JOptionPane.showMessageDialog(new JFrame(), "You have " + num + " new message", "NEW Message",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("You have " + num + " new message");
                    }
                    message += "\n\n"+ "Do you want to show the message?";
                    int userChoice = JOptionPane.showOptionDialog(new JFrame(),message,"Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"Cancel","Show the message"},null);
                    switch (userChoice){
                        case 1:
                            //show the information
                            InfoBoardFrame.getIntance();
                            break;
                        case 0:
                            //cancel
                            break;
                        default:
                            System.out.println("error");
                    }
                }
            }
            else {
                String str1 = "You can't receive message from myFoodora";
                String str2 = "Please set infoBoard notified on";
                message = message + str1 + "\n" + str2;
                System.out.println("You can't receive message from myFoodora");
                System.out.println("Please set infoBoard notified on");
                JOptionPane.showMessageDialog(new JFrame(), message,"Login",JOptionPane.INFORMATION_MESSAGE);

            }
        }
        else if (currentUser instanceof Restaurant){
            message += "You have " + ((Restaurant)currentUser).getOrders().size() + " orders";
            JOptionPane.showMessageDialog(new JFrame(), message,"Login",JOptionPane.INFORMATION_MESSAGE);
        }
        else if (currentUser instanceof Manager){
            message += "MyFoodora has " + myFoodoraSystemGUI.orders.size() + " orders";
            JOptionPane.showMessageDialog(new JFrame(), message,"Login",JOptionPane.INFORMATION_MESSAGE);
        }
        else if (currentUser instanceof Courier){
            if (((Courier) currentUser).getNewOrderCondition()){
                message += "You have a NEW order to deliver";
            }
            else {
                message += "There is no order to deliver";
            }
            JOptionPane.showMessageDialog(new JFrame(), message,"Login",JOptionPane.INFORMATION_MESSAGE);
        }
        else {
            message = "error";
            JOptionPane.showMessageDialog(new JFrame(),message,"Login",JOptionPane.ERROR_MESSAGE);
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
                        JOptionPane.showMessageDialog(new JFrame(), "You have " + num + " new messages", "NEW Message",JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("You have " + num + " new messages");
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(), "You have " + num + " new message", "NEW Message",JOptionPane.INFORMATION_MESSAGE);
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
     * Returns the list of all available restaurants
     * @return an array of restaurants
     */
    public Restaurant[] getRestaurants(){
        if (currentUser instanceof Customer || currentUser instanceof Manager){
            ArrayList<Restaurant> allReastaurant= new ArrayList<>();
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Restaurant){
                    allReastaurant.add((Restaurant) user);
                }
            }

            return allReastaurant.toArray(new Restaurant[allReastaurant.size()]);
        }

        return new Restaurant[0];
    }

    public Restaurant[] getRestaurantsUp(){
        Restaurant[] a = new Restaurant[getRestaurants().length];
        ArrayList<Restaurant> tmp = new ArrayList<>();
        for (Restaurant restaurant: getRestaurants()){
            tmp.add(restaurant);
        }
        Collections.sort(tmp,new SortByIncomeUp());
        return tmp.toArray(a);
    }

    public Restaurant[] getRestaurantsDown(){
        Restaurant[] a = new Restaurant[getRestaurants().length];
        ArrayList<Restaurant> tmp = new ArrayList<>();
        for (Restaurant restaurant: getRestaurants()){
            tmp.add(restaurant);
        }
        Collections.sort(tmp,new SortByIncomeDown());
        return tmp.toArray(a);
    }

    public Customer[] getCustomers(){
        if (currentUser instanceof Manager){
            ArrayList<Customer> allCustomers= new ArrayList<>();
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Customer){
                    allCustomers.add((Customer) user);
                }
            }

            return allCustomers.toArray(new Customer[allCustomers.size()]);
        }

        return new Customer[0];
    }

    public Customer[] getCustomersUp(){
        Customer[] a = new Customer[getCustomers().length];
        ArrayList<Customer> tmp = new ArrayList<>();
        for (Customer customer: getCustomers()){
            tmp.add(customer);
        }
        Collections.sort(tmp,new SortByExpenseUp());
        return tmp.toArray(a);
    }

    public Customer[] getCustomersDown(){
        Customer[] a = new Customer[getCustomers().length];
        ArrayList<Customer> tmp = new ArrayList<>();
        for (Customer customer: getCustomers()){
            tmp.add(customer);
        }
        Collections.sort(tmp,new SortByExpenseDown());
        return tmp.toArray(a);
    }

    public Courier[] getCouriers(){
        if (currentUser instanceof Manager){
            ArrayList<Courier> allCouriers= new ArrayList<>();
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Courier){
                    allCouriers.add((Courier) user);
                }
            }

            return allCouriers.toArray(new Courier[allCouriers.size()]);
        }

        return new Courier[0];
    }

    public Courier[] getCouriersUp(){
        Courier[] a = new Courier[getCouriers().length];
        ArrayList<Courier> tmp = new ArrayList<>();
        for (Courier courier: getCouriers()){
            tmp.add(courier);
        }
        Collections.sort(tmp,new SortByOrderUp());
        return tmp.toArray(a);
    }

    public Courier[] getCouriersDown(){
        Courier[] a = new Courier[getCouriers().length];
        ArrayList<Courier> tmp = new ArrayList<>();
        for (Courier courier: getCouriers()){
            tmp.add(courier);
        }
        Collections.sort(tmp,new SortByOrderDown());
        return tmp.toArray(a);
    }

    public String[] getRestaurantsNames(){
        if (currentUser instanceof Customer){
            ArrayList<String> allReastaurant= new ArrayList<>();
            for (User user: users.getUsers()
                    ) {
                if (user instanceof Restaurant){
                    allReastaurant.add(user.getName());
                }
            }

            return allReastaurant.toArray(new String[allReastaurant.size()]);
        }
        else {

            return new String[0];

        }
    }

    public void creatDashboard(User user){
        if (user instanceof Customer){
            new CustomerDashboard();
        }
        else if (user instanceof Restaurant){
            new RestaurantDashboard();
        }
        else if (user instanceof Manager){
            new ManagerDashboard();
        }
        else if (user instanceof Courier){
            new CourierDashboard();
        }
    }

    public void setCurrentRestaurant(Restaurant restaurant){

        currentRestaurant = restaurant;


    }

    public Food[] getMenu(){
            if (currentUser instanceof Customer){
                if (currentRestaurant != null){

                    ArrayList<Food> menu = new ArrayList<>();

                    for (Item item: currentRestaurant.getItems()){
                        menu.add(item);
                    }

                    for (Meal meal:currentRestaurant.getMeals()){
                        menu.add(meal);
                    }

                    return menu.toArray(new Food[menu.size()]);

                }
                else {
                    return new Food[0];
                }
            }
            else {
                return new Food[0];
            }
    }

    public String[] getMenuNames(){
        if (currentUser instanceof Customer){
            if (currentRestaurant != null){

                ArrayList<String> menu = new ArrayList<>();

                for (Item item: currentRestaurant.getItems()){
                    menu.add(item.getName());
                }

                for (Meal meal:currentRestaurant.getMeals()){
                    menu.add(meal.getName());
                }

                return menu.toArray(new String[menu.size()]);

            }
            else {
                return new String[0];
            }
        }
        else {
            return new String[0];
        }
    }

    @Override
    public void disconnectUser() {
        if (currentUser!=null){
            System.out.println(currentUser.getName() + " has logged out");
            JOptionPane.showMessageDialog(new JFrame(), myFoodoraSystemGUI.getCurrentUser().getName() + " has disconnect","Disconnect",JOptionPane.PLAIN_MESSAGE);
            this.currentUser = null;
        }
        else {
            System.out.println("there is no user connected in system");
            JOptionPane.showMessageDialog(new JFrame(),"there is no user connected in system","Disconnect",JOptionPane.ERROR_MESSAGE);
        }
    }

    public Item[] getItems() {
        ArrayList<Item> items = ((Restaurant) currentUser).getItems();
        return items.toArray(new Item[items.size()]);

    }

    public Meal[] getMeals() {

        ArrayList<Meal> meals = ((Restaurant) currentUser).getMeals();
        return meals.toArray(new Meal[meals.size()]);

    }

    public String[] getItems(ItemCategory itemCategory, FoodType foodType) {

       ArrayList<Item> items = ((Restaurant) currentUser).getItems();
       ArrayList<String> itemsOfCategoryAndType = new ArrayList<>();

       for (Item item : items){

           if ((item.getItemCategory() == itemCategory) && (item.getFoodType() == foodType)){

               itemsOfCategoryAndType.add(item.getName());

           }

       }

       return itemsOfCategoryAndType.toArray(new String[itemsOfCategoryAndType.size()]);

    }

    public void createItemGUI(String itemName, BigDecimal price, ItemCategory itemCategory, FoodType foodType) throws EmptyNameException, DuplicateNameException {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getItem(itemName) != null){ //This item already exists

                throw new DuplicateNameException();

            } else {

                Item item = new Item(itemName, price, itemCategory, foodType);
                restaurant.addItem(item);

                System.out.println(item + " was successfully created!");

            }


        } else {

            System.out.println("Your restaurant must be logged in to create a item.");

        }
    }

    public void createMealGUI(String mealName, MealCategory mealCategory, FoodType foodType) throws DuplicateNameException, EmptyNameException {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;

            if (restaurant.getMeal(mealName) != null){ //This meal already exists

                throw new DuplicateNameException();

            } else {

                Meal meal = null;

                meal = new Meal(mealName, (Restaurant) currentUser, mealCategory, foodType);

                restaurant.addMeal(meal);

                System.out.println(meal + " was successfully created!");

            }


        } else {

            System.out.println("Your restaurant must be logged in to create a meal.");

        }
    }

    public void addDish2MealGUI(String itemName, String mealName) throws IncompatibleFoodTypeException, TooManyItemsException {
        if (currentUser instanceof Restaurant){

            Restaurant restaurant = (Restaurant) currentUser;
            Meal meal = restaurant.getMeal(mealName);
            Item item = restaurant.getItem(itemName);
            meal.addItem(item);

        } else {

            System.out.println("Your restaurant must be logged in to add a dish to a meal.");

        }
    }

    public void setGenericDiscountFactor(BigDecimal GenericDiscountFactor){
        if (currentUser instanceof Restaurant){
            if (GenericDiscountFactor.doubleValue() < 1 && GenericDiscountFactor.doubleValue() > 0){
                ((Restaurant) currentUser).setGenericDiscountFactor(GenericDiscountFactor);
                JOptionPane.showMessageDialog(new JFrame(), "The discount factor has been applied successfully!","Success", JOptionPane.INFORMATION_MESSAGE);
                saveMenu();
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(),"The discount factor must be > 0 and < 1.","Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }

    public void setSpecialDiscountFactor(BigDecimal specialDiscountFactor){
        if (currentUser instanceof Restaurant){
            if (specialDiscountFactor.doubleValue() < 1 && specialDiscountFactor.doubleValue() > 0){
                ((Restaurant) currentUser).setSpecialDiscountFactor(specialDiscountFactor);
                JOptionPane.showMessageDialog(new JFrame(), "The discount factor has been applied successfully!","Success", JOptionPane.INFORMATION_MESSAGE);
                saveMenu();
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(),"The discount factor must be > 0 and < 1.","Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Your restaurant must log in.");
        }
    }


    /**
     * sort ordered items in a sequence given by the value of comparator
     */
    public DefaultListModel<String> sortMeals(Comparator comparator){

        DefaultListModel<String> mealsListModel = new DefaultListModel<>();

        ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllMeals(((Restaurant) currentUser).getOrders()));
        Collections.sort(sortMeal, comparator);
        for (Sort sortmeal: sortMeal) {
            mealsListModel.addElement((sortmeal.getType()).getName() + "     Qty: " + sortmeal.getQuantity());
        }

        return mealsListModel;

    }


    /**
     * sort ordered items in a sequence given by the value of comparator
     */
    public DefaultListModel<String> sortItems(Comparator comparator){

        DefaultListModel<String> itemsListModel = new DefaultListModel<>();

        ArrayList<Sort> sortMeal = getQuantityOfFoods(getAllItems(((Restaurant) currentUser).getOrders()));
        Collections.sort(sortMeal, comparator);
        for (Sort sortmeal: sortMeal) {
            itemsListModel.addElement((sortmeal.getType()).getName() + "     Qty: " + sortmeal.getQuantity());
        }

        return itemsListModel;

    }
}
