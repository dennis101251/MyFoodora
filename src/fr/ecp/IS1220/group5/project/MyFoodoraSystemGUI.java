package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.GUI.*;
import fr.ecp.IS1220.group5.project.exception.DuplicateNameException;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.*;
import fr.ecp.IS1220.group5.project.util.PasswordHash;

import javax.swing.*;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;

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
//                            loginInformation();
                            login.dispose();
                            JOptionPane.showMessageDialog(new JFrame(),"Welcome: "+myFoodoraSystemGUI.getCurrentUser().getName(),"Login",JOptionPane.INFORMATION_MESSAGE);



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
}
