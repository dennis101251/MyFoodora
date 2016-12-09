package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
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

}