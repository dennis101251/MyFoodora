package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.math.BigDecimal;
import java.util.GregorianCalendar;

/**
 * Created by dennis101251 on 2016/12/11.
 */
public class OrderTest {
    @Test
    public void delateOrders() throws Exception {
        Userlist.delateUserFile();
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));

        myFoodoraSystem.addOrder(order);
        myFoodoraSystem.saveOrders();

        File file = new File("tmp/orders.ser");
        Assert.assertTrue(file.exists());

        Order.delateOrders();
        Assert.assertTrue(!file.exists());
    }

    @Test
    public void getTotal_price() throws Exception {

    }

    @Test
    public void getOrder_price() throws Exception {

    }

    @Test
    public void getDelivery_cost() throws Exception {

    }

    @Test
    public void getDelivery_distance() throws Exception {

    }

    @Test
    public void showOrder() throws Exception {

    }

    @Test
    public void isCourierHasBeenNotified() throws Exception {

    }

    @Test
    public void applyFidelityDiscount() throws Exception {

    }

    @Test
    public void dateTest(){
        Userlist.delateUserFile();
        Order.delateOrders();
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        Restaurant restaurant = new Restaurant("KFC","kfc","123456",new Coordinate(0,0));
        Customer customer = new Customer("Zexi","DENG","dennis",new Coordinate(40,0), "123456");
        Order order = new Order(restaurant,customer, BigDecimal.valueOf(1),BigDecimal.valueOf(1),BigDecimal.valueOf(1));
        System.out.println(order.getDate().getTime());
        System.out.println(new GregorianCalendar(2017,0,1).getTime());
        if (order.getDate().getTime().before(new GregorianCalendar(2020,1,1).getTime()) && order.getDate().getTime().after(new GregorianCalendar(2017,1,1).getTime())){
            System.out.println("OK");
        }
        else {
            System.out.println("False");
        }

    }
}