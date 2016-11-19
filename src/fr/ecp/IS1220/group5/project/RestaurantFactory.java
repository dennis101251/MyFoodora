package fr.ecp.IS1220.group5.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class RestaurantFactory extends UserFactory {
    @Override
    public User createUser() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        do {
            System.out.println("Hi, restaurant: Press (Y) to continue, press (N) to return");

            str = br.readLine();

            if (str.equalsIgnoreCase("Y")) {
                do {
                    System.out.println("What's the restaurant's name?");
                    str = br.readLine();
                    String name = str;

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
                    Coordinate address = new Coordinate(X,Y);

                    Restaurant newRestaurant = new Restaurant(name,username,password,address);

                    System.out.println(newRestaurant.toString());

                    System.out.println("Do you confirm your information? Y/N");
                    str = br.readLine();
                    //Type N to repeat, Y to break

                    if (str.equalsIgnoreCase("Y")){
                        br.close();
                        return newRestaurant;
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
        br.close();
        return null;
    }


//      Test
//   public static void main(String[] args) throws IOException {
//        RestaurantFactory restaurantFactory = new RestaurantFactory();
//        User user = restaurantFactory.createUser();
//        if (user == null){
//            System.out.println("no user");
//        }
//        else {
//            System.out.println(user.toString());;
//        }
//
//    }
}
