package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class RestaurantInfoPanel extends JPanel {

    private Restaurant restaurant;
    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    JPanel infoPanel;
    JPanel leftPanel;
    JPanel rightPanel;

    JButton status;

    private UserInfoPanel userInfoPanel;

    public RestaurantInfoPanel(){
        super();
        this.add(new JLabel(" "));
    }
    public RestaurantInfoPanel(final Restaurant restaurant){
        super();
        this.restaurant = restaurant;

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.X_AXIS));

        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(5,1));
        leftPanel.add(new JLabel("Account type: Restaurant"));
        leftPanel.add(new JLabel("Name: " + restaurant.getName()));
        leftPanel.add(new JLabel("Username: " + restaurant.getUsername()));
        leftPanel.add(new JLabel("ID: " + restaurant.getId()));
        leftPanel.add(new JLabel("Address: " + restaurant.getAddress().toString()));
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        infoPanel.add(leftPanel,c);

        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(6,1));
        rightPanel.add(new JLabel("Items: " + restaurant.getItems().size()));
        rightPanel.add(new JLabel("Meals: " + restaurant.   getMeals().size()));
        rightPanel.add(new JLabel("Order: " + restaurant.getOrders().size()));
        rightPanel.add(new JLabel("Total income: " + Money.display(restaurant.getIncome())));

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new GridLayout(1,2));
        statusPanel.add(new JLabel("Account Status:"));
        String buttonText;
        if (restaurant.getStatus()){
            buttonText = "Active";
        }
        else {
            buttonText = "Disactive";
        }
        status = new JButton(buttonText);
        status.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (restaurant.getStatus()){
                    try {
                        myFoodoraSystem.disactivateUser(restaurant.getUsername());
                        status.setText("Disactive");
                        notifyObserver();
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    try {
                        myFoodoraSystem.activateUser(restaurant.getUsername());
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
                        myFoodoraSystem.removeUser(restaurant.getUsername());
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
