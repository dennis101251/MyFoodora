package fr.ecp.IS1220.group5.project;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Created by dennis101251 on 2016/11/27.
 */
public class CommandLine {

    MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
    private Scanner scanner = new Scanner(System.in);

    public void run(){
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                case "login":
                    myFoodoraSystem.loginUser(commands[1], commands[2]);
                    break;
                case "createItem":
                    myFoodoraSystem.createItem(commands[1], new BigDecimal(commands[2]));
                    break;
                case "createMeal":
                    myFoodoraSystem.createMeal(commands[1]);
                    break;
                case "addDish2Meal":
                    myFoodoraSystem.addDish2Meal(commands[1], commands[2]);
                    break;
                case "showMeal":
                    myFoodoraSystem.showMeal(commands[1], commands[2]);
                    break;
                case "saveMenu":
                    myFoodoraSystem.saveMenu();
                    break;
                case "setMealPrice":
                    myFoodoraSystem.setMealPrice(commands[1]);
                    break;
                case "setSpecialOffer":
                    myFoodoraSystem.setSpecialOffer(commands[1]);
                    break;
                case "removeFromSpecialOffer":
                    myFoodoraSystem.removeFromSpecialOffer(commands[1]);
                    break;
                case "addDish":
                    myFoodoraSystem.addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                    break;
                case "addMeal2Order":
                    myFoodoraSystem.addMeal2Order(commands[1]);
                    break;
                case "endOrder":
                    myFoodoraSystem.endOrder();
                    break;
                case "registerCourrier":
                    break;
                case "onDuty":
                    myFoodoraSystem.onDuty(commands[1]);
                    break;
                case "offDuty":
                    myFoodoraSystem.offDuty(commands[1]);
                    break;
                case "addContactInfo":
                    myFoodoraSystem.addContactInfo(commands[1]);
                    break;
                case "associateCard":
                    myFoodoraSystem.associateCard(commands[1], commands[2]);
                    break;
                case "associateAgreement":
                    myFoodoraSystem.associateAgreement(commands[1], commands[2]);
                    break;
                case "registerRestaurant":
                    myFoodoraSystem.registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]), commands[4]);
                    break;
                case "notifySpecialOffer":
                    break;
                case "registerCustomer":
                    myFoodoraSystem.registerCustomer(commands[1], commands[2], commands[3], String2Coordinate(commands[4]), commands[5]);
                    break;
                case "help":
                    System.out.println("List of available commands:");
                    break;
                default:
                    System.out.println("Unvalid command. Type help to check the available commands.");
                    break;
            }

        }
    }

//    public void start() throws IOException, UserNotFoundException {
//        System.out.println("================================");
//        System.out.println("      Welcome to MyFoodora!     ");
//        System.out.println("================================");
//
//        String command = null;
//
//        do {
//            System.out.println(" ");
//            System.out.println(">>   START            ");
//            System.out.println(" ");
//            System.out.println("    - login username password");
//            System.out.println("    - register");
//            System.out.println("    - help");
//            System.out.println("    - quit");
//            System.out.println(" ");
//
//            command = scanner.nextLine();
//
//            String[] commands = command.split(" ");
//            switch (commands[0]){
//                case "login":
////                    format: login username password
//                    if (commands.length == 1){
//                        System.out.println("lack of username and password");
//                        break;
//                    }
//                    else {
//                        login(commands[1],commands[2]);
//                    }
//                    break;
//                case "register":
//                    register();
//                    break;
//                case "quit":
//                    break;
//                case "help":
//                    startHelp();
//                    break;
//                default:
//                    System.out.println("Invalid command. Type help to check the available commands.");
//                    break;
//            }
//        }while (!(command.equalsIgnoreCase("quit")));
//
//        scanner.close();
//        System.out.println("end");
//    }
//
//    public void startHelp(){
//        System.out.println("type the following command to continue");
//        System.out.println("[1] login username password");
//        System.out.println("[2] register");
//        System.out.println("[3] help");
//        System.out.println("[4] quit");
//    }
//
//    public void login(String username, String password){
//        user = myFoodoraSystem.loginUser(username,password);
//        if (user == null){
//            System.out.println("Please try again");
//        }
//        else {
//            if (user instanceof Customer){
//                System.out.println("hi Customer!");
//            }
//            else if (user instanceof Restaurant){
//                System.out.println("hi Restaurant!");
//            }
//            else if (user instanceof Courier){
//                System.out.println("hi Courier!");
//            }
//            else if (user instanceof  Manager){
//                System.out.println("hi Manager!");
//            }
//            else {
//                System.out.println("error");
//            }
//        }
//    }
//
//    public void register() throws IOException {
//
//        boolean registerSuccessfully = false;
//        User newUser = null;
//
//        String command = null;
//        Scanner scanner = new Scanner(System.in);
//
//        do {
//
//            if (registerSuccessfully){
//                break;
//            }
//
//            System.out.println(">>   START   >>   REGISTER");
//            System.out.println("Select which type you want register, type quit to return");
//            System.out.println(" - customer");
//            System.out.println(" - restaurant");
//            System.out.println(" - courier");
//            System.out.println(" - manager");
//
//
//            command = scanner.nextLine();
//
//            String[] commands = command.split(" ");
//            switch (commands[0]){
//                case "customer":
//                    CustomerFactory customerFactory = new CustomerFactory();
//                    newUser = customerFactory.createUser();
//                    if (newUser == null){
//                        System.out.println("no user has been created");
//                    }
//                    else {
//                        myFoodoraSystem.addUser(newUser);
//                        registerSuccessfully = true;
//                    }
//                    break;
//                case "restaurant":
//                    RestaurantFactory restaurantFactory = new RestaurantFactory();
//                    newUser = restaurantFactory.createUser();
//                    if (newUser == null){
//                        System.out.println("no user has been created");
//                    }
//                    else {
//                        myFoodoraSystem.addUser(newUser);
//                        registerSuccessfully = true;
//                    }
//                    break;
//                case "courier":
//                    CourierFactory courierFactory = new CourierFactory();
//                    newUser = courierFactory.createUser();
//                    if (newUser == null){
//                        System.out.println("no user has been created");
//                    }
//                    else {
//                        myFoodoraSystem.addUser(newUser);
//                        registerSuccessfully = true;
//                    }
//                    break;
//                case "manager":
//                    ManagerFactory managerFactory = new ManagerFactory();
//                    newUser = managerFactory.createUser();
//                    if (newUser == null){
//                        System.out.println("no user has been created");
//                    }
//                    else {
//                        myFoodoraSystem.addUser(newUser);
//                        registerSuccessfully = true;
//                    }
//                    break;
//                case "quit":
//                    System.out.println("quit");
//                    break;
//                case "help":
//                    loginHelp();
//                    break;
//                default:
//                    System.out.println("Invalid command. Type help to check the available commands.");
//                    break;
//            }
//        }while (!(command.equalsIgnoreCase("quit")));
//    }
//
//    public void loginHelp(){
//        System.out.println(">>login help");
//        System.out.println("type the name of user which you want to choose");
//    }

    public static void main(String[] args) throws IOException, UserNotFoundException {
        CommandLine commandLine = new CommandLine();
        commandLine.run();
    }

    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
