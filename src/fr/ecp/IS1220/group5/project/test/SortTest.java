package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.util.Sort;
import fr.ecp.IS1220.group5.project.util.SortByQuantityDown;
import fr.ecp.IS1220.group5.project.util.SortByQuantityUp;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by dennis101251 on 2016/12/13.
 */
public class SortTest {
    @Test
    public void SortTest(){
        Order.delateOrders();
        Userlist.delateUserFile();

        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order1 = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        Meal meal1 = new Meal("Happy", restaurant);
        Meal meal2 = new Meal("Sad",restaurant);
        order1.addMeal(meal1);
        order1.addMeal(meal1);
        order1.addMeal(meal2);
        Order order2 = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        order2.addMeal(meal2);
        order2.addMeal(meal2);

        Item item1 = null;
        try {
            item1 = new Item("Onion",new BigDecimal(1), ItemCategory.Dessert, FoodType.Standard);
            Item item2 = new Item ("Chicken",new BigDecimal(1), ItemCategory.Dessert, FoodType.Standard);
            Item item3 = new Item ("Burger",new BigDecimal(1), ItemCategory.Dessert, FoodType.Standard);

            order1.addItem(item1);
            order1.addItem(item1);
            order1.addItem(item2);
            order1.addItem(item3);
            order2.addItem(item3);
            order2.addItem(item3);

        } catch (EmptyNameException e) {
            e.printStackTrace();
        }



        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        ArrayList<Sort> sortMeals = myFoodoraSystem.getQuantityOfFoods(myFoodoraSystem.getAllMeals(orders));
        ArrayList<Sort> sortItems = myFoodoraSystem.getQuantityOfFoods(myFoodoraSystem.getAllItems(orders));

        for (Sort sortmeal: sortItems
                ) {
            System.out.println((sortmeal.getType()).getName() + "||" + sortmeal.getQuantity());
        }

        Collections.sort(sortItems, new SortByQuantityUp());

        for (Sort sortmeal: sortItems
                ) {
            System.out.println((sortmeal.getType()).getName() + "||" + sortmeal.getQuantity());
        }

        Collections.sort(sortItems, new SortByQuantityDown());

        for (Sort sortmeal: sortItems
                ) {
            System.out.println((sortmeal.getType()).getName() + "||" + sortmeal.getQuantity());
        }
    }

    @Test
    public void SortTestInSystem() throws UserNotFoundException {
        Order.delateOrders();
        Userlist.delateUserFile();

        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        myFoodoraSystem.registerRestaurant("McDonalds", "mcdonalds",new Coordinate(0,0),"123456");
        myFoodoraSystem.loginUser("mcdonalds","123456");
        myFoodoraSystem.createItem("FrenchFries", new BigDecimal(2));
        myFoodoraSystem.createItem("BigMac",new BigDecimal(4));
        myFoodoraSystem.createItem("CocaCola", new BigDecimal(2));
        myFoodoraSystem.createMeal("BestOf");
        myFoodoraSystem.addDish2Meal("FrenchFries", "BestOf");
        myFoodoraSystem.addDish2Meal("BigMac","BestOf");
        myFoodoraSystem.addDish2Meal("CocaCola","BestOf");
        myFoodoraSystem.saveMenu();

        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.registerCustomer("Marco", "Merlotti", "MM", "501", new Coordinate(50,50), "marco.merlotti@student.ecp.fr", "0611041568");
        myFoodoraSystem.loginUser("MM", "501");
        myFoodoraSystem.showRestaurant();
        myFoodoraSystem.chooseRestaurant("McDonalds");
        myFoodoraSystem.showMenu();
        myFoodoraSystem.addItem2Order("FrenchFries",1);
        myFoodoraSystem.addItem2Order("CocaCola",3);
        myFoodoraSystem.addMeal2Order("BestOf", 2);
        myFoodoraSystem.showOrder();
        myFoodoraSystem.endOrder();

        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.registerCustomer("WU", "DANYI", "WD", "501", new Coordinate(50,50), "marco.merlotti@student.ecp.fr", "0611041568");
        myFoodoraSystem.loginUser("WD", "501");
        myFoodoraSystem.showRestaurant();
        myFoodoraSystem.chooseRestaurant("McDonalds");
        myFoodoraSystem.showMenu();
        myFoodoraSystem.addItem2Order("FrenchFries",1);
        myFoodoraSystem.addItem2Order("CocaCola",3);
        myFoodoraSystem.addItem2Order("BigMac",2);
        myFoodoraSystem.addMeal2Order("BestOf", 1);
        myFoodoraSystem.showOrder();
        myFoodoraSystem.endOrder();

        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.loginUser("mcdonalds","123456");
        myFoodoraSystem.sortLeasItem();
        myFoodoraSystem.sortMostItem();
        myFoodoraSystem.sortMostMeal();
        myFoodoraSystem.sortLeastMeal();
    }
}