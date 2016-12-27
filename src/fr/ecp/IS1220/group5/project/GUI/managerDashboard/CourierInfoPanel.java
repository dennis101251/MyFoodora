package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.user.Courier;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class CourierInfoPanel extends JPanel {

    public CourierInfoPanel(){
        super();
        this.add(new JLabel(" "));
    }

    public CourierInfoPanel(Courier courier){
        super();
        this.add(new JLabel(courier.getName()));
    }
}
