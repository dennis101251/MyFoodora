package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.util.Sort;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by dennis101251 on 2016/12/9.
 */
public class MyFoodoraSystemTest {

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
    public void loginUserTest(){
//        Userlist.delateUserFile();

        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        myFoodoraSystem.registerCourier("Zemin","Jianf","JZ","123456",new Coordinate(10,10),"123456");
        myFoodoraSystem.loginUser("JZ","123456");

        Assert.assertTrue(myFoodoraSystem.getCurrentUser().getName().equalsIgnoreCase("Zemin"));

        myFoodoraSystem.disconnectUser();

        myFoodoraSystem.loginUser("JZ","*");
        Assert.assertTrue(myFoodoraSystem.getCurrentUser() == null);
    }

    @Test
    public void scenarioOfRestaurant(){
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

        myFoodoraSystem.showMeal("McDonalds", "BestOf");
    }

    @Test
    public void scenarioOfCustomer() throws UserNotFoundException {
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
        myFoodoraSystem.addMeal2Order("BestOf", 2);
        myFoodoraSystem.showOrder();
        myFoodoraSystem.endOrder();
        myFoodoraSystem.showHistoryOfOrder_Customer();
        myFoodoraSystem.showFidelityCard();

        myFoodoraSystem.checkInfoBoard();
    }

    @Test
    public void scenarioOfManager() throws UserNotFoundException {
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
        myFoodoraSystem.chooseRestaurant("McDonalds");
        myFoodoraSystem.showMenu();
        myFoodoraSystem.addItem2Order("FrenchFries",1);
        myFoodoraSystem.addMeal2Order("BestOf", 2);
        myFoodoraSystem.endOrder();

        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.registerManager("Zexi", "DENG", "dennis", "101251");
        myFoodoraSystem.loginUser("dennis", "101251");
        myFoodoraSystem.showHistoryOfOrder_System();
        myFoodoraSystem.totalIncome();
        myFoodoraSystem.totalProfit();
        myFoodoraSystem.totalDeliveryCost();

        myFoodoraSystem.setTarget_profit(20.0);
        myFoodoraSystem.determineDelivery_Cost();
        myFoodoraSystem.determineMarkup_Percentage();
        myFoodoraSystem.determineService_fee();
    }

    @Test
    public void scenarioOfCourier() throws UserNotFoundException {
        Order.delateOrders();
        Userlist.delateUserFile();

        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

        myFoodoraSystem.registerCourier("Zemin","Jianf","JZ","123456",new Coordinate(10,10),"123456");
        myFoodoraSystem.registerCourier("Jintao","Hu","HJ","123456",new Coordinate(20,20),"123456");

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
        myFoodoraSystem.chooseRestaurant("McDonalds");
        myFoodoraSystem.showMenu();
        myFoodoraSystem.addItem2Order("FrenchFries",1);
        myFoodoraSystem.addMeal2Order("BestOf", 2);
        myFoodoraSystem.endOrder();

        //default: we take the fastDelivery policy
        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.loginUser("JZ","123456");
        myFoodoraSystem.disconnectUser();
        myFoodoraSystem.loginUser("HJ","123456");

        Assert.assertTrue(((Courier) myFoodoraSystem.getUser("JZ")).getNewOrderCondition());
        Assert.assertTrue(!((Courier) myFoodoraSystem.getUser("HJ")).getNewOrderCondition());
    }

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
        Userlist.delateUserFile();
        Order.delateOrders();

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
        Userlist.delateUserFile();
        Order.delateOrders();

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
    public void delegateOrder2Courier() throws UserNotFoundException {
        Userlist.delateUserFile();
        Order.delateOrders();

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

        Item pizza = null;
        try {
            pizza = new Item("pizza", new BigDecimal(5), ItemCategory.MainDish, FoodType.Standard);
        } catch (EmptyNameException e) {
            e.printStackTrace();
        }

        Meal meal = null;
        try {
            meal = new Meal("meal", restaurant, MealCategory.HalfMeals, FoodType.Standard);
        } catch (EmptyNameException e) {
            e.printStackTrace();
        }
        try {
            meal.addItem(pizza);
        } catch (IncompatibleFoodTypeException e) {
            e.printStackTrace();
        } catch (TooManyItemsException e) {
            e.printStackTrace();
        }

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

    @Test
    public void getAllMealsTest(){
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

        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        Assert.assertTrue(myFoodoraSystem.getAllMeals(orders).size() == 4);
        System.out.println(orders.toString());
    }

    @Test
    public void getQuantityOfMealTest(){
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
        } catch (EmptyNameException e) {
            e.printStackTrace();
        }



        ArrayList<Order> orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);

        ArrayList<Sort> sortMeals = myFoodoraSystem.getQuantityOfFoods(myFoodoraSystem.getAllMeals(orders));
        ArrayList<Sort> sortItems = myFoodoraSystem.getQuantityOfFoods(myFoodoraSystem.getAllItems(orders));

        for (Sort sortmeal: sortMeals
             ) {
            System.out.println((sortmeal.getType()).getName() + "||" + sortmeal.getQuantity());
        }

        System.out.println("=============");

        for (Sort sortmeal: sortItems
                ) {
            System.out.println((sortmeal.getType()).getName() + "||" + sortmeal.getQuantity());
        }
        Assert.assertTrue(sortMeals.get(0).getQuantity() == 2);
    }
}