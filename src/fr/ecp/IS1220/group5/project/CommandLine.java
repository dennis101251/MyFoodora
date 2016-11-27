package fr.ecp.IS1220.group5.project;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by dennis101251 on 2016/11/27.
 */
public class CommandLine {

    MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
    private Scanner scanner = new Scanner(System.in);
    private User user = null;

    public void start() throws IOException, UserNotFoundException {
        System.out.println("================================");
        System.out.println("      Welcome to MyFoodora!     ");
        System.out.println("================================");

        String command = null;

        do {
            System.out.println(" ");
            System.out.println(">>   START            ");
            System.out.println(" ");
            System.out.println("    - login username password");
            System.out.println("    - register");
            System.out.println("    - help");
            System.out.println("    - quit");
            System.out.println(" ");

            command = scanner.nextLine();

            String[] commands = command.split(" ");
            switch (commands[0]){
                case "login":
//                    format: login username password
                    if (commands.length == 1){
                        System.out.println("lack of username and password");
                        break;
                    }
                    else {
                        login(commands[1],commands[2]);
                    }
                    break;
                case "register":
                    register();
                    break;
                case "quit":
                    break;
                case "help":
                    startHelp();
                    break;
                default:
                    System.out.println("Invalid command. Type help to check the available commands.");
                    break;
            }
        }while (!(command.equalsIgnoreCase("quit")));

        scanner.close();
        System.out.println("end");
    }

    public void startHelp(){
        System.out.println("type the following command to continue");
        System.out.println("[1] login username password");
        System.out.println("[2] register");
        System.out.println("[3] help");
        System.out.println("[4] quit");
    }

    public void login(String username, String password){
        user = myFoodoraSystem.loginUser(username,password);
        if (user == null){
            System.out.println("Please try again");
        }
        else {
            if (user instanceof Customer){
                System.out.println("hi Customer!");
            }
            else if (user instanceof Restaurant){
                System.out.println("hi Restaurant!");
            }
            else if (user instanceof Courier){
                System.out.println("hi Courier!");
            }
            else if (user instanceof  Manager){
                System.out.println("hi Manager!");
            }
            else {
                System.out.println("error");
            }
        }
    }

    public void register() throws IOException {

        boolean registerSuccessfully = false;
        User newUser = null;

        String command = null;
        Scanner scanner = new Scanner(System.in);

        do {

            if (registerSuccessfully){
                break;
            }

            System.out.println(">>   START   >>   REGISTER");
            System.out.println("Select which type you want register, type quit to return");
            System.out.println(" - customer");
            System.out.println(" - restaurant");
            System.out.println(" - courier");
            System.out.println(" - manager");


            command = scanner.nextLine();

            String[] commands = command.split(" ");
            switch (commands[0]){
                case "customer":
                    CustomerFactory customerFactory = new CustomerFactory();
                    newUser = customerFactory.createUser();
                    if (newUser == null){
                        System.out.println("no user has been created");
                    }
                    else {
                        myFoodoraSystem.addUser(newUser);
                        registerSuccessfully = true;
                    }
                    break;
                case "restaurant":
                    RestaurantFactory restaurantFactory = new RestaurantFactory();
                    newUser = restaurantFactory.createUser();
                    if (newUser == null){
                        System.out.println("no user has been created");
                    }
                    else {
                        myFoodoraSystem.addUser(newUser);
                        registerSuccessfully = true;
                    }
                    break;
                case "courier":
                    CourierFactory courierFactory = new CourierFactory();
                    newUser = courierFactory.createUser();
                    if (newUser == null){
                        System.out.println("no user has been created");
                    }
                    else {
                        myFoodoraSystem.addUser(newUser);
                        registerSuccessfully = true;
                    }
                    break;
                case "manager":
                    ManagerFactory managerFactory = new ManagerFactory();
                    newUser = managerFactory.createUser();
                    if (newUser == null){
                        System.out.println("no user has been created");
                    }
                    else {
                        myFoodoraSystem.addUser(newUser);
                        registerSuccessfully = true;
                    }
                    break;
                case "quit":
                    System.out.println("quit");
                    break;
                case "help":
                    loginHelp();
                    break;
                default:
                    System.out.println("Invalid command. Type help to check the available commands.");
                    break;
            }
        }while (!(command.equalsIgnoreCase("quit")));
    }

    public void loginHelp(){

    }

    public static void main(String[] args) throws IOException, UserNotFoundException {
        CommandLine commandLine = new CommandLine();
        commandLine.start();
    }
}
