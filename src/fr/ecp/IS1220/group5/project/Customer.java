package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Customer extends User{
	
	private String surname;
	private Coordinate address;
	private FidelityCard fidelityCard = new BasicFidelityCard();
	private ArrayList<Order> historyOfOrder = new ArrayList<>();
	public InfoBoard infoBoard = new InfoBoard();

	public Customer(String name, String surname, String username, String password, Coordinate address, String email, String phoneNumber) {
		super(name, username, password);
		this.surname = surname;
		this.address = address;
		this.infoBoard.setEmail(email);
		this.infoBoard.setPhone(phoneNumber);
	}

	public Customer(String firstName, String lastName, String username, Coordinate address, String password){
		super(firstName, username, password);
		this.surname = lastName;
		this.address = address;
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
			FileOutputStream fileOut = new FileOutputStream("tmp/orders.ser");
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
			FileInputStream fileIn = new FileInputStream("tmp/orders.ser");
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

	public void addOrderToHistory(Order order){
		this.historyOfOrder.add(order);
	}

	public ArrayList<Order> getHistoryOfOrder(){
		return historyOfOrder;
	}

	private void pay(BigDecimal price, Restaurant restaurant){
	}

	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}

	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}

	@Override
	public String toString() {
		return "Customer{" +
				" first name='" + name + '\'' +
				", username='" + username + '\'' +
				", surname='" + surname + '\'' +
				", address=" + address +
//				", fidelityCard=" + fidelityCard +
				'}';
	}
}
