package fr.ecp.IS1220.group5.project;

import java.io.Serializable;

public class Meal  implements Serializable {

	private static final long serialVersionUID = 2530546545664573253L;

	private String name;
	private Money price;
	private MealCategory mealCategory;
	private	MealType mealType;

	public Meal(String name, Money price, MealCategory mealCategory, MealType mealType) {
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

	public Money getPrice() {
		return price;
	}

	public void setPrice(Money price) {
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
