package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Meal  implements Serializable {

	private static final long serialVersionUID = 2530546545664573253L;

	private String name;
	private BigDecimal price;
	private MealCategory mealCategory;
	private	MealType mealType;
	private ArrayList<Item> items = new ArrayList<>();

	public Meal(String name, BigDecimal price, MealCategory mealCategory, MealType mealType) {
		this.name = name;
		this.price = price;
		this.mealCategory = mealCategory;
		this.mealType = mealType;
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

	@Override
	public String toString() {
		return "Meal{" +
				"name='" + name + '\'' +
				", price=" + price +
				", mealCategory=" + mealCategory +
				", mealType=" + mealType +
				'}';
	}
}
