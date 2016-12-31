package fr.ecp.IS1220.group5.project.menu;

import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;

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
public class Meal  implements Serializable, Food {

	private static final long serialVersionUID = 2530546545664573253L;
	/**
	 * the name of this meal.
	 */
	private String name;
	/**
	 * the price of this meal.
	 */
	private BigDecimal price = new BigDecimal(0);
	/**
	 * The meal category of this meal.
	 *
	 * @see MealCategory
	 */
	private MealCategory mealCategory;
	/**
	 * The meal type of this meal.
	 *
	 * @see FoodType
	 */
	private FoodType foodType;
	/**
	 * The items contained in this meal.
	 */
	private ArrayList<Item> items = new ArrayList<>();
	/**
	 * The Restaurant that has created this meal.
	 */
	private Restaurant restaurant;
	/**
	 * true if this meal is the meal of the week, false otherwise.
	 */
	private boolean isMealOfTheWeek = false;

	private int nbItems = 0;
		private int nbStarters = 0;
		private int nbMainDishes = 0;
		private int nbDesserts = 0;

	/**
	 * The constructor of this meal.
	 * @param name the name of this meal.
	 * @param restaurant the restaurant that has created this meal.
	 * @param mealCategory the meal category.
	 * @param foodType the meal type.
	 */
	public Meal(String name, Restaurant restaurant, MealCategory mealCategory, FoodType foodType) throws EmptyNameException {

		if (name.equals("")) {
			throw new EmptyNameException();
		} else {
			this.name = name;
		}

		this.restaurant = restaurant;
		this.mealCategory = mealCategory;
		this.foodType = foodType;
	}

	/**
	 * Sets the value of the isMealOfTheWeek attribute:
	 * @param isMealOfTheWeek true if it is the meal of the week, false otherwise
	 */
	public void setMealOfTheWeek(boolean isMealOfTheWeek){
		this.isMealOfTheWeek = isMealOfTheWeek;
		this.updatePrice();
	}

	public boolean isMealOfTheWeek(){
		return isMealOfTheWeek;
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

	/**
	 * Another constructor of this meal (only name and restaurant)
	 * @param name the name of this meal.
	 * @param restaurant the restaurant that has created this meal.
	 */
	public Meal(String name, Restaurant restaurant){
		this.name = name;
		this.restaurant = restaurant;
	}

	/**
	 * Returns the name of this meal.
	 * @return the name of this meal.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this meal.
	 * @param name the name of this meal.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the price of this meal.
	 * @return the price of this meal.
	 */
	@Override
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Returns the meal category of this meal.
	 * @return the meal of this meal.
	 */
	public MealCategory getMealCategory() {
		return mealCategory;
	}

	/**
	 * Sets the meal category.
	 * @param mealCategory the new meal category.
	 */
	public void setMealCategory(MealCategory mealCategory) {
		this.mealCategory = mealCategory;
	}

	/**
	 * Returns the meal type of this meal.
	 * @return the meal type of this meal.
	 */
	public FoodType getFoodType() {
		return foodType;
	}

	/**
	 * Sets the meal type
	 * @param foodType the new meal type.
	 */
	public void setFoodType(FoodType foodType) {
		this.foodType = foodType;
	}

	/**
	 * Adds an item to this meal.
	 * @param item the item to be added to this meal.
	 */
	public void addItem(Item item) throws IncompatibleFoodTypeException, TooManyItemsException {

		if (item.getFoodType() != this.foodType) {

			throw new IncompatibleFoodTypeException();

		} else {

			if ((nbItems == 2 && this.mealCategory == MealCategory.HalfMeals) || (nbItems == 3 && this.mealCategory == MealCategory.FullMeals)){
				throw new TooManyItemsException();
			} else if ((nbStarters == 1 && item.getItemCategory() == ItemCategory.Starter) || (nbMainDishes == 1 && item.getItemCategory() == ItemCategory.MainDish) || (nbDesserts == 1 && item.getItemCategory() == ItemCategory.Dessert)){
				throw new TooManyItemsException();
			} else {

				nbItems++;
				switch (item.getItemCategory()){
					case Starter:
						nbStarters++;
						break;
					case MainDish:
						nbMainDishes++;
						break;
					case Dessert:
						nbDesserts++;
						break;
				}

				this.items.add(item);
				this.updatePrice();

			}

		}


	}



	/**
	 * Returns the list of items contained in this meal.
	 * @return the list of items
	 *
	 * @see Item
	 */
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
				", foodType=" + foodType +
				'}';
	}
}
