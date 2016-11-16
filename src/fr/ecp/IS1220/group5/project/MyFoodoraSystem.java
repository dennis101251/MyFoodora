package fr.ecp.IS1220.group5.project;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MyFoodoraSystem {
	
	private Userlist users;
	private ArrayList<Order> orders;
	private double service_fee;
	private double markup_percentage;
	private double delivery_cost;
	
	
	public MyFoodoraSystem() {
//		retrieveUsers();
//		retrieveOrders();
//		retrieveFinancial();
		System.out.println("init successfully");
	}

	public void registerUser(User user){
		users.addUser(user);
	}

	public void retrieveOrders(){
		try {
			FileInputStream fileIn = new FileInputStream("tmp/orders.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			orders = (ArrayList<Order>) in.readObject();

			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void retrieveUsers(){
		try {
			FileInputStream fileIn = new FileInputStream("tmp/users.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			users = (Userlist) in.readObject();
			
			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void retrieveFinancial(){
		
		Financial financial = null;
		
		try {
			FileInputStream fileIn = new FileInputStream("tmp/financial.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			financial = (Financial) in.readObject();
			
			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		service_fee = financial.service_fee;
		markup_percentage = financial.markup_percentage;
		delivery_cost = financial.delivery_cost;
		
	}

	public static void main(String[] args) {
		MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

	}

}
