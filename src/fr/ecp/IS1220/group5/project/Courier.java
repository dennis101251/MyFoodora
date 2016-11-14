package fr.ecp.IS1220.group5.project;

public class Courier extends User {
	
	private String surname;
	private Coordinate position;
	private String phoneNumber;
	private int deliveredOrdersCounter;
	
	public Courier(String name, String username, String surname, Coordinate position, String phoneNumber) {
		super(name, username);
		this.surname = surname;
		this.position = position;
		this.phoneNumber = phoneNumber;
	}

}
