package fr.ecp.IS1220.group5.project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by dennis101251 on 2016/11/16.
 */
public class UserlistTest {

    /**
    * Use this Test class to generate a serialization file to initialize the main system
    *
    * */


    public static void main(String[] args) {

        Userlist userlist = new Userlist();

        String filename = "/Users/dennis101251/IdeaProjects/MyFoodora/Users.csv";

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
                    userlist.addUser(newCustomer);
                }
                else if (userType.equals("Restaurant")){
                    Restaurant newRestaurant = new Restaurant(name,username,password,postion);
                    newRestaurant.setId(ID);
                    userlist.addUser(newRestaurant);
                }
                else if (userType.equals("Manager")){
                    Manager newManager = new Manager(name,username,password,surname);
                    newManager.setId(ID);
                    userlist.addUser(newManager);
                }
                else if (userType.equals("Courier")){
                    Courier newCourier = new Courier(name,username,password,surname,postion,phone);
                    newCourier.setId(ID);
                    userlist.addUser(newCourier);
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

        userlist.saveUsers();

        System.out.println("Successfully");

        Userlist userlist1 = new Userlist();


        userlist1.retrieveUsers();

        System.out.println(userlist1.toString());

    }
}
