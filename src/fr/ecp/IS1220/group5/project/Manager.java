package fr.ecp.IS1220.group5.project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Manager extends User {
	
	private String surname;
	private ArrayList<User> users = new ArrayList<>();
	
	public Manager(String name, String username, String surname) {
		super(name, username);
		this.surname = surname;
	}
	
	public void saveUsers(){
		try {
			FileOutputStream fileOut = new FileOutputStream("tmp/users.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			
			out.writeObject(users);
			
			out.close();
			fileOut.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void retrieveUsers(){
		
		try {
			FileInputStream fileIn = new FileInputStream("tmp/users.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			users = (ArrayList<User>) in.readObject();
			
			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public void removeUser(User user) throws UserNotFoundException{
		users.remove(user);
	}

}
