package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <b>The class which represents a customer's cart</b>
 * It is created by a <b>Customer</b>, who can add <b>Items</b> and <b>Meals</b>.
 *
 * @version 2.0
 *
 * @see Customer
 * @see Item
 * @see Meal
 */
public class Order implements Serializable{

	private static final long serialVersionUID = -4389016276908172461L;

	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	private Restaurant restaurant;
	private Customer customer;

	/**
	 *	this price is the sum of the items' and meals' prices, without any fee, markup percentage or discount.
	 */
	private BigDecimal order_price = new BigDecimal("0");

	/**
	 * the cost of delivering the order to the customer.
	 */
	private BigDecimal delivery_cost = new BigDecimal("0");

	/**
	 * the delivery cost per unit distance (e.g. 1 km)
	 */
	private BigDecimal delivery_cost_per_km = new BigDecimal("1");

	/**
	 * the markup percentage.
	 */
	private BigDecimal markup_percentage = new BigDecimal("0");

	/**
	 *
	 */
	private BigDecimal service_fee = new BigDecimal("0");

	/**
	 * the total price of the order, by applying the markup percentage, the fee and the discount (fidelity program)
	 */
	private BigDecimal total_price = new BigDecimal("0");

	public Order(Restaurant restaurant, Customer customer, Double delivery_cost_price, Double markup_percentage, Double service_fee) {
		this.restaurant = restaurant;
		this.customer = customer;
		this.delivery_cost_per_km = BigDecimal.valueOf(delivery_cost_price);
		this.markup_percentage = BigDecimal.valueOf(markup_percentage);
		this.service_fee = BigDecimal.valueOf(service_fee);
		computeDeliveryCost();
	}

	/**
	 * Computes the total price of the order, by applying the markup percentage, the fee and the discount (fidelity program)
	 */
	private void computeTotalPrice(){
		total_price = order_price;
		total_price = total_price.multiply(markup_percentage.add(BigDecimal.valueOf(1)));
		total_price = total_price.add(service_fee);
		total_price = customer.getFidelityCard().compute_discounted_price(total_price);
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
		delivery_cost = BigDecimal.valueOf(Coordinate.getDistance(restaurant.getAddress(),customer.getAddress()));
		delivery_cost = delivery_cost.multiply(delivery_cost_per_km);
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
	public BigDecimal getTotal_price() {
		return order_price;
	}
	public BigDecimal getDelivery_cost(){ return delivery_cost;}


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
