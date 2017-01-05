package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;


import fr.ecp.IS1220.group5.project.Login;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class RestaurantDashboard extends JFrame{

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    Customer[] customers;
    Restaurant[] restaurants;
    Courier[] couriers;

    JPanel mainPanel;
    JPanel headerPanel;
    JPanel workingPanel;

    JTabbedPane tabbedPane;

    MenuTabPanel menuTabPanel;
    AddItemTabPanel addItemTabPanel;
    AddMealTabPanel addMealTabPanel;
    ManageTabPanel statisticsTabPanel;

    JList<String> customersList;
    JList<String> courierList;
    JList<String> restaurantList;
    DefaultListModel<String> customersNames;
    DefaultListModel<String> restaurantsName;
    DefaultListModel<String> couriersName;


    JLabel name;

    public RestaurantDashboard(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(700, 550);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        this.setContentPane(mainPanel);

        //Header
        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome " + myFoodoraSystem.getCurrentUser().getName() + " !");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        headerPanel.add(welcomeLabel, c);

        JButton infoBoardButton = new JButton("Check Infoboard");
        infoBoardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MessageFrame.getInstance();
            }
        });
        c.gridx = 1;
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
                RestaurantDashboard.this.dispose();
                new Login();
            }
        });
        c.gridx = 2;
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

        workingPanel = new JPanel();
        workingPanel.setLayout(new GridBagLayout());

        tabbedPane = new JTabbedPane();

        menuTabPanel = new MenuTabPanel(this);
        addItemTabPanel = new AddItemTabPanel(this);
        addMealTabPanel = new AddMealTabPanel(this);
        statisticsTabPanel = new ManageTabPanel(this);
        tabbedPane.addTab("Menu", menuTabPanel);
        tabbedPane.addTab("Add Item", addItemTabPanel);
        tabbedPane.addTab("Add Meal", addMealTabPanel);
        tabbedPane.addTab("Manage", statisticsTabPanel);
        workingPanel.add(tabbedPane);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(workingPanel,c);

        this.setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }


    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("mcdonalds","123456");
        new RestaurantDashboard();
    }

    public void update(){

        menuTabPanel.update();
        addMealTabPanel.update();
        this.pack();
    }
}
