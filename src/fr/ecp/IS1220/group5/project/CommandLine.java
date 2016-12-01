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

    public User getUser(){
        return this.user;
    }

    public void loginUser(String username, String password) {
        user = myFoodoraSystem.loginUser(username,password);
    }

    public void run(){
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                case "login":
                    if (commands.length !=3 ){
                        System.out.println("invalid login");
                    }
                    else {
                        loginUser(commands[1], commands[2]);
                    }
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

                //Register user
                case "registerRestaurant":
                    if (commands.length != 5){
                        System.out.println("invalid login");
                    }
                    else {
                        registerRestaurant(commands[1], commands[2], commands[3], String2Coordinate(commands[4]));
                    }
                    break;
                case "registerCustomer":
                    if (commands.length != 8){
                        System.out.println("invalid login");
                    }
                    else {
                        registerCustomer(commands[1], commands[2], commands[3], commands[4],String2Coordinate(commands[5]), commands[6],commands[7]);
                    }
                    break;
                case "registerCourier":
                    if (commands.length != 7){
                        System.out.println("invalid login");
                    }
                    else {
                        registerCourier(commands[1],commands[2],commands[3],commands[4],String2Coordinate(commands[5]),commands[6]);
                    }
                    break;
                case "registerManager":
                    if (commands.length != 7){
                        System.out.println("invalid login");
                    }
                    else {
                        registerManager(commands[1],commands[2],commands[3],commands[4]);
                    }
                    break;

                case "notifySpecialOffer":
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

    public void registerRestaurant(String name, String username, String password, Coordinate coordinate) {
        myFoodoraSystem.addUser(new Restaurant(name,username,password,coordinate));
    }

    public void registerCustomer(String firstname, String lastname, String username, String password, Coordinate coordinate, String email, String phone) {
        myFoodoraSystem.addUser(new Customer(firstname,username,password,lastname,coordinate,email,phone));
    }

    public void registerCourier(String firstname,String lastname, String username, String password, Coordinate coordinate, String phone){
        myFoodoraSystem.addUser(new Courier(firstname,username,password,lastname,coordinate,phone));
    }

    public void registerManager(String firstnaem, String lastname, String username, String password){
        myFoodoraSystem.addUser(new Manager(firstnaem,username,password,lastname));
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

    public Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }

    public static void main(String[] args) throws IOException, UserNotFoundException {
        CommandLine commandLine = new CommandLine();
        commandLine.run();
    }
}
