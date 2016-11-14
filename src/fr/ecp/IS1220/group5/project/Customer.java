package fr.ecp.IS1220.group5.project;

public class Customer extends User{
	
	private String surname;
	private Coordinate address;
	private String email;
	private String phoneNumber;
	
	public Customer(String name, String username, String surname, Coordinate address, String email, String phoneNumber) {
		super(name, username);
		this.surname = surname;
		this.address = address;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	
	
	
}
