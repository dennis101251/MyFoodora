package fr.ecp.IS1220.group5.project.GUI.register;

import fr.ecp.IS1220.group5.project.util.Coordinate;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexandre_carlier on 19/12/2016.
 */
public class CustomerRegisterPanel extends RegisterPanel{

    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;
    private JTextField addressXTextField;
    private JTextField addressYTextField;
    private JTextField mailTextField;
    private JTextField phoneTextField;

    public CustomerRegisterPanel(){

        this.setLayout(new GridLayout(7, 1));

        JPanel firstNamePanel = new JPanel();
        firstNamePanel.setLayout(new BoxLayout(firstNamePanel,BoxLayout.X_AXIS));
        JLabel firstNameLabel = new JLabel("First name:");
        firstNameTextField = new JTextField(30);
        firstNameTextField.setMaximumSize(firstNameTextField.getPreferredSize());
        firstNamePanel.add(firstNameLabel);
        firstNamePanel.add(Box.createHorizontalGlue());
        firstNamePanel.add(firstNameTextField);
        this.add(firstNamePanel);

        JPanel lastNamePanel = new JPanel();
        lastNamePanel.setLayout(new BoxLayout(lastNamePanel,BoxLayout.X_AXIS));
        JLabel lastNameLabel = new JLabel("Last name:");
        lastNameTextField = new JTextField(30);
        lastNameTextField.setMaximumSize(lastNameTextField.getPreferredSize());
        lastNamePanel.add(lastNameLabel);
        lastNamePanel.add(Box.createHorizontalGlue());
        lastNamePanel.add(lastNameTextField);
        this.add(lastNamePanel);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel,BoxLayout.X_AXIS));
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(30);
        usernameTextField.setMaximumSize(usernameTextField.getPreferredSize());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createHorizontalGlue());
        usernamePanel.add(usernameTextField);
        this.add(usernamePanel);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel,BoxLayout.X_AXIS));
        JLabel passwordLabel = new JLabel("Password:");
        passwordTextField = new JPasswordField(30);
        passwordTextField.setMaximumSize(passwordTextField.getPreferredSize());
        passwordPanel.add(passwordLabel);
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(passwordTextField);
        this.add(passwordPanel);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BoxLayout(addressPanel,BoxLayout.X_AXIS));
        JLabel addressLabel = new JLabel("Address:");
        JLabel xLabel = new JLabel("X:");
        addressXTextField = new JTextField(5);
        addressXTextField.setMaximumSize(addressXTextField.getPreferredSize());
        JLabel yLabel = new JLabel("Y:");
        addressYTextField = new JTextField(5);
        addressYTextField.setMaximumSize(addressYTextField.getPreferredSize());
        addressPanel.add(addressLabel);
        addressPanel.add(Box.createHorizontalGlue());
        addressPanel.add(xLabel);
        addressPanel.add(Box.createHorizontalGlue());
        addressPanel.add(addressXTextField);
        addressPanel.add(Box.createHorizontalGlue());
        addressPanel.add(yLabel);
        addressPanel.add(Box.createHorizontalGlue());
        addressPanel.add(addressYTextField);
        this.add(addressPanel);

        JPanel mailPanel = new JPanel();
        mailPanel.setLayout(new BoxLayout(mailPanel,BoxLayout.X_AXIS));
        JLabel mailLabel = new JLabel("Email:");
        mailTextField = new JTextField(30);
        mailTextField.setMaximumSize(mailTextField.getPreferredSize());
        mailPanel.add(mailLabel);
        mailPanel.add(Box.createHorizontalGlue());
        mailPanel.add(mailTextField);
        this.add(mailPanel);

        JPanel phonePanel = new JPanel();
        phonePanel.setLayout(new BoxLayout(phonePanel,BoxLayout.X_AXIS));
        JLabel phoneLabel = new JLabel("Phone:");
        phoneTextField = new JTextField(30);
        phoneTextField.setMaximumSize(phoneTextField.getPreferredSize());
        phonePanel.add(phoneLabel);
        phonePanel.add(Box.createHorizontalGlue());
        phonePanel.add(phoneTextField);
        this.add(phonePanel);

    }

    public String getFirstName(){
        return firstNameTextField.getText();
    }

    public String getLastName(){
        return lastNameTextField.getText();
    }

    public String getUserame(){
        return usernameTextField.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public Coordinate getAddress(){
        return new Coordinate(Double.parseDouble(addressXTextField.getText()), Double.parseDouble(addressYTextField.getText()));
    }

    public String getMail(){
        return mailTextField.getText();
    }

    public String getPhone(){
        return phoneTextField.getText();
    }


    public String getUserType() {
        return "Customer";
    }
}
