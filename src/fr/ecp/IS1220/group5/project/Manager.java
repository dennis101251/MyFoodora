package fr.ecp.IS1220.group5.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Manager extends User implements Observer{
	
	private String surname;
	private Userlist userlist;
	
	public Manager(String name, String username, String surname) {
		super(name, username);
		this.surname = surname;
	}

	public void addUsers
	

	



	@Override
	public void update(Observable o, Object arg) {
		this.users = (ArrayList<User>) arg;
	}
}
