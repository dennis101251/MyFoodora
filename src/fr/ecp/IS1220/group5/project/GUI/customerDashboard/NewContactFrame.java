package fr.ecp.IS1220.group5.project.GUI.customerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dennis101251 on 2017/1/1.
 */
public class NewContactFrame extends JFrame implements WindowListener{

    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    private JFrame self = this;

    private static NewContactFrame newContactFrame = null;

    JPanel mainPanel;
    JLabel infoLabel;
    JLabel phoneLabel;
    JTextField phoneField;
    JLabel emailLabel;
    JTextField emailField;
    JButton confirm;

    private InfoBoardFrame infoBoardFrame;

    public static NewContactFrame getInstance(){
        if (newContactFrame == null){
            newContactFrame = new NewContactFrame();
            return newContactFrame;
        }
        else {
            return newContactFrame;
        }
    }

    private NewContactFrame(){
        super();

        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("Contact");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        this.setContentPane(mainPanel);

        infoLabel = new JLabel("type the new contact information");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(3,0,10,0));
        mainPanel.add(infoLabel,new GBC(0,0).setFill(GridBagConstraints.BOTH));

        phoneLabel = new JLabel("Phone: ");
        mainPanel.add(phoneLabel, new GBC(0,1).setFill(GridBagConstraints.BOTH));

        phoneField = new JTextField("",20);
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){


                }else{
                    e.consume();
                }

            }
        });
        mainPanel.add(phoneField, new GBC(0,2));

        emailLabel = new JLabel("Email: ");
        emailLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        mainPanel.add(emailLabel, new GBC(0,3).setFill(GridBagConstraints.BOTH));

        emailField = new JTextField("", 20);
        mainPanel.add(emailField,new GBC(0,4));

        confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                String email = emailField.getText();
                if (!phone.equalsIgnoreCase("")){
                    if (!email.equalsIgnoreCase("")){
                        Customer customer = (Customer) myFoodoraSystem.getCurrentUser();
                        customer.infoBoard.setEmail(email);
                        customer.infoBoard.setPhone(phone);
                        try {
                            myFoodoraSystem.updateUser(customer);
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        notifyObserver();
//                        System.out.println("Phone: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getPhone());
                        newContactFrame = null;
                        self.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(),"No email","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(),"No phone","Message",JOptionPane.WARNING_MESSAGE);
                }

            }
        });
//        confirm.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        mainPanel.add(confirm, new GBC(0,5));

        this.pack();
        setVisible(true);

        this.setLocationRelativeTo(null);

        this.addWindowListener(this);

    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("MM","123456");
        new NewContactFrame();
    }

    public void notifyObserver(){
        infoBoardFrame.updateInfo();
    }

    public void addObserver(InfoBoardFrame infoBoardFrame){
        this.infoBoardFrame = infoBoardFrame;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        newContactFrame = null;
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
