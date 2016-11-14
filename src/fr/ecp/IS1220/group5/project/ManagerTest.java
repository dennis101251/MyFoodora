package fr.ecp.IS1220.group5.project;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class ManagerTest {

	@Test
	public void test() {
		Manager manager = new Manager("Larry", "Page", "lp");
		manager.retrieveUsers();
		//Restaurant restaurant = new Restaurant("Les Dunes", "dunes", new Coordinate(50, 63));
		//Restaurant restaurant2 = new Restaurant("Pizza Pino", "pino", new Coordinate(12, 23));
		//manager.addUser(restaurant);
		manager.saveUsers();
		
		manager.retrieveUsers();
		for (User user: manager.getUsers()){
			System.out.println(user);
		}
		
	}

}
