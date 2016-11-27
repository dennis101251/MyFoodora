package fr.ecp.IS1220.group5.project;

public class Courier extends User {
	
	private String surname;
	private Coordinate position;
	private String phoneNumber;
	private int deliveredOrdersCounter = 0;
//	private boolean isRegistered = false;
	private boolean workingState = false;
	
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

	public void accept(Order order){
//		take the order, order's state will be changed and the courier's state will also be changed
		this.deliveredOrdersCounter += 1;
		this.workingState = true;
//		not finish yet
	}

	public void refuse(Order order){
//		I think it should return something to let system know the condition
//		not finish yet
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
