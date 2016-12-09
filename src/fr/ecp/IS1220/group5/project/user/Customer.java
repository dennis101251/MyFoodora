package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.fidelity.BasicFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.FidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.LotteryFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.PointFidelityCard;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.util.Coordinate;

import java.util.ArrayList;
/**
 * <b>The class that represents a Customer</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Customer extends User {
	/**
	 * The surname of the Customer.
	 */
	private String surname;
	/**
	 * The address of the Customer.
	 */
	private Coordinate address;
	/**
	 * The chosen Fidelity Program of the Customer. By default, it is the Basic Fidelity Card.
	 *
	 * @see BasicFidelityCard
	 */
	private FidelityCard fidelityCard = new BasicFidelityCard();
	/**
	 * the list that stores the history of the Customer's orders.
	 */
	private ArrayList<Order> historyOfOrder = new ArrayList<>();
	/**
	 * The info-board that displays and stores the notifications of the Customer.
	 *
	 * @see InfoBoard
	 */
	public InfoBoard infoBoard = new InfoBoard();

	/**
	 *
	 *	The constructor of the Customer.
	 *
	 * @param name the name of the Customer
	 * @param surname the surname of the Customer
	 * @param username the username of the Customer
	 * @param password the password of the Customer
	 * @param address the address of the Customer
	 * @param email the email of the Customer
	 * @param phoneNumber the phone number of the Customer
	 *
	 * @see Coordinate
	 */
	public Customer(String name, String surname, String username, String password, Coordinate address, String email, String phoneNumber) {
		super(name, username, password);
		this.surname = surname;
		this.address = address;
		this.infoBoard.setEmail(email);
		this.infoBoard.setPhone(phoneNumber);
	}

	/**
	 * Another constructor without email and phone number.
	 *
	 * @param firstName the first name of the Customer
	 * @param lastName the last name of the Customer
	 * @param username the username of the Customer
	 * @param address the address of the Customer
	 * @param password the password of the Customer
	 */
	public Customer(String firstName, String lastName, String username, Coordinate address, String password){
		super(firstName, username, password);
		this.surname = lastName;
		this.address = address;
	}

	/**
	 * Adds a given order to the Customer"s history.
	 * @param order the order to add to the customer's history.
	 */
	public void addOrderToHistory(Order order){
		this.historyOfOrder.add(order);
	}

	/**
	 * Return the history of orders.
	 * @return the history of orders, as a list of Order objects.
	 *
	 * @see Order
	 */
	public ArrayList<Order> getHistoryOfOrder(){
		return historyOfOrder;
	}

	/**
	 * Return the selected Fidelity Card chosen by the Customer.
	 * @return the selected Fidelity Card, as a Fidelity Card object.
	 *
	 * @see FidelityCard
	 */
	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}

	/**
	 * Sets the Fidelity Card to the chosen program (Basic, Points or Lottery).
	 * @param fidelityCard	the chosen Fidelity Card (Basic, Points or Lottery).
	 *
	 * @see FidelityCard
	 * @see BasicFidelityCard
	 * @see PointFidelityCard
	 * @see LotteryFidelityCard
	 */
	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}

	/**
	 * Return the address of the customer
	 * @return the address of the customer, as a Coordinate object.
	 */
	public Coordinate getAddress(){
		return address;
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
