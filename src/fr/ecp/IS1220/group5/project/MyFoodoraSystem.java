package fr.ecp.IS1220.group5.project;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class MyFoodoraSystem {
	
	private Userlist users = new Userlist();
	private ArrayList<Order> orders;
	private double service_fee;
	private double markup_percentage;
	private double delivery_cost;
	
	
	public MyFoodoraSystem() throws IOException{

//        Load all registered the users
		retrieveUsers();
        System.out.println(users.toString());
//        sends alerts to the customers that agreed to be notified of special offers

        System.out.println("init successfully");
	}

	public void registerUser(User user){
		users.addUser(user);
	}

	public void retrieveOrders(){
		try {
			FileInputStream fileIn = new FileInputStream("/Users/dennis101251/IdeaProjects/MyFoodora/tmp/orders.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			orders = (ArrayList<Order>) in.readObject();

			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void retrieveUsers(){
        this.users.retrieveUsers();
    }
	
	public void retrieveFinancial(){
		
		Financial financial = null;
		
		try {
			FileInputStream fileIn = new FileInputStream("/Users/dennis101251/IdeaProjects/MyFoodora/tmp/financial.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			
			financial = (Financial) in.readObject();
			
			in.close();
			fileIn.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		service_fee = financial.service_fee;
		markup_percentage = financial.markup_percentage;
		delivery_cost = financial.delivery_cost;
		
	}

	public void openFromFile(String filename){
        try {
            BufferedReader fileInput = new BufferedReader(new FileReader(filename));
            String line = fileInput.readLine(); //Read the first line which is not necessary

            line = fileInput.readLine();

            while (line != null){
                StringTokenizer st = new StringTokenizer(line,",");

                if (!st.hasMoreTokens()){
                    line = fileInput.readLine();
                    continue;
                }

                String name = st.nextToken();


                if (!st.hasMoreTokens()) {
                    throw new BadFileException("Broken file 1");
                }
                int ID = Integer.parseInt(st.nextToken());


                if (!st.hasMoreTokens()) {
                    throw new BadFileException("Broken file 2");
                }
                String username = st.nextToken();

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 3");
                }
                String password = st.nextToken();

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 4");
                }
                String userType = st.nextToken();

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 5");
                }
                String surname = st.nextToken();

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 6");
                }
                String X_tmp = st.nextToken();
                double X;
                if (!X_tmp.equals("*")){
                    X = Double.parseDouble(X_tmp);
                }
                else
                    X = 0;

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 7");
                }
                String Y_tmp = st.nextToken();
                double Y;
                if (!X_tmp.equals("*")){
                    Y = Double.parseDouble(Y_tmp);
                }
                else
                    Y = 0;

                Coordinate postion = new Coordinate(X,Y);

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 8");
                }
                String phone = st.nextToken();

                if (!st.hasMoreElements()){
                    throw new BadFileException("Broken file 9");
                }
                String email = st.nextToken();

                //Read the whole line, put the user info into our workspace

                if (userType.equals("Customer")){
                    Customer newCustomer = new Customer(name,username,password,surname,postion,email,phone);
                    newCustomer.setId(ID);
                    this.users.addUser(newCustomer);
                }
                else if (userType.equals("Restaurant")){
                    Restaurant newRestaurant = new Restaurant(name,username,password,postion);
                    newRestaurant.setId(ID);
                    this.users.addUser(newRestaurant);
                }
                else if (userType.equals("Manager")){
                    Manager newManager = new Manager(name,username,password,surname);
                    newManager.setId(ID);
                    this.users.addUser(newManager);
                }
                else if (userType.equals("Courier")){
                    Courier newCourier = new Courier(name,username,password,surname,postion,phone);
                    newCourier.setId(ID);
                    this.users.addUser(newCourier);
                }
                else{
                    continue;
                }
                line = fileInput.readLine();
            }

            fileInput.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

	public static void main(String[] args) throws IOException{
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();



	}

}
