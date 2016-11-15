package fr.ecp.IS1220.group5.project;

/**
 * Created by Alexandre on 15/11/2016.
 */

public class Test {

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", new Coordinate(5,2));

        for (Item item: restaurant.getItems()){
            System.out.println(item);
        }

        //Item item = new Item("pizza", new Money(7), ItemCategory.MainDish, ItemType.Standard);

        //restaurant.addItem(item);

        //restaurant.saveItems();
    }

}
