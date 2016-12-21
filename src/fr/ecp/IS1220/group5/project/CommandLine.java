package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.GUI.Login;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.util.Coordinate;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

/**
 * <b>The class that scans the command line inputs</b>
 *
 * @version 2.0
 */
public class CommandLine {
    /**
     * MyFoodora's system.
     */
    MyFoodoraSystem myFoodoraSystem = new MyFoodoraSystem();
    /**
     * A scanner that scans the command line inputs.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * A loop that scans command line inputs until the user quits.
     * @throws UserNotFoundException
     */
    public void run() throws UserNotFoundException {
        String command = null;
        while (!(command = this.scanner.nextLine()).equalsIgnoreCase("quit")){

            String[] commands = command.split(" ");

            switch (commands[0]){
                //System
                case "login":
                    if (commands.length==3){
                        myFoodoraSystem.loginUser(commands[1], commands[2]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "showCurrentUser":
                    myFoodoraSystem.showCurrentUser();
                    break;
                case "disconnect":
                    myFoodoraSystem.disconnectUser();
                    break;
                case "registerRestaurant":
                    if (commands.length == 5){
                    myFoodoraSystem.registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]), commands[4]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "registerCustomer":
                    if (commands.length == 8) {
                        myFoodoraSystem.registerCustomer(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6], commands[7]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "registerCourier":
                    if (commands.length == 7) {
                        myFoodoraSystem.registerCourier(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "registerManager":
                    if (commands.length == 5) {
                        myFoodoraSystem.registerManager(commands[1], commands[2], commands[3], commands[4]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "sortMostMeal":
                    myFoodoraSystem.sortMostMeal();
                    break;
                case "sortLeastMeal":
                    myFoodoraSystem.sortLeastMeal();
                    break;
                case "sortMostItem":
                    myFoodoraSystem.sortMostItem();
                    break;
                case "sortLeasItem":
                    myFoodoraSystem.sortLeasItem();
                    break;

                //Restaurant
                case "createItem":
                    if (commands.length == 3) {
                        myFoodoraSystem.createItem(commands[1], new BigDecimal(commands[2]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "createMeal":
                    if (commands.length == 2) {
                        myFoodoraSystem.createMeal(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "addDish2Meal":
                    if (commands.length == 3) {
                        myFoodoraSystem.addDish2Meal(commands[1], commands[2]);
                    }
                    else{
                        System.out.println("not enough input");
                    }
                    break;
                case "showMeal":
                    if (commands.length == 3) {
                        myFoodoraSystem.showMeal(commands[1], commands[2]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "saveMenu":
                    myFoodoraSystem.saveMenu();
                    break;
                case "setSpecialOffer":
                    if (commands.length == 2) {
                        myFoodoraSystem.setSpecialOffer(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "removeFromSpecialOffer":
                    if (commands.length == 2) {
                        myFoodoraSystem.removeFromSpecialOffer(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "addDish":
                    if (commands.length == 4) {
                        myFoodoraSystem.addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "sendMessage":
                    myFoodoraSystem.sendMessage(commands[1]);
                    break;
                case "showOrdersOfRestaurant":
                    myFoodoraSystem.showOrdersOfRestaurant();
                    break;

                //Customer
                case "showRestaurant":
                    myFoodoraSystem.showRestaurant();
                    break;
                case "chooseRestaurant":
                    if (commands.length == 2) {
                        myFoodoraSystem.chooseRestaurant(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "showMenu":
                    myFoodoraSystem.showMenu();
                    break;
                case "addMeal2Order":
                    if (commands.length == 3) {
                        myFoodoraSystem.addMeal2Order(commands[1], Integer.parseInt(commands[2]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "addItem2Order":
                    if (commands.length == 3) {
                        myFoodoraSystem.addItem2Order(commands[1], Integer.parseInt(commands[2]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "showOrder":
                    myFoodoraSystem.showOrder();
                    break;
                case "endOrder":
                    myFoodoraSystem.endOrder();
                    break;
                case "showHistoryOfOrderOfCustomer":
                    myFoodoraSystem.showHistoryOfOrder_Customer();
                    break;
                case "setNotified":
                    if (commands.length == 2) {
                        myFoodoraSystem.setNotified(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "checkInfoBoard":
                    myFoodoraSystem.checkInfoBoard();
                    break;
                case "showFidelityCard":
                    myFoodoraSystem.showFidelityCard();
                    break;
                case "setGenericDiscountFactor":
                    if (commands.length == 2) {
                        myFoodoraSystem.setGenericDiscountFactor(new BigDecimal(commands[1]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "setSpecialDiscountFactor":
                    if (commands.length == 2) {
                        myFoodoraSystem.setSpecialDiscountFactor(new BigDecimal(commands[1]));
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "registerFidelityCard":
                    if (commands.length == 2) {
                        myFoodoraSystem.registerFidelityCard(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;

                //Manager
                case "showHistoryOfOrderOfSystem":
                    myFoodoraSystem.showHistoryOfOrder_System();
                    break;
                case "removeUser":
                    myFoodoraSystem.removeUser(commands[1]);
                    break;
                case "findUser":
                    myFoodoraSystem.findUser(commands[1]);
                    break;
                case "disactivateUser":
                    myFoodoraSystem.disactivateUser(commands[1]);
                    break;
                case "activateUser":
                    myFoodoraSystem.activateUser(commands[1]);
                    break;
                case "setServiceFee":
                    myFoodoraSystem.setService_fee(new BigDecimal(commands[1]));
                    break;
                case "setMarkupPercentage":
                    myFoodoraSystem.setMarkup_percentage(new BigDecimal(commands[1]));
                    break;
                case "setDeliveryCost":
                    myFoodoraSystem.setDelivery_cost(new BigDecimal(commands[1]));
                    break;
                case "totalIncome":
                    myFoodoraSystem.totalIncome();
                    break;
                case "totalDeliveryCost":
                    myFoodoraSystem.totalDeliveryCost();
                    break;
                case "totalProfit":
                    myFoodoraSystem.totalProfit();
                    break;
                case "averageIncomePerCustomer":
                    myFoodoraSystem.averageIncomePerCustomer();
                    break;
                case "setTarget_profit":
                    myFoodoraSystem.setTarget_profit(Double.parseDouble(commands[1]));
                    break;
                case "determineService_fee":
                    myFoodoraSystem.determineService_fee();
                    break;
                case "determineMarkup_Percentage":
                    myFoodoraSystem.determineMarkup_Percentage();
                    break;
                case "determineDelivery_Cost":
                    myFoodoraSystem.determineDelivery_Cost();
                    break;
                case "setDeliveryPolicy":
                    myFoodoraSystem.setDeliveryPolicy(commands[1]);
                    break;
                case "mostSellingRestaurant":
                    myFoodoraSystem.mostSellingRestaurant();
                    break;
                case "leastSellingRestaurant":
                    myFoodoraSystem.leastSellingRestaurant();
                    break;
                case "mostActiveCourier":
                    myFoodoraSystem.mostActiveCourier();
                    break;
                case "leastActiveCourier":
                    myFoodoraSystem.leastActiveCourier();
                    break;

                //Courier
                case "onDuty":
                    myFoodoraSystem.onDuty();
                    break;
                case "offDuty":
                    myFoodoraSystem.offDuty();
                    break;
                case "addContactInfo":
                    if (commands.length == 2) {
                        myFoodoraSystem.addContactInfo(commands[1]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "associateAgreement":
                    if (commands.length == 3) {
                        myFoodoraSystem.associateAgreement(commands[1], commands[2]);
                    }
                    else {
                        System.out.println("not enough input");
                    }
                    break;
                case "refuse":
                    myFoodoraSystem.refuse();
                    break;
                case "accept":
                    myFoodoraSystem.accept();
                    break;
                case "changePosition":
                    myFoodoraSystem.changePosition(String2Coordinate(commands[1]));
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

    /**
     * The main function that is called at the beginning of the program.
     * @param args
     * @throws IOException
     * @throws UserNotFoundException
     */
    public static void main(String[] args) throws IOException, UserNotFoundException {

        CommandLine commandLine = new CommandLine();
        commandLine.run();
    }

    /**
     * Converts a String to a Coordinate object.
     * @param address the adress, the format must be "x:y"
     * @return the adress, as a Coordinate object
     *
     * @see Coordinate
     *
     */
    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
