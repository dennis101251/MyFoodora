package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class User implements Serializable{

	private static final long serialVersionUID = 2530207107664573253L;
	
	protected String name;
	protected String username;
	protected String password;
	protected int id;
	protected boolean status = true;

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

	public void setId(int id){
		this.id = id;
	}

	public String getName(){
		return name;
	}

	public String getUsername() {
		return username;
	}

	public String getName(){
		return name;
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

	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
}
