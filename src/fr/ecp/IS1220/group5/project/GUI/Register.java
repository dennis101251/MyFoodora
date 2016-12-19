package fr.ecp.IS1220.group5.project.GUI;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class Register {

    private int choice;
//    private JOptionPane jOptionPane;
    private JFrame jFrame = new JFrame();

    public Register(){
        jFrame = new JFrame("Register");
//        jOptionPane = new JOptionPane();
        choice = JOptionPane.showOptionDialog(jFrame,"Message", "Title", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,null,null);
//        System.out.println(input);
    }


    public int getChoice(){
        return choice;
    }

    public static void main(String[] args) {
        Register register =  new Register();
        System.out.println(register.getChoice());
    }
}
