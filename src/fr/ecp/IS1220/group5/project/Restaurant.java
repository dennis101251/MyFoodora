package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.util.ArrayList;

public class Restaurant extends User {
	
	private Coordinate address;
	private ArrayList<Item> items = new ArrayList<>();
	private ArrayList<Meal> meals = new ArrayList<>();
	private double genericDiscountFactor = 0.05;
	private double specialDiscountFactor = 0.1;

	public Restaurant(String name, String username, String password, Coordinate address) {
		super(name, username, password);
		this.address = address;
//		retrieveItems();
//		retrieveMeals();
	}

	public void retrieveItems() {
		try {
			FileInputStream fileIn = new FileInputStream( SavingPath.string + "tmp/" + this.id + "items.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			this.items = (ArrayList<Item>) in.readObject();

			in.close();
			fileIn.close();

		} catch (IOException e) {
			//New file
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveItems(){
		try {
			FileOutputStream fileOut = new FileOutputStream(  SavingPath.string + "tmp/" + this.id + "items.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(this.items);

			out.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void retrieveMeals() {
		try {
			FileInputStream fileIn = new FileInputStream( SavingPath.string + "tmp/" + this.id + "meals.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			this.meals = (ArrayList<Meal>) in.readObject();


			in.close();
			fileIn.close();

		} catch (IOException e) {
			//New file
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void saveMeals(){
		try {
			FileOutputStream fileOut = new FileOutputStream( SavingPath.string + "tmp/" + this.id + "meals.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(meals);

			out.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public double getGenericDiscountFactor() {
		return genericDiscountFactor;
	}

	public void setGenericDiscountFactor(double genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
	}

	public double getSpecialDiscountFactor() {
		return specialDiscountFactor;
	}

	public void setSpecialDiscountFactor(double specialDiscountFactor) {
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

	@Override
	public String toString() {
		return "Restaurant [address=" + address + ", name=" + name + ", username=" + username + ", id=" + id + "]";
	}	
	
}
