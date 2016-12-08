package fr.ecp.IS1220.group5.project;

import java.util.ArrayList;
/**
 * <b>The class that represents a Customer</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Customer extends User{
	
	private String surname;
	private Coordinate address;
	private FidelityCard fidelityCard = new BasicFidelityCard();
	private ArrayList<Order> historyOfOrder = new ArrayList<>();
	public InfoBoard infoBoard = new InfoBoard();

	/**
	 *
	 *
	 *
	 * @param name
	 * @param surname
	 * @param username
	 * @param password
	 * @param address
	 * @param email
	 * @param phoneNumber
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

	public Customer(String firstName, String lastName, String username, Coordinate address, String password){
		super(firstName, username, password);
		this.surname = lastName;
		this.address = address;
	}

	public void addOrderToHistory(Order order){
		this.historyOfOrder.add(order);
	}

	public ArrayList<Order> getHistoryOfOrder(){
		return historyOfOrder;
	}

	public void	addPoints(int points){
		this.fidelityCard.addPoints(points);
	}

	public FidelityCard getFidelityCard() {
		return fidelityCard;
	}

	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}

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
