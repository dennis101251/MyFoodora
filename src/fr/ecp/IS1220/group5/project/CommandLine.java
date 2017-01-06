package fr.ecp.IS1220.group5.project;

import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.FoodType;
import fr.ecp.IS1220.group5.project.menu.ItemCategory;
import fr.ecp.IS1220.group5.project.menu.MealCategory;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Coordinate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
            command(command);
        }
    }


    public void command(String string) throws UserNotFoundException {
        String[] commands = string.split(" \"");
        for(int i = 0;i<commands.length;i++)
            commands[i] = commands[i].replaceAll("\"", "");

        switch (commands[0]) {
            //System
            case "login":
                if (commands.length == 3) {
                    myFoodoraSystem.loginUser(commands[1], commands[2]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "showCurrentUser":
                myFoodoraSystem.showCurrentUser();
                break;
            case "disconnect":
            case "logout":
                myFoodoraSystem.disconnectUser();
                break;
            case "registerRestaurant":
                if (commands.length == 5) {
                    myFoodoraSystem.registerRestaurant(commands[1], commands[2], String2Coordinate(commands[3]), commands[4]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "registerCustomer":
                if (commands.length == 8) {
                    myFoodoraSystem.registerCustomer(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6], commands[7]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "registerCourier":
                if (commands.length == 7) {
                    myFoodoraSystem.registerCourier(commands[1], commands[2], commands[3], commands[4], String2Coordinate(commands[5]), commands[6]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "registerManager":
                if (commands.length == 5) {
                    myFoodoraSystem.registerManager(commands[1], commands[2], commands[3], commands[4]);
                } else {
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
                if (commands.length == 5) {
                    myFoodoraSystem.createItem(commands[1], new BigDecimal(commands[2]), itemCategory(commands[3]), foodType(commands[4]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "createMeal":
                if (commands.length == 4) {
                    myFoodoraSystem.createMeal(commands[1], mealCategory(commands[2]), foodType(commands[3]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "addDish2Meal":
                if (commands.length == 3) {
                    myFoodoraSystem.addDish2Meal(commands[1], commands[2]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "saveMenu":
                myFoodoraSystem.saveMenu();
                break;
            case "setSpecialOffer":
                if (commands.length == 2) {
                    myFoodoraSystem.setSpecialOffer(commands[1]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "removeFromSpecialOffer":
                if (commands.length == 2) {
                    myFoodoraSystem.removeFromSpecialOffer(commands[1]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "addDish":
                if (commands.length == 4) {
                    myFoodoraSystem.addDish(commands[1], commands[2], new BigDecimal(commands[3]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "sendMessage":
                if (commands.length == 3) {
                    myFoodoraSystem.sendMessage(commands[1],commands[2]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "showOrdersOfRestaurant":
                myFoodoraSystem.showOrdersOfRestaurant();
                break;

            //Customer
            case "showRestaurants":
                myFoodoraSystem.showRestaurant();
                break;
            case "chooseRestaurant":
                if (commands.length == 2) {
                    myFoodoraSystem.chooseRestaurant(commands[1]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "showMenu":
                myFoodoraSystem.showMenu();
                break;
            case "addMeal2Order":
                if (commands.length == 3) {
                    myFoodoraSystem.addMeal2Order(commands[1], Integer.parseInt(commands[2]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "addItem2Order":
                if (commands.length == 3) {
                    myFoodoraSystem.addItem2Order(commands[1], Integer.parseInt(commands[2]));
                } else {
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
                } else {
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
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "setSpecialDiscountFactor":
                if (commands.length == 2) {
                    myFoodoraSystem.setSpecialDiscountFactor(new BigDecimal(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "registerFidelityCard":
                if (commands.length == 2) {
                    myFoodoraSystem.registerFidelityCard(commands[1]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "deleteMessage":
                myFoodoraSystem.deleteMessage(Integer.parseInt(commands[1]));
                break;

            //Manager
            case "showAllCustomers":
                myFoodoraSystem.showAllCustomers();
                break;
            case "showAllRestaurants":
                myFoodoraSystem.showAllRestaurants();
                break;
            case "showMenuItem":
                myFoodoraSystem.showMenuItem(commands[1]);
                break;
            case "showAllCouriers":
                myFoodoraSystem.showAllCouriers();
                break;
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
            case "associateCard":
                if (commands.length == 3) {
                    myFoodoraSystem.associateCard(commands[1], commands[2]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "setServiceFee":
                if (commands.length == 2) {
                myFoodoraSystem.setService_fee(new BigDecimal(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "setMarkupPercentage":
                if (commands.length == 2) {
                myFoodoraSystem.setMarkup_percentage(new BigDecimal(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "setDeliveryCost":
                if (commands.length == 2) {
                myFoodoraSystem.setDelivery_cost(new BigDecimal(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "totalIncome":
                myFoodoraSystem.totalIncome();
                break;
            case "totalDeliveryCost":
                myFoodoraSystem.totalDeliveryCost();
                break;
            case "showTotalProfit":
                myFoodoraSystem.totalProfit();
                break;
            case "showTotalProfit2":
                if (commands.length == 3){
                    myFoodoraSystem.showTotalProfit(fr.ecp.IS1220.group5.project.util.Date.date(commands[1]),
                            fr.ecp.IS1220.group5.project.util.Date.date(commands[2]));
                }
                else {
                    System.out.println("not enough input");
                }
            case "averageIncomePerCustomer":
                myFoodoraSystem.averageIncomePerCustomer();
                break;
            case "setTarget_profit":
                if (commands.length == 2) {
                myFoodoraSystem.setTarget_profit(Double.parseDouble(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
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
                if (commands.length == 2) {
                myFoodoraSystem.setDeliveryPolicy(commands[1]);
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "setProfitPolicy":
                if (commands.length == 2) {
                    myFoodoraSystem.setProfitPolicy(Integer.parseInt(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
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
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "associateAgreement":
                if (commands.length == 3) {
                    myFoodoraSystem.associateAgreement(commands[1], commands[2]);
                } else {
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
                if (commands.length == 2) {
                myFoodoraSystem.changePosition(String2Coordinate(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "findDeliverer":
                if (commands.length == 2) {
                myFoodoraSystem.findDeliverer(Integer.parseInt(commands[1]));
                } else {
                    System.out.println("not enough input");
                }
                break;
            case "runtest":
                runtest(commands[1]);
                break;

            case "help":
                System.out.println("List of available commands:");
                System.out.println("---------------------------");
                System.out.println("login <username> <password> : to log in.");
                System.out.println("logout : to log out.");
                System.out.println("registerRestaurant <name> <username> <address> <password> : (Manager) to add a new restaurant to the system.");
                System.out.println("registerCustomer <firstName> <lastName> <username> <address> <password> : (Manager) to add a new customer to the system.");
                System.out.println("registerCourier <firstName> <lastName> <username> <position> <password> : (Manager) to add a new courier to the system.");
                System.out.println("registerManager <name> <lastName> <username> <password> : (Manager) to add a new manager to the system.");

                System.out.println("\n--------Restaurant--------");
                System.out.println("createItem <itemName> <price> <itemCategory> <foodType> : (Restaurant) to add a new item to the menu.");
                System.out.println("createMeal <mealName> <mealCategory> <foodType> : (Restaurant) to add a new meal to the menu.");
                System.out.println("addDish2Meal <itemName> <mealName> : (Restaurant) to add an item to a given meal.");
                System.out.println("saveMenu : (Restaurant) to save the content of the currently created meal.");
                System.out.println("setSpecialOffer <mealName> : (Restaurant) to add a meal to the Meal-of-the-week special offer.");
                System.out.println("removeFromSpecialOffer <mealName> : (Restaurant) to remove a meal from the meal-of-the-week special offer.");
                System.out.println("sendMessage <title> <message> : (Restaurant) to send a message with a given title to all the customers.");
                System.out.println("findDeliverer <orderID> : (Restaurant) to allocate an order to a deliverer by application of the current delivery politic (you must know the ID-number of the order: use for instance \"showOrder\" from a logged in customer).");

                System.out.println("\n--------Customer--------");
                System.out.println("showRestaurants : (Customer) to show the list of available restaurants.");
                System.out.println("chooseRestaurant : (Customer) to choose a restaurant with a given name and start the order.");
                System.out.println("showMenu : (Customer) to show the menu of the currently chosen restaurant.");
                System.out.println("addMeal2Order <mealName> : (Customer) to add a given meal to the order.");
                System.out.println("addItem2Order <itemName> : (Customer) to add a given item to the order.");
                System.out.println("showOrder : (Customer) to show the content of the current order.");
                System.out.println("endOrder : (Customer) to end an order and pay.");
                System.out.println("showHistoryOfOrdersOfCustomer : (Customer) to show the history of one's orders.");
                System.out.println("setNotified <on OR off> : (Customer) to enable or disable notification from MyFoodora.");
                System.out.println("showFidelityCard : (Customer) to show the current fidelity program");
                System.out.println("registerFidelityCard <BasicFidelityCard OR LotteryFidelityCard OR PointFidelityCard> : (Customer) to register to a given fidelity program.");
                System.out.println("checkInfoBoard : (Customer) to check the new messages in the info-board.");
                System.out.println("deleteMessage <index> : (Customer) to delete the the message n° <index> of the info-board.");

                System.out.println("\n--------Manager--------");
                System.out.println("setDeliveryPolicy <fastDelivery OR fairOccupationDelivery>: (Manager) to set the delivery policy to the passed argument.");
                System.out.println("setProfitPolicy <0 : service fee OR 1 : markup percentage OR 2: delivery cost>: (Manager) to set the profit policy to the passed argument.");
                System.out.println("associateCard <userName> <cardType> : (Manager) to associate a fidelity card to a user with given name.");
                System.out.println("showAllCustomers : (Manager) to show all customers.");
                System.out.println("showAllRestaurants : (Manager) to show all restaurants.");
                System.out.println("showAllCouriers : (Manager) to show all couriers.");
                System.out.println("showTotalProfit : (Manager) to show the total profit.");
                System.out.println("showHistoryOfOrderOfSystem : (Manager) to show the history or orders of MyFoodora.");
                System.out.println("removeUser <username> : (Manager) to remove a user with given username.");
                System.out.println("findUser <username> : (Manager) prints whether the user with given username exists or not in the database.");
                System.out.println("disactivateUser <username> : (Manager) to disactivate a user with given username.");
                System.out.println("activateUser : (Manager) to activate a user with given username.");
                System.out.println("setServiceFee <service_fee> : (Manager) to set the service fee to a given value.");
                System.out.println("setMarkupPercentage <markupPercentage>: (Manager) to set the markup percentage to a given value.");
                System.out.println("setDeliveryCost <deliverycost>: (Manager) to set the delivery cost to a given value.");
                System.out.println("totalIncome : (Manager) to show the total income");
                System.out.println("totalDeliveryCost : (Manager) to show the total delivery cost.");
                System.out.println("averageIncomePerCustomer : (Manager) to show the average income per customer.");
                System.out.println("setTarget_profit <targe_profit> : (Manager) to set the target profit to a given value.");
                System.out.println("determineService_fee : (Manager) determines the service fee to meet the target.");
                System.out.println("determineMarkup_Percentage : (Manager) determines the  markup percentage to meet the target.");
                System.out.println("determineDelivery_Cost : (Manager) determines the delivery cost to meet the target.");
                System.out.println("mostSellingRestaurant : (Manager) to show the most selling restaurant.");
                System.out.println("leastSellingRestaurant : (Manager) to show the least selling restaurant.");
                System.out.println("mostActiveCourier : (Manager) to show the most active courier.");
                System.out.println("leastActiveCourier : (Manager) to show the least active courier.");

                System.out.println("\n--------Courier--------");
                System.out.println("onDuty : (Courier) to set the state of the courier to 'on'");
                System.out.println("offDuty : (Courier) to set the state of the courier to 'off'");
                System.out.println("refuse : (Courier) to refuse the assigned order and delegate the order to other couriers");
                System.out.println("accept : (Courier) to accept the assigned order ");
                System.out.println("changePosition <position> : (Courier) to change the position to a given position");

                System.out.println("\nruntest <testScenario-file> : executes the list of CLUI commands contained on the testScenario file.");
                break;
            default:
                System.out.println("Invalid syntax: type 'help' to display the list of available CLUI commands");
        }
    }

    public void runtest(String fileName) throws UserNotFoundException {

        //Clean all the local history
        Order.delateOrders();
        Userlist.delateUserFile();
        myFoodoraSystem.cleanHistory();
        myFoodoraSystem = new MyFoodoraSystem();

        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            System.out.println("read the file line by line");

            reader = new BufferedReader(new FileReader(file));
            String tempString = null;

            while ((tempString = reader.readLine()) != null && !tempString.equalsIgnoreCase("quit")) {
                command(tempString);
                }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
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
        String[] coordinates = address.split(",");
        return new Coordinate(Double.parseDouble(coordinates[0]), Double.parseDouble(coordinates[1]));
    }

    private ItemCategory itemCategory(String category){
        switch(category){
            case "Starter":
                return ItemCategory.Starter;
            case "MainDish":
                return ItemCategory.MainDish;
            case "Dessert":
                return ItemCategory.Dessert;
            default:
                return ItemCategory.MainDish;
        }
    }

    private FoodType foodType(String type){
        switch (type){
            case "Standard":
                return FoodType.Standard;
            case "Vegetarian":
                return FoodType.Vegetarian;
            case "GlutenFree":
                return FoodType.GlutenFree;
            default:
                return  FoodType.Standard;
        }
    }

    private MealCategory mealCategory(String category){
        switch(category){
            case "FullMeal":
                return MealCategory.FullMeals;
            case "HalfMeal":
                return MealCategory.HalfMeals;
            default:
                return MealCategory.FullMeals;
        }
    }
}
