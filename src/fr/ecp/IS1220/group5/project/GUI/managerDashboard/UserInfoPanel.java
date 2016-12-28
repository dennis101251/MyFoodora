package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;

import javax.swing.*;
import java.awt.*;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class UserInfoPanel extends JPanel{


    CustomerInfoPanel customerInfoPanel = new CustomerInfoPanel();
    RestaurantInfoPanel restaurantInfoPanel = new RestaurantInfoPanel();
    CourierInfoPanel courierInfoPanel = new CourierInfoPanel();
    DefaultInfoPanel defaultInfoPanel = new DefaultInfoPanel();
    CardLayout card = new CardLayout();

    UserTabPanel userTabPanel;

    public UserInfoPanel(){
        super();

        this.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        this.setLayout(card);

        this.add("DefaultInfoPanel",defaultInfoPanel);
        this.add("customerInfoPanel",customerInfoPanel);
        this.add("restaurantInfoPanel",restaurantInfoPanel);
        this.add("courierInfoPanel",courierInfoPanel);
    }

    public void showInfo(User user){
        if (user instanceof Customer){
            this.remove(customerInfoPanel);
            customerInfoPanel = new CustomerInfoPanel((Customer) user);
            customerInfoPanel.addObserver(this);
            this.add("customerInfoPanel",customerInfoPanel);
            card.show(this,"customerInfoPanel");
        }
        else if (user instanceof Restaurant){
            this.remove(restaurantInfoPanel);
            restaurantInfoPanel = new RestaurantInfoPanel((Restaurant) user);
            restaurantInfoPanel.addObserver(this);
            this.add("restaurantInfoPanel",restaurantInfoPanel);
            card.show(this,"restaurantInfoPanel");
        }
        else if (user instanceof Courier){
            this.remove(courierInfoPanel);
            courierInfoPanel = new CourierInfoPanel((Courier) user);
            courierInfoPanel.addObserver(this);
            this.add("courierInfoPanel",courierInfoPanel);
            card.show(this,"courierInfoPanel");
        }
    }

    public void updateInfo(){
        notifyObserver();
    }

    public synchronized void addObserver(UserTabPanel userTabPanel){
        this.userTabPanel = userTabPanel;
    }

    public void notifyObserver(){
        userTabPanel.updateInfo();
    }
}
