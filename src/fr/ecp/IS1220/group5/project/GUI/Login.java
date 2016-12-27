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
     */
    MyFoodoraSystemGUI myFoodoraSystemGUI;

    /**
     * we have 4 rows of info at the login frame
     */
    JPanel[] row = new JPanel[4];
    /**
     * we have two buttons of login and register
     */
    JButton loginButton;
    JButton registerButton;
    /**
     * two fields for username and password
     */
    JTextField usernameTextField;
    JPasswordField passwordField;
    /**
     * Label of "Welcome"
     */
    JLabel welcomeLabel;

    Dimension buttonDimension = new Dimension(100,35);
    Dimension labelDimension = new Dimension(80, 35);
    Dimension textFieldDimension = new Dimension(120,35);
    Dimension welcomeDimension = new Dimension(250,60);


    Font welcomeFont = new Font("Gill Sans", Font.BOLD,20);
    Font normalFont = new Font("Helvetica",Font.PLAIN,14);


    public Login(){
        super("Login");

        // set the size of the window
        setSize(400, 280);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        GridLayout grid = new GridLayout(4,1);
        panel.setLayout(grid);
        this.setContentPane(panel);


        for(int i = 0; i < 4; i++)
            row[i] = new JPanel();

        //Welcome label
        welcomeLabel = new JLabel("Welcome to MyFoodora");
        welcomeLabel.setPreferredSize(welcomeDimension);
        welcomeLabel.setFont(welcomeFont);
        row[0].add(welcomeLabel);
        add(row[0]);


        //Enter Username
        JLabel username = new JLabel("Username:");
        username.setPreferredSize(labelDimension);
        username.setFont(normalFont);
        usernameTextField = new JTextField(20);
        usernameTextField.setMaximumSize(usernameTextField.getPreferredSize());
        row[1].setLayout(new BoxLayout(row[1],BoxLayout.X_AXIS));
        row[1].add(username);
        row[1].add(Box.createHorizontalGlue());
        row[1].add(usernameTextField);
        add(row[1]);

        //Enter Password
        JLabel password = new JLabel("Password:");
        username.setPreferredSize(labelDimension);
        username.setFont(normalFont);
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        row[2].setLayout(new BoxLayout(row[2],BoxLayout.X_AXIS));
        row[2].add(password);
        row[2].add(Box.createHorizontalGlue());
        row[2].add(passwordField);
        add(row[2]);


        //Buttons
        registerButton = new JButton("Don't have an acccount? Register →");
        registerButton.addActionListener(this);

        loginButton = new JButton("Login");
        loginButton.addActionListener(this);
        loginButton.setPreferredSize(buttonDimension);

        //Set the layout of the two buttons
        BoxLayout b1 = new BoxLayout(row[1],BoxLayout.Y_AXIS);
        row[3].setLayout(b1);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(loginButton);
        BoxLayout b2 = new BoxLayout(buttonPanel,BoxLayout.X_AXIS);
        buttonPanel.setLayout(b2);
//        row[3].add(Box.createVerticalStrut (10));
        row[3]= buttonPanel;
//        row[3].add(Box.createVerticalStrut (10));
        add(row[3]);

        myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();

        this.setLocationRelativeTo(null);
        setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton){
            String username = usernameTextField.getText();
            String password = passwordField.getText();
            System.out.println(username);
            System.out.println(password);
            myFoodoraSystemGUI.loginUser(username,password, Login.this);
        }
        else if (e.getSource() == registerButton){
            System.out.println("register");
            Login.this.dispose();
            Register register = new Register();
//            Register2 register = new Register2();
        }
    }

    public static void main(String[] args) {
//        myFoodoraSystem.registerCustomer("Marco", "Merlotti", "MM", "501", new Coordinate(50,50), "marco.merlotti@student.ecp.fr", "0611041568");
//        myFoodoraSystem.loginUser("MM", "501");
        Login login = new Login();
    }
}
