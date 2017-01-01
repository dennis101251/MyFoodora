package fr.ecp.IS1220.group5.project.old_classes;

import fr.ecp.IS1220.group5.project.MyFoodoraSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * to set all the dashboards with the same format
 * User dashboard is created at the loginUser() in the class MyFoodoraSystemGUI
 *
 * @see MyFoodoraSystem#loginUser(String, String)
 *
 * Created by dennis101251 on 2016/12/19.
 */
public class Dashboard extends JFrame implements ActionListener{

    public Dashboard(){
        setSize(380, 250);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
