package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;

import javax.swing.*;

/**
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class ManagerDashboard extends Dashboard {
    JLabel name;
    public ManagerDashboard(){
        super();
        name = new JLabel();
        name.setText("lallal");
//        workingPanel.add(name);
        setVisible(true);
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("MM","501");
        ManagerDashboard managerDashboard = new ManagerDashboard();
    }
}
