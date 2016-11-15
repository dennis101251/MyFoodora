package fr.ecp.IS1220.group5.project;

import org.junit.Test;

/**
 * Created by Alexandre on 15/11/2016.
 */
public class RestaurantTest {

    @Test
    public void test() {

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", new Coordinate(5,2));



        Item item = new Item("pizza", new Money(7), ItemCategory.MainDish, ItemType.Standard);


        restaurant.addItem(item);

        restaurant.saveItems();
    }

}
