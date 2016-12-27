package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Restaurant;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class RestaurantInfoPanel extends JPanel {

    private Restaurant restaurant;
    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    JPanel headerPanel;
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

    JButton status;

    public RestaurantInfoPanel(){
        super();
        this.add(new JLabel(" "));
    }
    public RestaurantInfoPanel(Restaurant restaurant){
        super();



    }
}
