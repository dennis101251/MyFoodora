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
                    myFoodoraSystem.setService_fee(Double.parseDouble(commands[1]));
                    break;
                case "setMarkupPercentage":
                    myFoodoraSystem.setMarkup_percentage(Double.parseDouble(commands[1]));
                    break;
                case "setDeliveryCost":
                    myFoodoraSystem.setDelivery_cost(Double.parseDouble(commands[1]));
                    break;
                case "totalIncome":
                    myFoodoraSystem.totalIncome();
                    break;

                //Courier
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

    public static void main(String[] args) throws IOException, UserNotFoundException {
        CommandLine commandLine = new CommandLine();
        commandLine.run();
    }

    private Coordinate String2Coordinate(String address) {
        String[] coordinates = address.split(":");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }
}
