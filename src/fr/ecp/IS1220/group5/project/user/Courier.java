package fr.ecp.IS1220.group5.project.user;

import com.sun.org.apache.xpath.internal.operations.Or;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.menu.Order;

import java.util.ArrayList;

/**
 * <b>The class that represents a Courier</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Courier extends User {
	/**
	 * The surname of the Courier.
	 */
	private String surname;

	/**
	 * The current position of the Courier.
	 */
	private Coordinate position;

	/**
	 * The phone number of the Courier.
	 */
	private String phoneNumber;

	/**
	 * The number of delivered Orders.
	 */
	private int deliveredOrdersCounter = 0;

	/**
	 * The working state of the Courier. True if he is on-duty, false if he is off-duty.
	 */
	private boolean workingState = true;

	/**
	 * the order waiting to be deliver
	 */
	private Order newOrder = null;

	/**
	 * the list of orders which has been refused by this courier
	 */
	private ArrayList<Order> refuseOrders = new ArrayList<>();

	/**
	 *the list of orders which has been delivered by this courier
	 */
	private ArrayList<Order> historyOfOrders = new ArrayList<>();

	/**
	 * The constructor of the Courier class
	 * @param name the name of the Courier
	 * @param username the username of the Courier
	 * @param password the password of the Courier
	 * @param surname the surname of the Courier
	 * @param position the position of the Courier
	 * @param phoneNumber the phone number of the Courier
	 */
	public Courier(String name, String username, String password, String surname,  Coordinate position, String phoneNumber) {
		super(name, username, password);
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Sets the working-state of the Courier to "on-duty".
	 */
	public void setState_OnDuty(){
		this.workingState = true;
	}

	/**
	 * Sets the working-state of the Courier to "off-duty".
	 */
	public void setState_OffDuty(){
		this.workingState = false;
	}

	/**
	 * get the working state
	 */
	public boolean getWorkingState(){
		return workingState;
	}
	/**
	 * Changes the Courier's position.
	 * @param newposition the new position
	 */
	public void changePosition(Coordinate newposition){
		this.position = newposition;
	}

	/**
	 * get the condition of new order treated by the courier
	 */
	public boolean getNewOrderCondition(){return newOrder != null;}

	public Coordinate getPosition(){return position;}

	public int getDeliveredOrdersCounter(){return this.deliveredOrdersCounter;}

	public void setDeliveredOrdersCounter(int num){this.deliveredOrdersCounter = num;}

	public void addDeliveredOrdersCounter(){this.deliveredOrdersCounter = this.deliveredOrdersCounter + 1;}

	public Order getNewOrder(){return newOrder;}

	public void removeNewOrder(){
		this.newOrder = null;
	}

	public void addOrder2History(Order order){
		historyOfOrders.add(order);
	}

	public void addOrder2RefuseList(Order order){this.refuseOrders.add(order);}

	/**
	 * set up new order for the courier
	 * @param order
	 */
	public void setNewOrder(Order order){this.newOrder = order;}

	/**
	 *
	 * @return a representation of the Courier with: surname, position, phone number, delivered orders counter and current working state.
	 */
	@Override
	public String toString() {
		return "Courier{" +
				"surname='" + surname + '\'' +
				", position=" + position +
				", phoneNumber='" + phoneNumber + '\'' +
				", deliveredOrdersCounter=" + deliveredOrdersCounter +
				", workingState=" + workingState +
				'}';
	}
}