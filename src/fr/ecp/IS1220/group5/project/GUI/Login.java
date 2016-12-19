package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dennis101251 on 2016/12/19.
 */
public class Login extends JFrame implements ActionListener{
    /**
     * get the only one system
     *
     * the problem is that I didn't realize this before, and we can't change the constructor in to private
     */
    MyFoodoraSystemGUI myFoodoraSystemGUI;

    /**
     * we have 4 rows of info at the login frame
     */
    JPanel[] row = new JPanel[4];
    /**
     * we have two buttons of login and register
     */
    JButton[] buttons = new JButton[2];
    /**
     * two text fields for username and password
     */
    JTextField[] textFields = new JTextField[2];
    /**
     * Label of "Welcome"
     */
    JLabel welcomeLabel = new JLabel();

    Dimension buttonDimension = new Dimension(100,35);
    Dimension labelDimension = new Dimension(80, 35);
    Dimension textFieldDimension = new Dimension(120,35);
    Dimension welcomDimension = new Dimension(250,60);


    Font welcomeFont = new Font("Gill Sans", Font.BOLD,20);
    Font normalFont = new Font("Helvetica",Font.PLAIN,14);


    public Login(){
        super("Login");

        // set the size of the window
        setSize(380, 250);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        GridLayout grid = new GridLayout(4,1);
        setLayout(grid);

        for(int i = 0; i < 4; i++)
            row[i] = new JPanel();

        welcomeLabel = new JLabel("Welcome to MyFoodora");
        welcomeLabel.setPreferredSize(welcomDimension);
        welcomeLabel.setFont(welcomeFont);
        row[0].add(welcomeLabel);
        add(row[0]);

        JLabel username = new JLabel("Username");
        username.setPreferredSize(labelDimension);
        username.setFont(normalFont);
        textFields[0] = new JTextField();
//        textFields[0].setPreferredSize(textFieldDimension);
        row[1].setLayout(new BoxLayout(row[1],BoxLayout.X_AXIS));
        row[1].add(username);
        row[1].add(textFields[0]);
        add(row[1]);

        JLabel password = new JLabel("Password");
        username.setPreferredSize(labelDimension);
        username.setFont(normalFont);
        textFields[1] = new JTextField();
//        textFields[1].setPreferredSize(textFieldDimension);
        row[2].setLayout(new BoxLayout(row[2],BoxLayout.X_AXIS));
        row[2].add(password);
        row[2].add(textFields[1]);
        add(row[2]);


        buttons[0] = new JButton("Login");
        buttons[1] = new JButton("Register");
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addActionListener(this);
            buttons[i].setPreferredSize(buttonDimension);
        }
        //Set the layout of the two buttons
        BoxLayout b1 = new BoxLayout(row[1],BoxLayout.Y_AXIS);
        row[3].setLayout(b1);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttons[0]);
        buttonPanel.add(Box.createHorizontalGlue ());
        buttonPanel.add(buttons[1]);
        BoxLayout b2 = new BoxLayout(buttonPanel,BoxLayout.X_AXIS);
        buttonPanel.setLayout(b2);
//        row[3].add(Box.createVerticalStrut (10));
        row[3]= buttonPanel;
//        row[3].add(Box.createVerticalStrut (10));
        add(row[3]);

        myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();

        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttons[0]){
            String username = textFields[0].getText();
            String password = textFields[1].getText();
            System.out.println(username);
            System.out.println(password);
            myFoodoraSystemGUI.loginUser(username,password);
        }
        else if (e.getSource() == buttons[1]){
            System.out.println("register");
        }
    }

    public static void main(String[] args) {
//        myFoodoraSystem.registerCustomer("Marco", "Merlotti", "MM", "501", new Coordinate(50,50), "marco.merlotti@student.ecp.fr", "0611041568");
//        myFoodoraSystem.loginUser("MM", "501");
        Login login = new Login();
    }
}
