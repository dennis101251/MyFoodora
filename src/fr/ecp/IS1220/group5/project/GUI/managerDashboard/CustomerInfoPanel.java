package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class CustomerInfoPanel extends JPanel {
    private Customer customer;
    JPanel infoPanel;
    JPanel leftPanel;
    JPanel rightPanel;

    JLabel firstname;
    JLabel lastname;
    JLabel username;
    JLabel ID;
    JLabel address;
    JLabel email;
    JLabel phone;

    JLabel fidelity;
    JButton status;

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    private UserInfoPanel userInfoPanel;

    public CustomerInfoPanel(){
        super();
        this.add(new JLabel(" "));
    }

    public CustomerInfoPanel(final Customer customer){
        super();
        this.customer = customer;

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());

        firstname = new JLabel("First Name: " +  customer.getSurname());
        lastname = new JLabel("Last Name: " + customer.getName());
        username = new JLabel("Username: " + customer.getUsername());
        ID = new JLabel("ID: " + customer.getId());
        address = new JLabel("Address: " + customer.getAddress().toString());
        email = new JLabel("Email: " + customer.getEmail());
        phone = new JLabel("Phone: " + customer.getPhone());

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(8,1));
        leftPanel.add(new JLabel("Account type: Customer"));
        leftPanel.add(firstname);
        leftPanel.add(lastname);
        leftPanel.add(username);
        leftPanel.add(ID);
        leftPanel.add(address);
        leftPanel.add(email);
        leftPanel.add(phone);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        infoPanel.add(leftPanel,c);

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(7,1));
        rightPanel.add(new JLabel("Notification: " + customer.infoBoard.isNotified().toString()));
        fidelity = new JLabel("Fidelity Card: " + customer.getFidelityCard().getFidelityCardName());
        rightPanel.add(fidelity);
        JButton changeFidelityButton = new JButton("Associate fidelity card");
        changeFidelityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = JOptionPane.showOptionDialog(new Frame(), "Which kind of fidelity card you want choose","Fidelity card",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"Basic","Lottery","Point"},null);
                switch (userChoice){
                    case 2:
                        //Point
                        try {
                            myFoodoraSystem.associateCard(customer.getUsername(),"PointFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case 1:
                        //Lottery
                        try {
                            myFoodoraSystem.associateCard(customer.getUsername(),"LotteryFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case 0:
                        //Basic
                        try {
                            myFoodoraSystem.associateCard(customer.getUsername(),"basicFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    default:
                }
                fidelity.setText("Fidelity Card: " + customer.getFidelityCard().getFidelityCardName());

            }
        });
        rightPanel.add(changeFidelityButton);
        rightPanel.add(new JLabel("Order: " + customer.getNumOfOrder()));
        rightPanel.add(new JLabel("Total Expense: " + Money.display(customer.getTotalExpense())));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1,2));
        statusPanel.add(new JLabel("Account Status:"));
        String buttonText;
        if (customer.getStatus()){
            buttonText = "Active";
        }
        else {
            buttonText = "Disactive";
        }
        status = new JButton(buttonText);
        status.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customer.getStatus()){
                    try {
                        myFoodoraSystem.disactivateUser(customer.getUsername());
                        status.setText("Disactive");
                        notifyObserver();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    try {
                        myFoodoraSystem.activateUser(customer.getUsername());
                        status.setText("Active");
                        notifyObserver();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        statusPanel.add(status);
        rightPanel.add(statusPanel);

        JButton removeButton = new JButton("Delete this user");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = JOptionPane.showOptionDialog(new JFrame(),"You want to delete this user?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"Cancel","Yes"},null);
                if (userChoice == 1){
                    //Confirm
                    try {
                        myFoodoraSystem.removeUser(customer.getUsername());
                        notifyObserver();
                        userInfoPanel.showDefaultPanel();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                else if (userChoice == 0){
                    //Cancel

                }
            }
        });
        rightPanel.add(removeButton);

        rightPanel.setBorder(BorderFactory.createEmptyBorder(0,20,0,0));

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        infoPanel.add(rightPanel,c);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        this.add(infoPanel,c);
    }

    public synchronized void addObserver(UserInfoPanel userInfoPanel){
        this.userInfoPanel = userInfoPanel;
    }

    public void notifyObserver(){
        userInfoPanel.updateInfo();
    }
}
