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
	/**
	 * the surname of the Manager.
	 */
	private String surname;
	/**
	 * The list of registerd users.
	 */
	private Userlist userlist;

	/**
	 * The constructor of the Manager.
	 * @param name the name of the Manager.
	 * @param username the username of the Manager.
	 * @param password the password of the Manager.
	 * @param surname the surname of the Manager.
	 */
	public Manager(String name, String username, String password, String surname) {
		super(name, username, password);
		this.surname = surname;
	}

	/**
	 * Adds a user to the userList.
	 * @param user the user to add to the list.
	 */
	public void addUser(User user){
		this.userlist.addUser(user);
	}

	/**
	 * removes a given user from the userList.
	 * @param user the user to be removed.
	 * @throws UserNotFoundException
	 */
	public void removeUser(User user) throws UserNotFoundException {
		this.userlist.removeUser(user);
	}

	/**
	 * disactivates a user.
	 * @param user the user to disactivate.
	 * @throws UserNotFoundException
	 */
	public void disactivateUser(User user) throws UserNotFoundException{
		this.userlist.disactivateUser(user);
	}

	/**
	 * Activates a user.
	 * @param user the user to be activated.
	 * @throws UserNotFoundException
	 */
	public void activateUser(User user) throws UserNotFoundException{
		this.userlist.activateUser(user);
	}

	/**
	 * Retrieves the financial data from a .ser file.
	 * @return the retrieved data.
	 */
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

	/**
	 * Saves the financial data in a .ser file.
	 * @param financial the financial data to be saved.
	 */
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

	/**
	 * Sets the service fee of MyFoodora's system.
	 * @param servicefee the new service fee.
	 */
	public void changeServiceFee(BigDecimal servicefee){
		Financial financial = null;
		financial = retrieveFinancial();
		financial.service_fee = servicefee;
		saveFinancial(financial);
	}

	/**
	 * Changes the markup percentage of MyFoodora's system.
	 * @param markup_percentage the new markup percentage.
	 */
	public void changeMarkup_percentage(BigDecimal markup_percentage){
		Financial financial = null;
		financial = retrieveFinancial();
		financial.markup_percentage = markup_percentage;
		saveFinancial(financial);
	}

	/**
	 * Changes the delivery cost of MyFoodora's system.
	 * @param delivery_cost the new delivery cost.
	 */
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
