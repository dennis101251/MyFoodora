package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.GUI.courierDashboard.NewPhoneFrame;
import fr.ecp.IS1220.group5.project.Login;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import fr.ecp.IS1220.group5.project.util.GBC;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class CourierDashboard extends JFrame {

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    Courier courier;

    JPanel mainPanel;

    JPanel workingPanel;
    JPanel headerPanel;
        JButton newContactButton;
        JFrame newContactFrame = null;
    JPanel statePanel;
        JLabel stateLabel;
        JButton stateButton;
    JPanel newOrderPanel;
        JTextArea newOrderArea;
        JButton acceptButton;
        JButton refuseButton;
    JPanel postionPanel;
        JLabel positonXLabel;
        JTextField positonXField;
        JLabel positonYLabel;
        JTextField positonYField;
        JButton updateButton;
    JPanel orderPanel;
        JLabel orderLabel;
        JLabel incomeLabel;
        JTextArea orderArea;
    JPanel contactPanel;
        JLabel phoneLabel;
        JButton newPhoneButton;


    public CourierDashboard(){
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

        courier = (Courier) myFoodoraSystem.getCurrentUser();
        JLabel welcomeLabel = new JLabel("Welcome " + myFoodoraSystem.getCurrentUser().getType() + ", "+ myFoodoraSystem.getCurrentUser().getName() + " !");
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
                CourierDashboard.this.dispose();
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

        workingPanel = new JPanel(new GridBagLayout());
        mainPanel.add(workingPanel, new GBC(0,1));
        workingPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        statePanel = new JPanel(new GridLayout(1,2));
        stateLabel = new JLabel("Working state: ");
        statePanel.add(stateLabel);
        stateButton = new JButton(getWorkingState());
        stateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courier.getWorkingState()){
                    //On > Off
                    courier.setState_OffDuty();
                    try {
                        myFoodoraSystem.updateUser(courier);
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    stateButton.setText(getWorkingState());
                }
                else {
                    courier.setState_OnDuty();
                    try {
                        myFoodoraSystem.updateUser(courier);
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    stateButton.setText(getWorkingState());
                }
            }
        });
        statePanel.add(stateButton);
        workingPanel.add(statePanel,new GBC(0,0));

        newOrderPanel = new JPanel(new GridBagLayout());
        newOrderPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,20));
        JLabel jLabel = new JLabel("New Order");
        jLabel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        newOrderPanel.add(jLabel, new GBC(0,0,2,1));
        newOrderArea = new JTextArea(getOrderInfo(),3,15);

        newOrderArea.setLineWrap(true);
        newOrderArea.setWrapStyleWord(true);
        newOrderPanel.add(newOrderArea, new GBC(0,1,2,1));

        acceptButton = new JButton("Accept");
        refuseButton = new JButton("Refuse");
        if (courier.getNewOrderCondition()){
            acceptButton.setEnabled(true);
            refuseButton.setEnabled(true);
        }
        else {
            acceptButton.setEnabled(false);
            refuseButton.setEnabled(false);
        }
        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myFoodoraSystem.accept();
                } catch (UserNotFoundException e1) {
                    e1.printStackTrace();
                }
                positonXField.setText(String.valueOf(courier.getPosition().getX()));
                positonYField.setText(String.valueOf(courier.getPosition().getY()));
                newOrderArea.setText(getOrderInfo());
                if (courier.getNewOrderCondition()){
                    acceptButton.setEnabled(true);
                    refuseButton.setEnabled(true);
                }
                else {
                    acceptButton.setEnabled(false);
                    refuseButton.setEnabled(false);
                }
                orderArea.setText(getHistoryOrder());
                orderLabel.setText("Order:  you have finish " + courier.getDeliveredOrdersCounter() + " order");
            }
        });

        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myFoodoraSystem.refuse();
                } catch (UserNotFoundException e1) {
                    e1.printStackTrace();
                }
                newOrderArea.setText(getOrderInfo());
                if (courier.getNewOrderCondition()){
                    acceptButton.setEnabled(true);
                    refuseButton.setEnabled(true);
                }
                else {
                    acceptButton.setEnabled(false);
                    refuseButton.setEnabled(false);
                }
            }
        });

        newOrderPanel.add(acceptButton,new GBC(0,2,1,1));
        newOrderPanel.add(refuseButton, new GBC(1,2,1,1));
        newOrderPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        workingPanel.add(newOrderPanel, new GBC(0,2));

        postionPanel = new JPanel(new GridBagLayout());
        postionPanel.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        postionPanel.add(new JLabel("Position: "), new GBC(0,0,2,1).setFill(GridBagConstraints.BOTH));
        positonXLabel = new JLabel("X: ");
        postionPanel.add(positonXLabel,new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH));
        positonXField = new JTextField(String.valueOf(courier.getPosition().getX()),5);
        positonXField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){

                }else{
                    e.consume();
                }
            }
        });
        postionPanel.add(positonXField,new GBC(1,1,1,1).setFill(GridBagConstraints.BOTH));

        positonYLabel = new JLabel("Y: ");
        postionPanel.add(positonYLabel,new GBC(2,1,1,1).setFill(GridBagConstraints.BOTH));
        positonYField = new JTextField(String.valueOf(courier.getPosition().getY()),5);
        positonYField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){

                }else{
                    e.consume();
                }
            }
        });
        postionPanel.add(positonYField,new GBC(3,1,1,1).setFill(GridBagConstraints.BOTH));
        updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                courier.changePosition(new Coordinate(Double.parseDouble(positonXField.getText()),Double.parseDouble(positonYField.getText())));
                System.out.println("Updated");
            }
        });
        postionPanel.add(updateButton,new GBC(2,0,2,1).setFill(GridBagConstraints.BOTH));
        workingPanel.add(postionPanel, new GBC(0,1).setFill(GridBagConstraints.BOTH));

        orderPanel = new JPanel(new GridBagLayout());
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
        orderLabel = new JLabel("Order:  you have finish " + courier.getDeliveredOrdersCounter() + " order");
        orderPanel.add(orderLabel,new GBC(0,0).setFill(GridBagConstraints.BOTH));
        incomeLabel = new JLabel("Income:  " + Money.display(courier.getTotalIncome()));
        orderPanel.add(incomeLabel, new GBC(0,1).setFill(GridBagConstraints.BOTH));
        orderArea = new JTextArea(getHistoryOrder(),6,15);
        orderArea.setLineWrap(true);
        orderArea.setWrapStyleWord(true);
        orderPanel.add(orderArea,new GBC(0,2).setFill(GridBagConstraints.BOTH));
        workingPanel.add(orderPanel,new GBC(0,3));

        contactPanel = new JPanel(new GridBagLayout());
        contactPanel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        phoneLabel = new JLabel("Phone: " + courier.getPhoneNumber());
        contactPanel.add(phoneLabel,new GBC(0,0));
        newPhoneButton = new JButton("new Phone");
        newPhoneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewPhoneFrame newPhoneFrame = NewPhoneFrame.getInstance();
                newPhoneFrame.addObserver(CourierDashboard.this);
            }
        });
        contactPanel.add(newPhoneButton,new GBC(0,1));
        workingPanel.add(contactPanel,new GBC(0,4).setFill(GridBagConstraints.BOTH));



        this.setLocationRelativeTo(null);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI.getInstance().loginUser("jz", "123456");
        new CourierDashboard();
    }

    public void updateInfo(){
        courier = (Courier) myFoodoraSystem.getCurrentUser();
        phoneLabel.setText("Phone: " + courier.getPhoneNumber());
    }

    public String getWorkingState(){
        if (courier.getWorkingState()){
            return "On";
        }
        else {
            return "Off";
        }
    }

    public String getOrderInfo(){
        String string;
        if (courier.getNewOrderCondition()){
            string = courier.getNewOrder().infoForCourier();
        }
        else {
            string = "You don't have an order to deliver";
        }
        return string;
    }

    public String getHistoryOrder(){
        String string = "";
        if (courier.getDeliveredOrdersCounter() == 0){
            string = "You don't hav any order";
        }
        else {
            int i = 0;
            for (Order order: courier.getHistoryOfOrders()){
                i++;
                string += i + ") " +"\n" + order.infoForCourier();
            }
        }

        return string;
    }
}
