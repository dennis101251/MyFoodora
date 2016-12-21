package fr.ecp.IS1220.group5.project.old_classes;

import fr.ecp.IS1220.group5.project.GUI.Login;
import fr.ecp.IS1220.group5.project.MyFoodoraSystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by dennis101251 on 2016/11/16.
 */
public class MyFoodoraSysytemTest {

//    Test the scenario in our system

    public static void main(String[] args) throws IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();

//      Set a bufferedReader to interact with user
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str;

        System.out.println("Welcome to Myfoodora System");
        System.out.println("_____________________________________________________________");

//        myFoodoraSystem.registerUser();
        String dennis = "dennis";
        String wrongUser = "dasd";
        String password = "deng87343353";
        String wrongpassword = "2";
        myFoodoraSystem.loginUser(dennis,password);
        myFoodoraSystem.loginUser(wrongUser,password);
        myFoodoraSystem.loginUser(dennis,wrongpassword);

        System.out.println("Test successfully!");
    }
}
