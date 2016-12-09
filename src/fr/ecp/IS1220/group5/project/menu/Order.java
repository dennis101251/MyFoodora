package fr.ecp.IS1220.group5.project.menu;

import com.sun.xml.internal.bind.v2.model.core.ID;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.util.IDGenerator;
import fr.ecp.IS1220.group5.project.util.Money;

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
	/**
	 * the list of items contained in the order.
	 */
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * the list of meals contained in the order.
	 */
	private ArrayList<Meal> meals = new ArrayList<>();
	/**
	 * the restaurant from which the meals and items are taken.
	 */
	private Restaurant restaurant;
	/**
	 * the customer that is ordering.
	 */
	private Customer customer;
	/**
	 * the courier who will to deliver this order.
	 */
	private Courier courier;
	/**
	 * the unique ID of this order.
	 */
	private int id;

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

	/**
	 * True if this order has been delegated
	 * False if this order is still waiting to be delegated
	 */
	private Boolean deliveryState = false;

	/**
	 * Constructor of the order.
	 * @param restaurant the restaurant from which the meals and items are taken.
	 * @param customer the customer who is ordering.
	 * @param delivery_cost_per_km the delivery cost per kilometer.
	 * @param markup_percentage the markup percentage of MyFoodora's system.
	 * @param service_fee the service fee of MyFoodora's system.
	 */
	public Order(Restaurant restaurant, Customer customer, BigDecimal delivery_cost_per_km, BigDecimal markup_percentage, BigDecimal service_fee) {
		this.restaurant = restaurant;
		this.customer = customer;
		this.delivery_cost_per_km = delivery_cost_per_km;
		this.markup_percentage = markup_percentage;
		this.service_fee = service_fee;
		IDGenerator idGenerator = IDGenerator.getInstance();
		this.id = idGenerator.getNextID();
		computeDeliveryCost();
	}

	/**
	 * Returns the restaurant from which the meals and items are taken.
	 * @return the restaurant from which the meals and items are taken.
	 */
	public Restaurant getRestaurant() {
		return restaurant;
	}

	/**
	 * Return the total price of the order (includign markup percentage and fees)
	 * @return the total price of the order (includign markup percentage and fees)
	 */
	public BigDecimal getTotal_price() {
		return total_price;
	}

	/**
	 * Return the price as the sum of the meals' and items' prices (without markup percentage and fees)
	 * @return the price as the sum of the meals' and items' prices (without markup percentage and fees)
	 */
	public BigDecimal getOrder_price(){return order_price;}

	/**
	 * Returns the delivery cost of this order.
	 * @return the delivery cost of this order.
	 */
	public BigDecimal getDelivery_cost(){ return delivery_cost;}

	/**
	 * Return the distance the courier has to travel to deliver this order.
	 * @return the delivery distance.
	 */
	public BigDecimal getDelivery_distance(){return delivery_distance;}

	/**
	 * Returns the delivery state of this order.
	 * @return the delivery state of this order.
	 */
	public Boolean getDeliveryState(){return deliveryState;}

	/**
	 * when a courier has accepted this order, it should be set as finished
	 */
	public void setDeliveryStateAsFinished(){ this.deliveryState = true;}

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

	/**
	 * Displays a user friendly representation of this order.
	 */
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

	/**
	 * Computes the delivery cost of this order.
	 * Delivery_cost = delivery_distance * delivery_cost_per_km
	 */
	private void computeDeliveryCost(){
		delivery_cost = BigDecimal.valueOf(Coordinate.getDistance(restaurant.getAddress(),customer.getAddress()));
		delivery_distance = delivery_cost;
		delivery_cost = delivery_cost.multiply(delivery_cost_per_km);
	}

	/**
	 * Updates the price of the order (without markup percentages or fees).
	 */
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

	/**
	 * Adds an item to the order
	 * @param item the item to be added.
	 *
	 * @see Item
	 */
	public void addItem(Item item){
		this.items.add(item);
		updateTotalPrice();
	}

	/**
	 * Adds a meal to the order.
	 * @param meal the meal to be added.
	 *
	 * @see Meal
	 */
	public void addMeal(Meal meal){
		this.meals.add(meal);
		updateTotalPrice();
	}

	/**
	 * Checks whether the order is empty (true) or not (false)
	 * @return true if the order is empty, false otherwise.
	 */
	public boolean isEmpty(){
		return meals.isEmpty() && items.isEmpty();
	}

	/**
	 * Returns the list of items containted in this order
	 * @return the list of items containted in this order
	 *
	 * @see Item
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * Returns the list of meals contained in this order.
	 * @return the list of meals contained in this order.
	 *
	 * @see Meal
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}

	/**
	 * get the unique id of each order has been created
	 * @return id
	 */
	public int getId(){return id;}

	/**
	 * when a courier accept this order, he should connect with this order
	 * @param courier
	 */
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

	/**
	 * applies the fidelity card discount and adds points to the customer.
	 */
	public void applyFidelityDiscount() {
		this.total_price = customer.getFidelityCard().compute_discounted_price(this.total_price);
	}
}
