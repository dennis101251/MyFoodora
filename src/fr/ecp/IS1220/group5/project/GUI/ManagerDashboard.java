package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class ManagerDashboard extends Dashboard {
    JLabel name;
    public ManagerDashboard(){
        super();
        name = new JLabel("Manager");
        add(name);
        setVisible(true);
    }
}
