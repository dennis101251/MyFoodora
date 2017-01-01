package fr.ecp.IS1220.group5.project.GUI.courierDashboard;

import fr.ecp.IS1220.group5.project.GUI.CourierDashboard;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by dennis101251 on 2017/1/1.
 */
public class NewPhoneFrame extends JFrame implements WindowListener{

    private static NewPhoneFrame newPhoneFrame = null;
    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    JPanel mainPanel;
    JLabel infoLabel;
    JLabel phoneLabel;
    JTextField phoneField;
    JButton confirm;

    CourierDashboard courierDashboard;

    public static NewPhoneFrame getInstance(){
        if (newPhoneFrame == null){
            newPhoneFrame = new NewPhoneFrame();
            return newPhoneFrame;
        }
        else {
            return newPhoneFrame;
        }
    }

    private NewPhoneFrame(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("Contact");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        this.setContentPane(mainPanel);

        infoLabel = new JLabel("type the new phone number");
        infoLabel.setBorder(BorderFactory.createEmptyBorder(3,0,10,0));
        mainPanel.add(infoLabel,new GBC(0,0).setFill(GridBagConstraints.BOTH));

        phoneLabel = new JLabel("Phone: ");
        mainPanel.add(phoneLabel, new GBC(0,1).setFill(GridBagConstraints.BOTH));

        phoneField = new JTextField("",13);
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

        confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String phone = phoneField.getText();
                if (!phone.equalsIgnoreCase("")){
                    Courier courier = (Courier) myFoodoraSystem.getCurrentUser();
                    courier.setPhoneNumber(phone);
                    try {
                        myFoodoraSystem.updateUser(courier);
                    } catch (UserNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    notifyObserver();
                    newPhoneFrame = null;
                    NewPhoneFrame.this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(),"No phone","Message",JOptionPane.WARNING_MESSAGE);
                }

            }
        });

        mainPanel.add(confirm, new GBC(0,3));

        this.pack();
        setVisible(true);
        this.setLocationRelativeTo(null);

        this.addWindowListener(this);
    }

    public void notifyObserver(){
        courierDashboard.updateInfo();
    }

    public void addObserver(CourierDashboard courierDashboard){
        this.courierDashboard = courierDashboard;
    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        newPhoneFrame = null;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
