package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class RestaurantDashboard extends Dashboard {

    JLabel name;
    public RestaurantDashboard(){
        super();
        name = new JLabel("Restaurant");
        add(name);
        setVisible(true);
    }

}
