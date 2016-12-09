package fr.ecp.IS1220.group5.project;

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
                    myFoodoraSystem.loginUser(commands[1], commands[2]);
                    break;
                case "showCurrentUser":
                    myFoodoraSystem.showCurrentUser();
                    break;
                case "disconnect":
                    myFoodoraSystem.disconnectUser();
                    break;
                case "registerRestaurant":
                    myFoodoraSystem.registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]), commands[4]);
                    break;
                case "registerCustomer":
                    myFoodoraSystem.registerCustomer(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6], commands[7]);
                    break;
                case "registerCourier":
                    myFoodoraSystem.registerCourier(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6]);
                    break;
                case "registerManager":
                    myFoodoraSystem.registerManager(commands[1], commands[2], commands[3], commands[4]);
                    break;
                //Restaurant
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
                case "setSpecialOffer":
                    myFoodoraSystem.setSpecialOffer(commands[1]);
                    break;
                case "removeFromSpecialOffer":
                    myFoodoraSystem.removeFromSpecialOffer(commands[1]);
                    break;
                case "addDish":
                    myFoodoraSystem.addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                    break;
                case "sendMessage":
                    myFoodoraSystem.sendMessage(commands[1]);
                    break;
                case "showOrdersOfRestaurant":
                    myFoodoraSystem.showOrdersOfRestaurant();
                    break;
                case "mostSellingRestaurant":
                    myFoodoraSystem.mostSellingRestaurant();
                    break;
                case "leastSellingRestaurant":
                    myFoodoraSystem.leastSellingRestaurant();
                    break;

                //Customer
                case "showRestaurant":
                    myFoodoraSystem.showRestaurant();
                    break;
                case "chooseRestaurant":
                    myFoodoraSystem.chooseRestaurant(commands[1]);
                    break;
                case "showMenu":
                    myFoodoraSystem.showMenu();
                    break;
                case "addMeal2Order":
                    myFoodoraSystem.addMeal2Order(commands[1],Integer.parseInt(commands[2]));
                    break;
                case "addItem2Order":
                    myFoodoraSystem.addItem2Order(commands[1],Integer.parseInt(commands[2]));
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
                    myFoodoraSystem.setNotified(commands[1]);
                    break;
                case "checkInfoBoard":
                    myFoodoraSystem.checkInfoBoard();
                    break;
                case "showFidelityCard":
                    myFoodoraSystem.showFidelityCard();
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

                //Courier
                case "onDuty":
                    myFoodoraSystem.onDuty();
                    break;
                case "offDuty":
                    myFoodoraSystem.offDuty();
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
