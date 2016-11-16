package fr.ecp.IS1220.group5.project;

import java.io.Serializable;

public abstract class User implements Serializable{

	private static final long serialVersionUID = 2530207107664573253L;
	
	protected String name;
	protected String username;
	protected int id;
	protected static int uniqueValue = 45132486;
	protected boolean status = true;
	
	
	public User(String name, String username) {
		this.name = name;
		this.username = username;
		this.id = uniqueValue;
		uniqueValue++;
	}
}
