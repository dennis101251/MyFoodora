package fr.ecp.IS1220.group5.project.user;

import fr.ecp.IS1220.group5.project.util.Coordinate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class CustomerFactory extends UserFactory {

    @Override
    public User createUser() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        do {
            System.out.println("Hi, Customer: Press (Y) to continue, press (N) to return");

            str = br.readLine();

            if (str.equalsIgnoreCase("Y")) {
                do {
                    System.out.println("What's your username?");
                    str = br.readLine();
                    if (foundRepeatUserame(str)){
                        System.out.println("This username has been used, pleas choose another");
                        continue;
                    }
                    String username = str;

                    System.out.println("What's your password?");
                    str = br.readLine();
                    String password = str;

                    System.out.println("What's your first name?");
                    str = br.readLine();
                    String firstName = str;

                    System.out.println("What's your last name?");
                    str = br.readLine();
                    String lastName = str;

                    System.out.println("What's your address? Format: x,y");
                    str = br.readLine();//Don't check the address's format

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

                    Customer newCustomer = new Customer(firstName,username,password,lastName,address,email,phone);

                    //Set up attribute
                    System.out.println("Do you want to receive the special offer? Y/N");
                    str = br.readLine();

                    if (str.equalsIgnoreCase("Y")){
                        newCustomer.infoBoard.setNotified_On();
                        System.out.println("Select the contact to be used to send the offers: (E)mail/(P)hone");
                        str = br.readLine();

                        if (str.equalsIgnoreCase("E")|str.equalsIgnoreCase("Email")){
                            newCustomer.infoBoard.setContactType_Email();
                        }
                        else if(str.equalsIgnoreCase("P")|str.equalsIgnoreCase("Phone")){
                            newCustomer.infoBoard.setContactType_Phone();
                        }
                        else {
                            newCustomer.infoBoard.setContactType_Email();
                        }
                    }
                    else{
                        newCustomer.infoBoard.setNotified_Off();
                    }

                    System.out.println(newCustomer.toString());

                    System.out.println("Do you confirm your information? Y/N");
                    str = br.readLine();
                    //Type N to repeat, Y to break

                    if (str.equalsIgnoreCase("Y")){
                        return newCustomer;
                    }
                    else if (str.equalsIgnoreCase("N")){
                        continue;
                    }
                }while (!str.equalsIgnoreCase("Y"));
            }
            else if (str.equalsIgnoreCase("N")){
                return null;
            }
            else {
                System.out.println(" *** >> Invalid typing *** ");
                System.out.println("===========================");
                continue;
            }
        }while (!str.equalsIgnoreCase("Y"));
        return null;
    }
}
