package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by dennis101251 on 2017/1/1.
 */
public class MessageFrame extends JFrame implements WindowListener{

    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    private JFrame self = this;

    private static MessageFrame messageFrame = null;

    JPanel mainPanel;
    JLabel nameLabel;
    JLabel titleLabel;
    JTextField titleField;
    JLabel messageLabel;
    JTextArea messageArea;
    JButton confirm;
    JButton reset;

    public static MessageFrame getInstance(){
        if (messageFrame == null){
            messageFrame = new MessageFrame();
            return messageFrame;
        }
        else {
            return messageFrame;
        }
    }

    private MessageFrame(){
        super();

        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("Message");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        this.setContentPane(mainPanel);

        nameLabel = new JLabel("Send message to all customers");
        nameLabel.setFont(new Font("Helvetica",Font.PLAIN,14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(3,0,5,0));
        mainPanel.add(nameLabel,new GBC(0,0).setFill(GridBagConstraints.BOTH));

        titleLabel = new JLabel("Title:");
        mainPanel.add(titleLabel,new GBC(0,1).setFill(GridBagConstraints.BOTH));

        titleField = new JTextField("",20);
        mainPanel.add(titleField,new GBC(0,2));

        messageLabel = new JLabel("Message:");
        messageLabel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
        mainPanel.add(messageLabel,new GBC(0,3).setFill(GridBagConstraints.BOTH));

        messageArea = new JTextArea("",8,20);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        mainPanel.add(messageArea,new GBC(0,4));

        JPanel button = new JPanel(new GridBagLayout());
        confirm = new JButton("Confirm");
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleField.getText();
                String message= messageArea.getText();
                if (!title.equalsIgnoreCase("")){
                    if (!message.equalsIgnoreCase("")){
                        myFoodoraSystem.sendMessage(title,message);
                        self.dispose();
                    }
                    else {
                        JOptionPane.showMessageDialog(new JFrame(),"No message","Message",JOptionPane.WARNING_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(new JFrame(),"No title","Message",JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageArea.setText("");
                titleField.setText("");
            }
        });
        button.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        button.add(confirm,new GBC(0,0).setFill(GridBagConstraints.BOTH));
        button.add(reset,new GBC(1,0).setFill(GridBagConstraints.BOTH));
        mainPanel.add(button,new GBC(0,5));

        this.pack();
        setVisible(true);

        this.setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("kfc","123456");
        new MessageFrame();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        messageFrame = null;
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
