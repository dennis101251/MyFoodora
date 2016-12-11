package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by dennis101251 on 2016/12/9.
 */
public class MyFoodoraSystemTest {
    @Test
    public void findCourier_FastDelivery() throws Exception {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        Courier courier1 = new Courier("Bill","BG","123456","Gates",new Coordinate(10,10),"123456");
        Courier courier2 = new Courier("Steve","SJ","123456","Jobs",new Coordinate(20,20),"123456");
        Courier courier3 = new Courier("Bill","BC","123456","Clliton",new Coordinate(15,20),"123456");
        ArrayList<Courier> couriers = new ArrayList<>();
        couriers.add(courier1);
        couriers.add(courier2);
        couriers.add(courier3);
        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        Courier bestCourier =  myFoodoraSystem.findCourier_FastDelivery(couriers,order);

        Assert.assertTrue(bestCourier.getName().equalsIgnoreCase("Bill"));
    }

    @Test
    public void findCourier_FastDelivery_IfEmpty() throws Exception {
        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        ArrayList<Courier> couriers = new ArrayList<>();
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        Courier bestCourier = myFoodoraSystem.findCourier_FastDelivery(couriers, order);

        Assert.assertTrue(bestCourier == null);

    }
    @Test
    public void findCourier_FairOccupationDelivery() throws Exception  {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        Courier courier1 = new Courier("Bill","BG","123456","Gates",new Coordinate(10,10),"123456");
        courier1.setDeliveredOrdersCounter(3);
        Courier courier2 = new Courier("Steve","SJ","123456","Jobs",new Coordinate(20,20),"123456");
        courier2.setDeliveredOrdersCounter(2);
        Courier courier3 = new Courier("Zemin","JZ","123456","Jiang",new Coordinate(15,20),"123456");
        courier3.setDeliveredOrdersCounter(2);
        ArrayList<Courier> couriers = new ArrayList<>();
        couriers.add(courier1);
        couriers.add(courier2);
        couriers.add(courier3);

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        Courier bestCourier =  myFoodoraSystem.findCourier_FairOccupationDelivery(couriers,order);

        Assert.assertTrue(bestCourier.getName().equalsIgnoreCase("Steve"));
    }

    @Test
    public void updateUserTest() throws UserNotFoundException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        myFoodoraSystem.registerCourier("Zemin","Jianf","JZ","123456",new Coordinate(10,10),"123456");

        Courier courier = (Courier) myFoodoraSystem.getUser("JZ");
        Assert.assertEquals(courier.getDeliveredOrdersCounter(),0);

        courier.setDeliveredOrdersCounter(10);

        myFoodoraSystem.updateUser(courier);
        courier = (Courier) myFoodoraSystem.getUser("JZ");
        Assert.assertEquals(courier.getDeliveredOrdersCounter(),10);
    }
    @Test
    public void updateOrderTest(){
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));


        int id = order.getId();
        myFoodoraSystem.addOrder(order);
        Assert.assertTrue(!myFoodoraSystem.findOrer(id).getDeliveryState());

        Order newOrder = myFoodoraSystem.findOrer(id);
        newOrder.setDeliveryStateAsFinished();

        myFoodoraSystem.updateOrder(newOrder);
        Assert.assertTrue(myFoodoraSystem.findOrer(id).getDeliveryState());
    }

    @Test
    public void delegateOrder2Courier() throws UserNotFoundException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        myFoodoraSystem.registerCourier("Zemin","Jianf","JZ","123456",new Coordinate(10,10),"123456");
        myFoodoraSystem.registerCourier("Jintao","Hu","HJ","123456",new Coordinate(20,20),"123456");

        Assert.assertTrue(!((Courier) myFoodoraSystem.getUser("JZ")).getNewOrderCondition());

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));

        myFoodoraSystem.delegateOrder2Courier(order);
        Assert.assertTrue(((Courier) myFoodoraSystem.getUser("JZ")).getNewOrderCondition());
    }

    @Test
    public void delegateOrder2Courier_NoCourierIsAvailable() throws UserNotFoundException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        myFoodoraSystem.registerCourier("Zemin","Jianf","JZ","123456",new Coordinate(10,10),"123456");
        myFoodoraSystem.registerCourier("Jintao","Hu","HJ","123456",new Coordinate(20,20),"123456");

        Courier courier = (Courier) myFoodoraSystem.getUser("JZ");
        courier.setState_OffDuty();
        myFoodoraSystem.updateUser(courier);
        courier = (Courier) myFoodoraSystem.getUser("HJ");
        courier.setState_OffDuty();
        myFoodoraSystem.updateUser(courier);

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));

        myFoodoraSystem.delegateOrder2Courier(order);
    }

    @Test
    public void setSpecialOffer(){
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        myFoodoraSystem.registerCustomer("Jintao","Hu","HJ","123456",new Coordinate(0,0),"*","*");

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));

        Item pizza = new Item("pizza", new BigDecimal(5), ItemCategory.MainDish, ItemType.Standard);

        Meal meal = new Meal("meal", restaurant, MealCategory.HalfMeals, MealType.Standard);
        meal.addItem(pizza);

        myFoodoraSystem.registerRestaurant("Pizzeria","pizzeria",new Coordinate(1,3),"123456");
        myFoodoraSystem.loginUser("pizzeria", "123456");
        myFoodoraSystem.createItem("pizza", new BigDecimal(5));
        myFoodoraSystem.createMeal("meal");
        myFoodoraSystem.addDish2Meal("pizza","meal");
        myFoodoraSystem.setSpecialOffer("meal");

        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.loginUser("HJ","123456");
    }

    @Test
    public void registerFidelityCardTest() throws UserNotFoundException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
        myFoodoraSystem.registerCustomer("Jintao","Hu","HJ","123456",new Coordinate(0,0),"*","*");
        myFoodoraSystem.loginUser("HJ","123456");

        Assert.assertTrue(((Customer) myFoodoraSystem.getUser("HJ")).getFidelityCard().getFidelityCardName().equalsIgnoreCase("BasicFidelityCard"));

        myFoodoraSystem.registerFidelityCard("LotteryFidelityCard");

        Assert.assertTrue(((Customer) myFoodoraSystem.getUser("HJ")).getFidelityCard().getFidelityCardName().equalsIgnoreCase("LotteryFidelityCard"));
    }

    @Test
    public void getUserTest(){
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();


    }
}