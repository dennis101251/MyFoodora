package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Order implements Serializable{

	private static final long serialVersionUID = -4389016276908172461L;

	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	private Restaurant restaurant;
	private Money total_price = new Money(0);

	public Order(Restaurant restaurant) {

		this.restaurant = restaurant;

	}

	private Money compute_total_price(){
		Money price = new Money("0");

		for (Item item : items){

			price = price.add(item.getPrice());

		}


		for (Meal meal : meals){

			price = price.add(meal.getPrice());

		}

		return price;

	}

	public void addItem(Item item){
		this.items.add(item);
		this.compute_total_price();
	}

	public void addMeal(Meal meal){
		this.meals.add(meal);
		this.compute_total_price();
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public Money getTotal_price() {
		return total_price;
	}

	@Override
	public String toString() {
		return "Order{" +
				"items=" + items +
				", meals=" + meals +
				", restaurant=" + restaurant +
				", total_price=" + total_price +
				'}';
	}
}
