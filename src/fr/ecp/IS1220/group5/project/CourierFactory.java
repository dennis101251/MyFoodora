package fr.ecp.IS1220.group5.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class CourierFactory extends UserFactory {
    @Override
    public User createUser() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        System.out.println("Hi, Customer: Press (Y) to continue, press (N) to return");

        str = br.readLine();

        if (str.equals("Y")) {
            do {
                System.out.println("What's your first name?");
                str = br.readLine();
                String firstName = str;

                System.out.println("What's your last name?");
                str = br.readLine();
                String lastName = str;

                System.out.println("What's your username?");
                str = br.readLine();
                String username = str;

                System.out.println("What's your password?");
                str = br.readLine();
                String password = str;

                System.out.println("What's your address? Format: x,y");
                str = br.readLine();

                StringTokenizer st = new StringTokenizer(str,",");
                double X = Double.parseDouble(st.nextToken());
                double Y = Double.parseDouble(st.nextToken());
                Coordinate address = new Coordinate(X,Y);//tag: Don't check out the format

                System.out.println("What's your phone number?");
                str = br.readLine();
                String phone = str;

                Courier newCourier = new Courier(firstName,username,password,lastName,address,phone);

                //Set up attribute

                System.out.println("What is your current duty status? (On)_duty/(Off)_duty");
                str = br.readLine();

                if (str.equalsIgnoreCase("On")|str.equalsIgnoreCase("On_duty")){
                    newCourier.setState_OnDuty();
                }
                else {
                    newCourier.setState_OffDuty();
                }

                System.out.println(newCourier.toString());

                System.out.println("Do you confirm your information? Y/N");
                str = br.readLine();
                //Type N to repeat, Y to break

                if (str.equalsIgnoreCase("Y")){
                    br.close();
                    return newCourier;
                }
                else if (str.equalsIgnoreCase("N")){
                    continue;
                }
            }while (!str.equalsIgnoreCase("Y"));
        }
        else {
            br.close();
            return null;
        }
        br.close();
        return null;
    }

    public static void main(String[] args) throws IOException {
        CourierFactory courierFactory = new CourierFactory();
        User user = courierFactory.createUser();
        if (user == null){
            System.out.println("no user");
        }
        else {
            System.out.println(user.toString());;
        }

    }
}
