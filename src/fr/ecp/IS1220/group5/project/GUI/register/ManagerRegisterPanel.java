package fr.ecp.IS1220.group5.project.GUI.register;

import javax.swing.*;
import java.awt.*;

/**
 * Created by alexandre_carlier on 19/12/2016.
 */
public class ManagerRegisterPanel extends RegisterPanel{

    private JTextField nameTextField;
    private JTextField lastNameTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;

    public ManagerRegisterPanel(){

        this.setLayout(new GridLayout(4, 1));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(30);
        nameTextField.setMaximumSize(nameTextField.getPreferredSize());
        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameTextField);
        this.add(namePanel);

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

    }


    public String getName(){
        return nameTextField.getText();
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

    public String getUserType() {
        return "Manager";
    }
}
