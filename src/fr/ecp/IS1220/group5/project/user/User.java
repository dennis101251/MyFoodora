package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.util.IDGenerator;
import fr.ecp.IS1220.group5.project.util.PasswordHash;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * <b>The class which represents the users of MyFoodora</b>
 * <p>
 *     There are 4 types of users:
 * </p>
 *     <ul>
 *         <li>Managers</li>
 *         <li>Restaurants</li>
 *         <li>Customers</li>
 *         <li>Couriers</li>
 *     </ul>
 *
 *
 * @version 2.0
 */
public abstract class User implements Serializable{

	private static final long serialVersionUID = 2530207107664573253L;
	/**
	 * the name of the User.
	 */
	protected String name;
	/**
	 * the username of the User.
	 */
	protected String username;
	/**
	 * The hashed password of the User.
	 */
	protected String password;
	/**
	 * The unique ID of the User.
	 */
	protected int id;
	/**
	 * The current status of the User (true or false).
	 */
	protected boolean status = true;

	/**
	 * The constructor of the User.
	 * @param name the name of the User.
	 * @param username the username of the User.
	 * @param password the password of the User.
	 */
	public User(String name, String username, String password) {

		try {
			this.name = name;
			this.username = username;
			this.password = PasswordHash.createHash(password);
			IDGenerator idGenerator = IDGenerator.getInstance();
			this.id = idGenerator.getNextID();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

	/**
	 * get the type of user
	 */
	public String getType(){return null;};
	/**
	 * Return the username of the User.
	 * @return the username of the User.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Return the name of the User.
	 * @return the name of the User.
	 */
	public String getName(){
		return name;
	}

	/**
	 * Activates a User.
	 */
	public void activate(){
		status = true;
	}

	/**
	 * Disactivates a User.
	 */
	public void disactivate(){status = false;}

	/**
	 * Returns the status of a User.
	 * @return true of activated, false if disactivated.
	 */
	public boolean getStatus(){return status;}

	/**
	 * Returns the unique ID of the customer.
	 * @return the unique ID of the custimer.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the hashed password of the User.
	 * @return the hashed password of the User
	 */
	public String getPassword() {
		return password;
	}

	@Override
	public String toString() {
		return "User{" +
				"name='" + name + '\'' +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", id=" + id +
				'}';
	}

}
