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
	protected static int uniqueValue = 45132486;
	protected boolean status = true;

	public User(String name, String username, String password) throws InvalidKeySpecException, NoSuchAlgorithmException {
		this.name = name;
		this.username = PasswordHash.createHash(username);
		this.password = password;
		this.id = uniqueValue;
		uniqueValue++;
	}

	public void setId(int id){
		this.id = id;
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
}
