package fr.ecp.IS1220.group5.project;

import java.util.ArrayList;

public class Restaurant extends User {
	
	private Coordinate address;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	
	public Restaurant(String name, String username, Coordinate address) {
		super(name, username);
		this.address = address;
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

	@Override
	public String toString() {
		return "Restaurant [address=" + address + ", name=" + name + ", username=" + username + ", id=" + id + "]";
	}	
	
}
