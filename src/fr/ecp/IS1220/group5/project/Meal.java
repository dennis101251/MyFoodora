package fr.ecp.IS1220.group5.project;

import java.io.Serializable;

public class Meal  implements Serializable {

	private static final long serialVersionUID = 2530546545664573253L;

	private String name;
	private Money price;
	private MealCategory mealCategory;
	private	MealType mealType;
}
