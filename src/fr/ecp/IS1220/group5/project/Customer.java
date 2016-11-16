package fr.ecp.IS1220.group5.project;

public class Customer extends User{
	
	private String surname;
	private Coordinate address;
	private String email;
	private String phoneNumber;
	private boolean isNotified = false;
	private String contactType;
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

//	@Override
//	public String toString() {
//		return "Customer{" +
//				"surname='" + surname + '\'' +
//				", address=" + address +
//				", email='" + email + '\'' +
//				", phoneNumber='" + phoneNumber + '\'' +
//				'}';
//	}
}
