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
    private User user = null;

    public void loginUser(String username, String password) {
        user = myFoodoraSystem.loginUser(username,password);
    }

    public void run(){
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                case "login":
                    loginUser(commands[1], commands[2]);
                    break;
                case "createMeal":
                    createMeal(commands[1]);
                    break;
                case "addDish2Meal":
                    addDish2Meal(commands[1], commands[2]);
                    break;
                case "showMeal":
                    showMeal(commands[1]);
                    break;
                case "saveMeal":
                    saveMeal(commands[1]);
                    break;
                case "setMealPrice":
                    setMealPrice(commands[1]);
                    break;
                case "setSpecialOffer":
                    setSpecialOffer(commands[1]);
                    break;
                case "removeFromSpecialOffer":
                    removeFromSpecialOffer(commands[1]);
                    break;
                case "addDish":
                    addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                    break;
                case "addMeal2Order":
                    addMeal2Order(commands[1]);
                    break;
                case "endOrder":
                    endOrder();
                    break;
                case "registerCourrier":
                    break;
                case "onDuty":
                    onDuty(commands[1]);
                    break;
                case "offDuty":
                    offDuty(commands[1]);
                    break;
                case "addContactInfo":
                    addContactInfo(commands[1]);
                    break;
                case "associateCard":
                    associateCard(commands[1], commands[2]);
                    break;
                case "associateAgreement":
                    associateAgreement(commands[1], commands[2]);
                    break;
                case "registerRestaurant":
                    registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]),commands[4]);
                    break;
                case "notifySpecialOffer":
                    break;
                case "registerCustomer":
                    registerCustomer(commands[1], commands[2], commands[3], String2Coordinate(commands[4]), commands[5]);
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

    private void registerRestaurant(String command, String command1, Coordinate coordinate, String command2) {

    }

    private void registerCustomer(String command, String command1, String command2, Coordinate coordinate, String command3) {

    }




    public void createMeal(String mealName) {
        if (user instanceof Restaurant){

            Restaurant restaurant = (Restaurant) user;
            Meal meal = new Meal(mealName);
            restaurant.addMeal(meal);

            System.out.println(meal + " was successfully created!");

        } else {

            System.out.println("Your resurant must be logged in to create a meal.");

        }
    }

    public void addDish2Meal(String dishName, String mealName) {

    }

    public void showMeal(String mealName){

    }

    public void saveMeal(String mealName){

    }

    public void setMealPrice(String mealName){

    }

    public void setSpecialOffer(String mealName){

    }

    public void removeFromSpecialOffer(String mealName){

    }

    public void addDish(String dishName, String dishCategory, BigDecimal unitPrice){

    }

    public void addMeal2Order(String mealName){

    }

    public void endOrder(){

    }

    public void onDuty(String username){

    }

    public void offDuty(String username){

    }

    public void addContactInfo(String contactInfo){

    }

    public void associateCard(String userName, String cardType){

    }

    public void associateAgreement(String username, String agreement){

    }

    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
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
}
