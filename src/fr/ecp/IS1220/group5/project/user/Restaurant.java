package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.util.Coordinate;

import java.math.BigDecimal;
import java.util.ArrayList;
/**
 * <b>The class that represents a Restaurant</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Restaurant extends User {
	/**
	 * The address of the restaurant.
	 */
	private Coordinate address;
	/**
	 * The available 'à la carte' items that this restaurants cooks.
	 */
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * The available meals from this restaurant.
	 */
	private ArrayList<Meal> meals = new ArrayList<>();
	/**
	 * The generic discount factor that is applied to a meal.
	 */
	private BigDecimal genericDiscountFactor = new BigDecimal("0.05");
	/**
	 * The special discount factor that is applied to the meal of the week.
	 */
	private BigDecimal specialDiscountFactor = new BigDecimal("0.1");
	/**
	 * The history of orders.
	 */
	private ArrayList<Order> orders = new ArrayList<>();

	/**
	 * The constructor of the Restaurant.
	 * @param name the name of the Restaurant.
	 * @param username the username of the restaurant.
	 * @param password the hashed password of the restaurant.
	 * @param address the address of the restaurant.
	 */
	public Restaurant(String name, String username, String password, Coordinate address) {
		super(name, username, password);
		this.address = address;
//		retrieveItems();
//		retrieveMeals();
	}

	/**
	 *
	 */
	@Override
	public String getType(){
		return "Restaurant";
	}

	public void updateMeal(Meal meal){
		String mealName = meal.getName();
		meals.remove(getMeal(mealName));
		meals.add(meal);
	}

	/**
	 * Returns the generic discount factor of this restaurant.
	 * @return the generic discount factor.
	 */
	public BigDecimal getGenericDiscountFactor() {
		return genericDiscountFactor;
	}

	/**
	 * Sets the generic discount factor of this restaurant.
	 * @param genericDiscountFactor the new generic discount factor.
	 */
	public void setGenericDiscountFactor(BigDecimal genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
	}

	/**
	 * Returns the special discount factor of this restaurant.
	 * @return the special discount factor
	 */
	public BigDecimal getSpecialDiscountFactor() {
		return specialDiscountFactor;
	}

	/**
	 * Sets the special discount factor of this restaurant.
	 * @param specialDiscountFactor the new special discount factor
	 */
	public void setSpecialDiscountFactor(BigDecimal specialDiscountFactor) {
		this.specialDiscountFactor = specialDiscountFactor;
	}

	/**
	 * The available items that this restaurant cooks.
	 * @return the list of items of this restaurant.
	 */
	public ArrayList<Item> getItems() {
		return items;
	}

	/**
	 * Adds an item to the list of items.
	 * @param item the item to add to the menu.
	 */
	public void addItem(Item item) {
		items.add(item);
	}

	/**
	 * Removes a given item from the list of items.
	 * @param item the item to be removed.
	 */
	public void removeItem(Item item){
		items.remove(item);
	}

	/**
	 * Returns the list of meals of this restaurant.
	 * @return the list of meals.
	 */
	public ArrayList<Meal> getMeals() {
		return meals;
	}

	/**
	 * Adds a meal to the list of meals.
	 * @param meal the new meal to be added to the list.
	 */
	public void addMeal(Meal meal) {
		meals.add(meal);
	}

	/**
	 * Removes a given meal from the list of meals.
	 * @param meal the meal to be removed from the list.
	 */
	public void removeMeal(Meal meal){
		meals.remove(meal);
	}

	/**
	 * Adds an order to the history of orders.
	 * @param order the new order to add to the history.
	 */
	public void addOrder(Order order){
		this.orders.add(order);
	}

	/**
	 * Returns the history of orders.
	 * @return the list of orders from this restaurant.
	 */
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

	/**
	 * Returns the Meal object that has the same name as the one that is searched.
	 * @param mealName the name of the meal that is searched.
	 * @return the meal, as a Meal object.
	 */
    public Meal getMeal(String mealName) {

		for (Meal meal : this.meals){
			if (meal.getName().equalsIgnoreCase(mealName)){
				return meal;
			}
		}

		return null;
	}

	/**
	 * Returns the address of this restaurant.
	 * @return the address, as a Coordinate object.
	 */
	public Coordinate getAddress(){
		return address;
	}
}
