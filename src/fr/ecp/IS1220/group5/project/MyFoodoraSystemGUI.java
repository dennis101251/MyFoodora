package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.GUI.*;
import fr.ecp.IS1220.group5.project.menu.Food;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.user.*;
import fr.ecp.IS1220.group5.project.util.*;

import javax.swing.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collections;

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
}
