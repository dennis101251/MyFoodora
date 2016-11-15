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

	public void addUser(User user){
		this.userlist.addUser(user);
	}

	public void removeUser(User user) throws UserNotFoundException{
		this.userlist.removeUser(user);
	}

	public void disactivateUser(User user) throws UserNotFoundException{
		this.userlist.disactivateUser(user);
	}

	public void activateUser(User user) throws UserNotFoundException{
		this.userlist.activateUser(user);
	}




	@Override
	public void update(Observable o, Object arg) {
		this.userlist = (Userlist) arg;
	}
}
