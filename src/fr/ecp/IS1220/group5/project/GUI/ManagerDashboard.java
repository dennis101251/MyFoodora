package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.GUI.managerDashboard.userTabPanel;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class ManagerDashboard extends JFrame{

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    Customer[] customers;
    Restaurant[] restaurants;
    Courier[] couriers;

    JPanel mainPanel;
    JPanel headerPanel;
    JPanel workingPanel;

    JTabbedPane tabbedPane;
    JPanel userTabPanel;
        JPanel customersPanel;
        JPanel restaurantsPanel;
        JPanel couriersPanel;
    JPanel financialTabPanel;
    JPanel deliveryTabPanel;

    JList<String> customersList;
    JList<String> courierList;
    JList<String> restaurantList;
    DefaultListModel<String> customersNames;
    DefaultListModel<String> restaurantsName;
    DefaultListModel<String> couriersName;


    JLabel name;

    public ManagerDashboard(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(600, 450);
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

        JButton logoutButton = new JButton("Log out");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myFoodoraSystem.disconnectUser();
                ManagerDashboard.this.dispose();
                new Login();
            }
        });
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

        workingPanel = new JPanel();
        workingPanel.setLayout(new GridBagLayout());

        tabbedPane = new JTabbedPane();

        userTabPanel = new userTabPanel();
        financialTabPanel = new JPanel();
        deliveryTabPanel = new JPanel();
        tabbedPane.addTab("User", userTabPanel);
        tabbedPane.addTab("Financial", financialTabPanel);
        tabbedPane.addTab("Delivery",deliveryTabPanel);
        workingPanel.add(tabbedPane);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(workingPanel,c);

        this.setLocationRelativeTo(null);
        setVisible(true);
    }

    public DefaultListModel<String> listUser(User[] users){
        DefaultListModel<String> nameModel = new DefaultListModel<>();
        for (User user: users
                ) {
            if (!user.getStatus()){
                nameModel.addElement(user.getUsername() + "(disabled)");
            }
            else {
                nameModel.addElement(user.getUsername());
            }
        }
        return nameModel;
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("dennis","101251");
        ManagerDashboard managerDashboard = new ManagerDashboard();
    }


}
