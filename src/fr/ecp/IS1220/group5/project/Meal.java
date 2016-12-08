package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * <b>The class that represents a meal</b>
 *
 * It can be created by a <b>Restaurant</b> and chosen by a <b>Customer</b>.
 *
 * @see Restaurant
 * @see Customer
 */
public class Meal  implements Serializable {

	private static final long serialVersionUID = 2530546545664573253L;

	private String name;
	private BigDecimal price;
	private MealCategory mealCategory;
	private	MealType mealType;
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * The Restaurant that has created this meal.
	 */
	private Restaurant restaurant;
	/**
	 * true if this meal is the meal of the week, false otherwise.
	 */
	private boolean isMealOfTheWeek = false;

	public Meal(String name, Restaurant restaurant, MealCategory mealCategory, MealType mealType) {
		this.name = name;
		this.restaurant = restaurant;
		this.mealCategory = mealCategory;
		this.mealType = mealType;
	}

	/**
	 * Sets the value of the isMealOfTheWeek attribute:
	 * @param isMealOfTheWeek true if it is the meal of the week, false otherwise
	 */
	public void setMealOfTheWeek(boolean isMealOfTheWeek){
		this.isMealOfTheWeek = isMealOfTheWeek;
		this.updatePrice();
	}

	/**
	 * Updates the total price of the meal, by applying the discount_factor of the restaurant.
	 *	If this meal is the meal of the week, the discount factor is special_discout_factor
	 *
	 * @see Restaurant
	 */
	public void updatePrice(){
		BigDecimal price = new BigDecimal("0");

		for (Item item: items){
			price = price.add(item.getPrice());
		}

		if (isMealOfTheWeek){
			this.price = price.multiply((new BigDecimal("1")).subtract(restaurant.getSpecialDiscountFactor()));
		}else{
			this.price = price.multiply((new BigDecimal("1")).subtract(restaurant.getGenericDiscountFactor()));
		}


	}

	public void setPrice(BigDecimal price){
		this.price = price;
	}

	public Meal(String name, Restaurant restaurant){
		this.name = name;
		this.restaurant = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public MealCategory getMealCategory() {
		return mealCategory;
	}

	public void setMealCategory(MealCategory mealCategory) {
		this.mealCategory = mealCategory;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public void addItem(Item item){
		this.items.add(item);
		this.updatePrice();
	}

	public ArrayList<Item> getItems(){
		return items;
	}

	@Override
	public String toString() {
		return "Meal{" +
				"name='" + name + '\'' +
				", price=" + price +
				", items=" + items +
				", mealCategory=" + mealCategory +
				", mealType=" + mealType +
				'}';
	}
}
