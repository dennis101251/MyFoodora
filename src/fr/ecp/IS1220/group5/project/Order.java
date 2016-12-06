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
	private BigDecimal order_price = new BigDecimal("0");
	private BigDecimal actual_price = new BigDecimal("0");
	private BigDecimal delivery_cost = new BigDecimal("0");
	private BigDecimal dilivery_cost_price = new BigDecimal("1");
	private BigDecimal markup_percentage = new BigDecimal("0");
	private BigDecimal service_fee = new BigDecimal("0");
	private BigDecimal total_price = new BigDecimal("0");

	public Order(Restaurant restaurant, Customer customer, Double dilivery_cost_price, Double markup_percentage, Double service_fee) {
		this.restaurant = restaurant;
		this.customer = customer;
		this.dilivery_cost_price = BigDecimal.valueOf(dilivery_cost_price);
		this.markup_percentage = BigDecimal.valueOf(markup_percentage);
		this.service_fee = BigDecimal.valueOf(service_fee);
		computeDeliveryCost();
	}

	private void computeTotalPrice(){
		total_price = order_price;
		total_price = total_price.multiply(markup_percentage.add(BigDecimal.valueOf(1)));
		total_price = total_price.add(service_fee);
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
		System.out.println(">>order price: " + Money.display(order_price));
		System.out.println(">>total price: " + Money.display(total_price));
	}

	private void computeDeliveryCost(){
		delivery_cost = delivery_cost.add(BigDecimal.valueOf(Coordinate.getDistance(restaurant.getAddress(),customer.getAddress())));
		delivery_cost = delivery_cost.multiply(dilivery_cost_price);
	}

	private BigDecimal compute_order_price(){
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
		order_price = this.compute_order_price();
		computeTotalPrice();
	}

	public void addMeal(Meal meal){
		this.meals.add(meal);
		order_price = this.compute_order_price();
		computeTotalPrice();
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
		return order_price;
	}

	@Override
	public String toString() {
		return "Order{" +
				"items=" + items +
				", meals=" + meals +
				", restaurant=" + restaurant +
				", total_price=" + order_price +
				'}';
	}
}
