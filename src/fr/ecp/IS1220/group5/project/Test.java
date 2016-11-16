package fr.ecp.IS1220.group5.project;

/**
 * Created by Alexandre on 15/11/2016.
 */

public class Test {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", new Coordinate(5,2));
//
//
//    Item pizza = new Item("pizza", new Money(7), ItemCategory.MainDish, ItemType.Standard);
//
//    restaurant.addItem(pizza);
//
//    restaurant.saveItems();
//
//    //System.out.println(restaurant.getId());
//
//    restaurant.getItems().forEach(System.out::println);
//
//    Item item = restaurant.getItems().get(0);
//
//
       Customer customer = new Customer("Alexandre", "alexandre", "alex", new Coordinate(5,2), "alexandre.carlier01@gmail.com", "+33602246329");
//
//        Order order = new Order(restaurant);
//        order.addItem(item);
//        customer.placeOrder(order);

        for (Order order : customer.retrieveOrders()){
            System.out.println(order);
        }

    }

}
