package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.ItemCategory;
import fr.ecp.IS1220.group5.project.menu.ItemType;
import fr.ecp.IS1220.group5.project.menu.Order;
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

}