package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class CustomerDashboard extends Dashboard {
    JLabel name;
    public CustomerDashboard(){
        super();
        name = new JLabel("Customer");
        add(name);
        setVisible(true);
    }
}
