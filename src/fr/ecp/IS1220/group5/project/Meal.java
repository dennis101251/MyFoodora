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

	public Meal(String name, MealCategory mealCategory, MealType mealType) {
		this.name = name;
		this.mealCategory = mealCategory;
		this.mealType = mealType;
	}

	public Meal(String name){
		this.name = name;
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

	public void setPrice(BigDecimal price) {
		this.price = price;
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
