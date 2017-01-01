package fr.ecp.IS1220.group5.project.GUI.customerDashboard;

import fr.ecp.IS1220.group5.project.Login;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.menu.Food;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alexandre_carlier on 20/12/2016.
 */
public class CustomerDashboard extends JFrame implements Observer{

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    Food[] dishes;

    JPanel mainPanel;

    JPanel headerPanel;
    JPanel orderPanel;

    JPanel leftPanel;
    JPanel rightPanel;

    JList<String> restaurantsList;
    JList<String> dishesList;
    JList<String> orderList;

    JLabel intermediatePrice;
    JLabel totalPrice;

    public CustomerDashboard(){

        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(600, 450);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setContentPane(mainPanel);

        //Header

        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome " + myFoodoraSystem.getCurrentUser().getName() + " !");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(welcomeLabel, c);

        JButton historyButton = new JButton("Check history & card");
        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                HistoryFrame.getInstance();
            }
        });
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(historyButton, c);

        JButton infoBoardButton = new JButton("Check Infoboard");
        infoBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InfoBoardFrame.getIntance();
            }
        });
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(infoBoardButton, c);

        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFoodoraSystem.disconnectUser();
                CustomerDashboard.this.dispose();
                new Login();
            }
        });
        c.gridx = 3;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(logoutButton, c);


        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        mainPanel.add(headerPanel, c);

        //Order
        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());



        //Left panel
        leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        leftPanel.setLayout(new GridLayout(4, 1));

        leftPanel.add(new JLabel("Choose a restaurant:"));

        String[] restaurantsNames = myFoodoraSystem.getRestaurantsNames();

        restaurantsList = new JList<>(restaurantsNames);
        restaurantsList.setVisibleRowCount(5);
        restaurantsList.setFixedCellHeight(20);
        restaurantsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        restaurantsList.isSelectedIndex(0);
        restaurantsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                updateMenuList();

            }

        });
        JScrollPane scrollPane = new JScrollPane(restaurantsList);

        leftPanel.add(scrollPane);

        leftPanel.add(new JLabel("Choose your dishes: (double-click to select)"));

        dishes = myFoodoraSystem.getMenu();
        String[] dishesNames = myFoodoraSystem.getMenuNames();

        dishesList = new JList<>(dishesNames);
        dishesList.setVisibleRowCount(5);
        dishesList.setFixedCellHeight(20);
        dishesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        dishesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    addItem2Order();
                }

            }

        });

        JScrollPane scrollPane2 = new JScrollPane(dishesList);
        leftPanel.add(scrollPane2);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.6;
        c.weighty = 1;
        orderPanel.add(leftPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.1;
        c.weighty = 1;
        orderPanel.add(new JPanel(), c);

        //Right panel
        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        rightPanel.setLayout(new GridLayout(5, 1));

        rightPanel.add(new JLabel("Your order (double-click to remove)"));

        String[] chosenDishes = {};
        orderList = new JList<>(chosenDishes);
        orderList.setVisibleRowCount(5);
        orderList.setFixedCellHeight(20);
        orderList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    removeItemFromOrder();
                }

            }

        });
        JScrollPane scrollPane3 = new JScrollPane(orderList);
        rightPanel.add(scrollPane3);

        intermediatePrice = new JLabel("Intermediate price: ");
        totalPrice = new JLabel("Total price (w. fees): ");

        rightPanel.add(intermediatePrice);
        rightPanel.add(totalPrice);

        JPanel endOrderPanel = new JPanel();
        JButton endOrder = new JButton("End order");
        endOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 myFoodoraSystem.endOrder();
            }
        });
        endOrderPanel.add(endOrder);
        rightPanel.add(endOrderPanel);

        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.3;
        c.weighty = 1;
        orderPanel.add(rightPanel, c);


        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(orderPanel, c);


        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("WDY","501");
        CustomerDashboard dashboard = new CustomerDashboard();

    }

    public void updateMenuList(){

        myFoodoraSystem.chooseRestaurant(restaurantsList.getSelectedValue());
        myFoodoraSystem.getCurrentOrder().addObserver(this);
        this.update(myFoodoraSystem.getCurrentOrder(), null);

        dishes = myFoodoraSystem.getMenu();
        String[] dishesNames = myFoodoraSystem.getMenuNames();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(String dishName : dishesNames)
            model.addElement(dishName);

        dishesList.setModel(model);

    }


    public void addItem2Order(){

        try{

            Food food = dishes[dishesList.getSelectedIndex()];

            if (food instanceof Item){
                myFoodoraSystem.getCurrentOrder().addItem((Item) food);
            }else{
                myFoodoraSystem.getCurrentOrder().addMeal((Meal) food);
            }

        } catch (ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(new JFrame(),"You must choose a dish to add.","Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void removeItemFromOrder(){

        try{

            myFoodoraSystem.getCurrentOrder().removeAtIndex(orderList.getSelectedIndex());

        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(new JFrame(),"You must choose a dish to remove.","Error", JOptionPane.ERROR_MESSAGE);
        }catch(ArrayIndexOutOfBoundsException e){
            JOptionPane.showMessageDialog(new JFrame(),"You must choose a dish to remove.","Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        //When the order is changed.
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Food food: myFoodoraSystem.getCurrentOrder().getFood()) {
            model.addElement(food.getName() + " - " + Money.display(food.getPrice()));
        }
        orderList.setModel(model);

        intermediatePrice.setText("Intermediate price: " + Money.display(myFoodoraSystem.getCurrentOrder().getOrder_price()));
        totalPrice.setText("Total price (w. fees): " +  Money.display(myFoodoraSystem.getCurrentOrder().getTotal_price()));

    }

}
