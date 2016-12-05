package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Order implements Serializable{

	private static final long serialVersionUID = -4389016276908172461L;

	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	private Restaurant restaurant;
	private Customer customer;
	private BigDecimal total_price = new BigDecimal("0");
	private BigDecimal actual_price = new BigDecimal("0");

	public Order(Restaurant restaurant, Customer customer) {

		this.restaurant = restaurant;
		this.customer = customer;

	}

	public void applyFidelityCard(BigDecimal actual_price){
		this.actual_price = actual_price;
	}

	public void showOrder(){
		System.out.println("Customer: " + customer.getName() + "||Restaurant: " + restaurant.getName());
		for (Item item: items
			 ) {
			System.out.println(item.getName() + " " + Money.display(item.getPrice()));
		}
		for (Meal meal: meals){
			System.out.println(meal.getName() + " " + Money.display(meal.getPrice()));
		}
	}

	private BigDecimal compute_total_price(){
		BigDecimal price = new BigDecimal("0");

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
		total_price = this.compute_total_price();
	}

	public void addMeal(Meal meal){
		this.meals.add(meal);
		total_price = this.compute_total_price();
	}

	public boolean isEmpty(){
		return meals.isEmpty() && items.isEmpty();
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
	public BigDecimal getActual_price() {
		return actual_price;
	}
	public BigDecimal getTotal_price() {
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
