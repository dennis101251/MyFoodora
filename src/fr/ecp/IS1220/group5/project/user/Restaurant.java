package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.menu.Order;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * <b>The class that represents a Restaurant</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Restaurant extends User {

	private Coordinate address;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	private BigDecimal genericDiscountFactor = new BigDecimal("0.05");
	private BigDecimal specialDiscountFactor = new BigDecimal("0.1");
	private ArrayList<Order> orders = new ArrayList<>();

	public Restaurant(String name, String username, String password, Coordinate address) {
		super(name, username, password);
		this.address = address;
//		retrieveItems();
//		retrieveMeals();
	}

	public BigDecimal getGenericDiscountFactor() {
		return genericDiscountFactor;
	}

	public void setGenericDiscountFactor(BigDecimal genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
	}

	public BigDecimal getSpecialDiscountFactor() {
		return specialDiscountFactor;
	}

	public void setSpecialDiscountFactor(BigDecimal specialDiscountFactor) {
		this.specialDiscountFactor = specialDiscountFactor;
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void addItem(Item item) {
		items.add(item);
	}
	
	public void removeItem(Item item){
		items.remove(item);
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}

	public void addMeal(Meal meal) {
		meals.add(meal);
	}
	
	public void removeMeal(Meal meal){
		meals.remove(meal);
	}

	public void addOrder(Order order){
		this.orders.add(order);
	}

	public ArrayList<Order> getOrders(){
		return orders;
	}

	public BigDecimal getIncome(){
		BigDecimal sum = new BigDecimal("0");
		for (Order order: orders
			 ) {
			sum = sum.add(order.getOrder_price());
		}
		return sum;
	}

	@Override
	public String toString() {
		return "Restaurant [address=" + address + ", name=" + name + ", username=" + username + ", id=" + id + "]";
	}

    public Item getItem(String itemName) {

		for (Item item : this.items){
			if (item.getName().equalsIgnoreCase(itemName)){
				return item;
			}
		}

		return null;
    }

    public Meal getMeal(String mealName) {

		for (Meal meal : this.meals){
			if (meal.getName().equalsIgnoreCase(mealName)){
				return meal;
			}
		}

		return null;
	}

	public Coordinate getAddress(){
		return address;
	}
}
