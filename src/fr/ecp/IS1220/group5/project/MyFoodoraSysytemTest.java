package fr.ecp.IS1220.group5.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by dennis101251 on 2016/11/16.
 */
public class MyFoodoraSysytemTest {
//    I want to test the scenario in our system

    public static void main(String[] args) throws IOException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

//      Set a bufferedReader to interact with user
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        System.out.println("Welcome to Myfoodora System");
        System.out.println("_____________________________________________________________");
        do {
            System.out.println("Do you want to register as a new user? Type Y to continue, or N to quit");

            str = br.readLine();

            if (str.equals("Y")){
                System.out.println("What's your first name?");
                str = br.readLine();
                String firstName = str;

                System.out.println("What's your last name?");
                str = br.readLine();
                String lastName = str;

                System.out.println("What's your username?");
                str = br.readLine();
                String username = str;

                System.out.println("Which kind of user you want to register? (M)anager/(Cu)stomer/(Co)urier");

                str = br.readLine();

                if (str.equals("M")|str.equalsIgnoreCase("Manager")){

                    Manager newManager = new Manager(firstName,username,lastName);

                    newManager.toString();
                    myFoodoraSystem.registerUser(newManager);
                }

                else if (str.equals("Cu")|str.equalsIgnoreCase("Customer")){

                    System.out.println("What's your address? Format: x,y");
                    str = br.readLine();

                    StringTokenizer st = new StringTokenizer(str,",");
                    double X = Double.parseDouble(st.nextToken());
                    double Y = Double.parseDouble(st.nextToken());
                    Coordinate address = new Coordinate(X,Y);

                    System.out.println("What's your email?");
                    str = br.readLine();
                    String email = str; //tag: Don't check out the format of email

                    System.out.println("What's your phone number?");
                    str = br.readLine();
                    String phone = str;

                    Customer newCustomer = new Customer(firstName,username,lastName,address,email,phone);

                    System.out.println("Do you want to receive the special offer? Y/N"); //tag: did check out the format
                    str = br.readLine();

                    if (str.equalsIgnoreCase("Y")){
                        newCustomer.setNotified_On();
                        System.out.println("Select the contact to be used to send the offers: (E)mail/(P)hone");
                        str = br.readLine();

                        if (str.equalsIgnoreCase("E")|str.equalsIgnoreCase("Email")){
                            newCustomer.setContactType_Email();
                        }
                        else if(str.equalsIgnoreCase("P")|str.equalsIgnoreCase("Phone")){
                            newCustomer.setContactType_Phone();
                        }
                        else {
                            newCustomer.setContactType_Email();
                        }
                    }
                    else{
                        newCustomer.setNotified_Off();
                    }

                    System.out.println("This is your information");
                    newCustomer.toString();

                    //Can add a step to check out whether the info is correct, lack of time

                    myFoodoraSystem.registerUser(newCustomer);
                }
                else if(str.equals("Co")|str.equalsIgnoreCase("Courier")){
                    System.out.println("What's your address? Format: x,y");
                    str = br.readLine();

                    StringTokenizer st = new StringTokenizer(str,",");
                    double X = Double.parseDouble(st.nextToken());
                    double Y = Double.parseDouble(st.nextToken());
                    Coordinate address = new Coordinate(X,Y);

                    System.out.println("What's your phone number?");
                    str = br.readLine();
                    String phone = str;

                    Courier newCourier = new Courier(firstName,username,lastName,address,phone);

                    System.out.println("What is your current duty status? (On)_duty/(Off)_duty");
                    str = br.readLine();

                    if (str.equalsIgnoreCase("On")|str.equalsIgnoreCase("On_duty")){
                        newCourier.setState_OnDuty();
                    }
                    else {
                        newCourier.setState_OffDuty();
                    }

                    System.out.println("This is your information");
                    newCourier.toString();

                    myFoodoraSystem.registerUser(newCourier);
                }



            }
            else if(str.equals("N")){
                break;
            }
            else {
                System.out.println("you enter the wrong command");
            }

        }while(!str.equals("N"));


    }
}
