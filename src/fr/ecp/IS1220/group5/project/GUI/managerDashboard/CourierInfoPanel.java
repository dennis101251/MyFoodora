package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class CourierInfoPanel extends JPanel {
    private Courier courier;
    JPanel infoPanel;
    JPanel leftPanel;
    JPanel rightPanel;

    JButton status;
    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    private UserInfoPanel userInfoPanel;

    public CourierInfoPanel(){
        super();
        this.add(new JLabel(" "));
    }

    public CourierInfoPanel(final Courier courier){
        super();
        this.courier = courier;

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(6,1));

        leftPanel.add(new JLabel("Account type: Courier"));
        leftPanel.add(new JLabel("First Name: " + courier.getSurname()));
        leftPanel.add(new JLabel("Last Name: " + courier.getName()));
        leftPanel.add(new JLabel("Username: " + courier.getUsername()));
        leftPanel.add(new JLabel("ID: " + courier.getId()));
        leftPanel.add(new JLabel("Phone: " + courier.getPhoneNumber()));
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        infoPanel.add(leftPanel,c);

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6,1));
        rightPanel.add(new JLabel("Current Address: " + courier.getPosition().toString()));
        rightPanel.add(new JLabel("Working state: " + courier.getWorkingState()));
        rightPanel.add(new JLabel("Delivery number: " + courier.getDeliveredOrdersCounter()));
        rightPanel.add(new JLabel("Total income: " + Money.display(courier.getTotalIncome())));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1,2));
        statusPanel.add(new JLabel("Account Status:"));
        String buttonText;
        if (courier.getStatus()){
            buttonText = "Active";
        }
        else {
            buttonText = "Disactive";
        }
        status = new JButton(buttonText);
        status.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (courier.getStatus()){
                    try {
                        myFoodoraSystem.disactivateUser(courier.getUsername());
                        status.setText("Disactive");
                        notifyObserver();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    try {
                        myFoodoraSystem.activateUser(courier.getUsername());
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
                        myFoodoraSystem.removeUser(courier.getUsername());
                        notifyObserver();
                        userInfoPanel.showDefaultPanel();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                    notifyObserver();
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
