package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class Register extends JFrame{

//    private int choice;
//    private JOptionPane jOptionPane;
    private JFrame[] jFrame = new JFrame[4];
    private JLabel name;

    public Register(){

        Object input = JOptionPane.showInputDialog(new JFrame("register"),"Which kind of user you you want to choose?","register", JOptionPane.QUESTION_MESSAGE,null,
                new String[]{"Customer","Restaurant","Manager","Courier"},"Customer");

        setSize(380, 250);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        name = new JLabel((String) input);
        add(name);

        setVisible(true);
    }



    public static void main(String[] args) {
        Register register =  new Register();
    }
}
