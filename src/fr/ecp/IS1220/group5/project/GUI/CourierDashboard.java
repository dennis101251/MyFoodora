package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class CourierDashboard extends Dashboard {
    JLabel name;
    public CourierDashboard(){
        super();
        name = new JLabel("Courier");

        add(name);
        setVisible(true);
    }
}
