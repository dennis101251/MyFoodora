package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.util.Financial;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;
/**
 * <b>The class that represents a Manager</b>
 * It extends the <b>User</b> abstract class.
 *
 * @see User
 */
public class Manager extends User implements Observer{
	
	private String surname;
	private Userlist userlist;
	
	public Manager(String name, String username, String password, String surname) {
		super(name, username, password);
		this.surname = surname;
	}

	public void addUser(User user){
		this.userlist.addUser(user);
	}

	public void removeUser(User user) throws UserNotFoundException {
		this.userlist.removeUser(user);
	}

	public void disactivateUser(User user) throws UserNotFoundException{
		this.userlist.disactivateUser(user);
	}

	public void activateUser(User user) throws UserNotFoundException{
		this.userlist.activateUser(user);
	}

	public Financial retrieveFinancial(){

		Financial financial = null;

		try {
			FileInputStream fileIn = new FileInputStream("tmp/financial.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);

			financial = (Financial) in.readObject();

			in.close();
			fileIn.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return financial;
	}

	public void saveFinancial(Financial financial){
		try {
			FileOutputStream fileOut = new FileOutputStream("tmp/financial.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);

			out.writeObject(financial);

			out.close();
			fileOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeServiceFee(BigDecimal servicefee){
		Financial financial = null;
		financial = retrieveFinancial();
		financial.service_fee = servicefee;
		saveFinancial(financial);
	}

	public void changeMarkup_percentage(BigDecimal markup_percentage){
		Financial financial = null;
		financial = retrieveFinancial();
		financial.markup_percentage = markup_percentage;
		saveFinancial(financial);
	}

	public void changeDelivery_cost(BigDecimal delivery_cost){
		Financial financial = null;
		financial = retrieveFinancial();
		financial.delivery_cost = delivery_cost;
		saveFinancial(financial);
	}

	@Override
	public String toString() {
		return "Manager{" +
				"surname='" + surname + '\'' +
				'}';
	}

	@Override
	public void update(Observable o, Object arg) {
		this.userlist = (Userlist) arg;
	}
}
