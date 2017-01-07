package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by dennis101251 on 2016/12/11.
 */
public class RestaurantTest {
    @Test
    public void updateMeal() throws Exception {

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));

        Item pizza = new Item("pizza", new BigDecimal(5), ItemCategory.MainDish, FoodType.Standard);

        Meal meal = new Meal("meal", restaurant, MealCategory.HalfMeals, FoodType.Standard);
        meal.addItem(pizza);
        restaurant.addMeal(meal);

        Assert.assertTrue(restaurant.getMeal("meal").getItems().size() == 1);

        Item jiaozi = new Item("jiaozi", new BigDecimal(2), ItemCategory.MainDish, FoodType.Standard);

        meal.addItem(jiaozi);
        restaurant.updateMeal(meal);

        Assert.assertTrue(restaurant.getMeal("meal").getItems().size() == 2);

    }

}