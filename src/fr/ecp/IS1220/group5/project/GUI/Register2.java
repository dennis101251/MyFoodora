package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.GUI.register.*;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by alexandre_carlier on 19/12/2016.
 */
public class Register2 extends JFrame {

    MyFoodoraSystemGUI myFoodoraSystem;

    JPanel mainPanel;
    JPanel radioPanel;
    JPanel userPanel;

    JPanel registerPanel;
    JButton registerButton;

    CustomerRegisterPanel panelCustomer;
    RestaurantRegisterPanel panelRestaurant;
    CourierRegisterPanel panelCourier;
    ManagerRegisterPanel panelManager;
    RegisterPanel currentCard;
    ActionListener listener;

    public Register2(){
        super("Register");

        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        // set the size of the window
        setSize(600, 400);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        GridBagLayout grid = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        mainPanel.setLayout(grid);
        this.setContentPane(mainPanel);

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register2.this.changeCard((JRadioButton) e.getSource());
            }
        };

        //RadioButton Panel : choose the type of user
        radioPanel = new JPanel();

        JRadioButton customerButton = new JRadioButton("Customer");
        customerButton.addActionListener(listener);
        customerButton.setSelected(true);

        JRadioButton restaurantButton = new JRadioButton("Restaurant");
        restaurantButton.addActionListener(listener);

        JRadioButton courierButton  = new JRadioButton("Courier");
        courierButton.addActionListener(listener);

        JRadioButton managerButton = new JRadioButton("Manager");
        managerButton.addActionListener(listener);

        ButtonGroup group = new ButtonGroup();
        group.add(customerButton);
        group.add(restaurantButton);
        group.add(courierButton);
        group.add(managerButton);
        radioPanel.add(customerButton);
        radioPanel.add(restaurantButton);
        radioPanel.add(courierButton);
        radioPanel.add(managerButton);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        mainPanel.add(radioPanel, c);

        //UserPanel : contains a differnt card for each type of user
        userPanel = new JPanel();
        CardLayout cardLayout = new CardLayout(0,0);
        userPanel.setLayout(cardLayout);


        //Panel Customer
        panelCustomer = new CustomerRegisterPanel();
        currentCard = panelCustomer;
        userPanel.add(panelCustomer);

        //Panel Restaurant
        panelRestaurant = new RestaurantRegisterPanel();
        userPanel.add(panelRestaurant);

        //Panel Courier
        panelCourier = new CourierRegisterPanel();
        userPanel.add(panelCourier);

        //Panel Manager
        panelManager = new ManagerRegisterPanel();
        userPanel.add(panelManager);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(userPanel, c);

        //Register button panel

        registerPanel = new JPanel();
        registerPanel.setLayout(new BoxLayout(registerPanel,BoxLayout.X_AXIS));
        JButton loginButton = new JButton("← Back to Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Register2.this.dispose();
                new Login();
            }
        });
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        registerPanel.add(loginButton);
        registerPanel.add(Box.createHorizontalGlue());
        registerPanel.add(registerButton);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        mainPanel.add(registerPanel, c);


        //Set visible
        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    public void changeCard(JRadioButton btn){

        currentCard.setVisible(false);

        switch (btn.getText()){
            case "Customer":
                currentCard = panelCustomer;
                panelCustomer.setVisible(true);
                break;
            case "Restaurant":
                currentCard = panelRestaurant;
                panelRestaurant.setVisible(true);
                break;
            case "Courier":
                currentCard = panelCourier;
                panelCourier.setVisible(true);
                break;
            case "Manager":
                currentCard = panelManager;
                panelManager.setVisible(true);
                break;
        }

    }

    public static void main(String[] args) {
        Register2 register = new Register2();
    }


    public void registerUser(){
        switch (currentCard.getUserType()){
            case "Customer":
                myFoodoraSystem.registerCustomer(panelCustomer.getFirstName(), panelCustomer.getLastName(), panelCustomer.getUserame(), panelCustomer.getPassword(), panelCustomer.getAddress(), panelCustomer.getMail(), panelCustomer.getPhone());
                break;
            case "Restaurant":
                myFoodoraSystem.registerRestaurant(panelRestaurant.getName(), panelRestaurant.getUserame(), panelRestaurant.getAddress(), panelRestaurant.getPassword());
                break;
            case "Courier":
                myFoodoraSystem.registerCourier(panelCourier.getFirstName(), panelCourier.getLastName(), panelCourier.getUserame(), panelCourier.getPassword(), panelCourier.getAddress(), panelCourier.getPhone());
                break;
            case "Manager":
                myFoodoraSystem.registerManager(panelManager.getName(), panelManager.getLastName(), panelManager.getUserame(), panelManager.getPassword());
                break;
        }
    }
}
