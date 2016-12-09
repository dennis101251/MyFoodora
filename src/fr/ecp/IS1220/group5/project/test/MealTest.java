package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by Alexandre on 08/12/2016.
 */
public class MealTest {
    @Test
    public void updatePrice() throws Exception {

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));

        Item pizza = new Item("pizza", new BigDecimal(5), ItemCategory.MainDish, ItemType.Standard);

        Meal meal = new Meal("meal", restaurant, MealCategory.HalfMeals, MealType.Standard);
        meal.addItem(pizza);

        assertTrue(meal.getPrice().equals(new BigDecimal("4.75")));

        meal.setMealOfTheWeek(true);
        assertTrue(meal.getPrice().equals(new BigDecimal("4.5")));
    }

}