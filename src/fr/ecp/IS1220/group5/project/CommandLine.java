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

                //Customer
                case "showRestaurant":
                    myFoodoraSystem.showRestaurant();
                    break;
                case "addMeal2Order":
                    myFoodoraSystem.addMeal2Order(commands[1]);
                    break;
                case "endOrder":
                    myFoodoraSystem.endOrder();
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
