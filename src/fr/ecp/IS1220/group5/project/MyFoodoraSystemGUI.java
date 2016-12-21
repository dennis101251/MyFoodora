package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.GUI.*;
import fr.ecp.IS1220.group5.project.menu.Food;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.user.*;
import fr.ecp.IS1220.group5.project.util.PasswordHash;

import javax.swing.*;
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

    @Override
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
                            //Login successfully
                            this.currentUser = myUser;
                            System.out.println( myUser.getName() + ": welcome to myFoodora!");
                            System.out.println("==============================================");
//                            loginInformation();

                            JOptionPane.showMessageDialog(new JFrame(),"Welcome: "+myFoodoraSystemGUI.getCurrentUser().getName(),"Login",JOptionPane.INFORMATION_MESSAGE);



                            creatDashboard(myUser);
                        }
                        else {
                            //Invalid password
                            System.out.println("Invalid password");
                            JOptionPane.showMessageDialog(new JFrame(),"Invalid password","Login",JOptionPane.INFORMATION_MESSAGE);
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
                    JOptionPane.showMessageDialog(new JFrame(),"You have been deactivated","Login",JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else {
                //User not found
                System.out.println("User: " + userName + " is not found in system");
                JOptionPane.showMessageDialog(new JFrame(),"User: " + userName + " is not found in system","Login",JOptionPane.INFORMATION_MESSAGE);
            }
        }
        else {
            //Forget to disconnect
            System.out.println("you have to disconnect first");
            JOptionPane.showMessageDialog(new JFrame(),"you have to disconnect first","Login",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Returns the list of all available restaurants
     * @return an array of restaurants
     */
    public Restaurant[] getRestaurants(){
        if (currentUser instanceof Customer){
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
            new CustomerDashboard2();
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


}
