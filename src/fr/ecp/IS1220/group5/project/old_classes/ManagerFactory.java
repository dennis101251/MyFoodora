package fr.ecp.IS1220.group5.project.old_classes;

import fr.ecp.IS1220.group5.project.user.Manager;
import fr.ecp.IS1220.group5.project.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class ManagerFactory extends UserFactory {
    @Override
    public User createUser() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        do {
            System.out.println("Hi, Manager: Press (Y) to continue, press (N) to return");

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

                    Manager newManager = new Manager(firstName,username,password,lastName);

                    System.out.println(newManager.toString());

                    System.out.println("Do you confirm your information? Y/N");
                    str = br.readLine();
                    //Type N to repeat, Y to break

                    if (str.equalsIgnoreCase("Y")){
                        return newManager;
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