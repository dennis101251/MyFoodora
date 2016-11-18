package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer extends User{
	
	private String surname;
	private Coordinate address;
	private String email;
	private String phoneNumber;
	private boolean isNotified = false;
	private String contactType;
	private FidelityCard fidelityCard = new BasicFidelityCard();
//
//	public Customer(String name, String surname, String username) {
//		super(name, username);
//		this.surname = surname;
//	}

	public void setNotified_On(){
		this.isNotified = true;
	}
	public void setNotified_Off(){
		this.isNotified = false;
	}
	public void setContactType_Email(){this.contactType = "email";}
	public void setContactType_Phone(){this.contactType = "phone";}


	public Customer(String name, String username, String password, String surname, Coordinate address, String email, String phoneNumber) {
		super(name, username, password);
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	public void placeOrder(Order order){

		this.pay(order.getTotal_price(), order.getRestaurant());
		this.saveOrder(order);
	}

	private void saveOrder(Order order){

		ArrayList<Order> orders = this.retrieveOrders();

		if (orders == null){
			orders = new ArrayList<>();
		}

		orders.add(order);

		try {
			FileOutputStream fileOut = new FileOutputStream("tmp/" + this.id + "orders.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(orders);

			out.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Order> retrieveOrders(){
		ArrayList<Order> orders = null;

		try {
			FileInputStream fileIn = new FileInputStream("tmp/" + this.id + "orders.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			orders = (ArrayList<Order>) in.readObject();

			in.close();
			fileIn.close();

		} catch (IOException e) {
			//No order yet
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return orders;
	}


	private void pay(BigDecimal price, Restaurant restaurant){

	}

	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}

	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}
}
