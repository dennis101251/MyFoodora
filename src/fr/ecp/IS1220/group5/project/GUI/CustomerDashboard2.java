package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexandre_carlier on 20/12/2016.
 */
public class CustomerDashboard2 extends JFrame {


    JPanel mainPanel;

    JPanel headerPanel;
    JPanel orderPanel;

    JPanel leftPanel;
    JPanel rightPanel;

    public CustomerDashboard2(){


        this.setSize(500, 400);
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

        JLabel welcomeLabel = new JLabel("Welcome Customer!");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        headerPanel.add(welcomeLabel, c);

        JButton logoutButton = new JButton("Log out");
        c.gridx = 1;
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

        String[] restaurants = {"McDonalds", "Pizzeria", "Sushi", "KFC", "Subway"};
        JList<String> list = new JList<>(restaurants);
        list.setVisibleRowCount(5);
        list.setFixedCellHeight(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        leftPanel.add(scrollPane);

        leftPanel.add(new JLabel("Choose your dishes:"));

        String[] dishes = {"BigMac", "French Fries", "Coca Cola", "Ham Burger"};
        JList<String> list2 = new JList<>(dishes);
        list2.setVisibleRowCount(5);
        list2.setFixedCellHeight(20);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane2 = new JScrollPane(list2);
        leftPanel.add(scrollPane2);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.65;
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
        rightPanel.setLayout(new GridLayout(4, 1));

        rightPanel.add(new JLabel("Your order"));

        String[] chosenDishes = {"BigMac", "French Fries", "Coca Cola"};
        JList<String> list3 = new JList<>(chosenDishes);
        list3.setVisibleRowCount(5);
        list3.setFixedCellHeight(20);
        JScrollPane scrollPane3 = new JScrollPane(list3);
        rightPanel.add(scrollPane3);

        rightPanel.add(new JLabel("Total price: 5€"));

        JPanel endOrderPanel = new JPanel();
        JButton endOrder = new JButton("End order");
        endOrderPanel.add(endOrder);
        rightPanel.add(endOrderPanel);

        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.25;
        c.weighty = 1;
        orderPanel.add(rightPanel, c);


        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(orderPanel, c);

        this.setVisible(true);


    }

    public static void main(String[] args) {

        CustomerDashboard2 dashboard = new CustomerDashboard2();

    }


}
