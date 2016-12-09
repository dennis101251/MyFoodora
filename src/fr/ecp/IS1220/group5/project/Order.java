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
	private Courier courier;

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

	private BigDecimal delivery_distance = new BigDecimal("0");

	/**
	 * the total price of the order, by applying the markup percentage, the fee and the discount (fidelity program)
	 */
	private BigDecimal total_price = new BigDecimal("0");

	public Order(Restaurant restaurant, Customer customer, BigDecimal delivery_cost_per_km, BigDecimal markup_percentage, BigDecimal service_fee) {
		this.restaurant = restaurant;
		this.customer = customer;
		this.delivery_cost_per_km = delivery_cost_per_km;
		this.markup_percentage = markup_percentage;
		this.service_fee = service_fee;
		computeDeliveryCost();
	}

	/**
	 * Computes the total price of the order, by applying the markup percentage, the fee and the discount (fidelity program)
	 */
	private void updateTotalPrice(){

		updateOrderPrice();

		total_price = order_price;
		System.out.println("Total: " + total_price);
		total_price = total_price.multiply(markup_percentage.add(new BigDecimal(1)));
		System.out.println("Total (with markup percentage): " + total_price);
		total_price = total_price.add(service_fee);
		System.out.println("Total (with fees): " + total_price);
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
		delivery_distance = delivery_cost;
		delivery_cost = delivery_cost.multiply(delivery_cost_per_km);
	}

	private void updateOrderPrice(){
		BigDecimal price = new BigDecimal("0");

		for (Item item : items){
			price = price.add(item.getPrice());
		}

		for (Meal meal : meals){
			price = price.add(meal.getPrice());
		}

		this.order_price = price;

	}

	public void addItem(Item item){
		this.items.add(item);
		updateTotalPrice();
	}

	public void addMeal(Meal meal){
		this.meals.add(meal);
		updateTotalPrice();
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
		return total_price;
	}
	public BigDecimal getOrder_price(){return order_price;}
	public BigDecimal getDelivery_cost(){ return delivery_cost;}
	public BigDecimal getDelivery_distance(){return delivery_distance;}

	public void setCourier(Courier courier){
		this.courier = courier;
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

	public void applyFidelityDiscount() {
		this.total_price = customer.getFidelityCard().compute_discounted_price(this.total_price);
	}
}
