package fr.ecp.IS1220.group5.project;

import java.util.ArrayList;

/**
 * <b>The class that represents a Courier</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Courier extends User {
	
	private String surname;
	private Coordinate position;
	private String phoneNumber;
	private int deliveredOrdersCounter = 0;
	private boolean workingState = true;
	private boolean newOrder = false;
	private ArrayList<Order> orders = new ArrayList<>();
	
	public Courier(String name, String username, String password, String surname,  Coordinate position, String phoneNumber) {
		super(name, username, password);
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
	}

	public void setState_OnDuty(){
		this.workingState = true;
	}

	public void setState_OffDuty(){
		this.workingState = false;
	}

	public void changePosition(Coordinate newposition){
		this.position = newposition;
	}

	public void notifyNewOrder(){
		newOrder = true;
	}

	public void disableNewOrder(){
		newOrder = false;
	}



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
