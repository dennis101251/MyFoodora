package fr.ecp.IS1220.group5.project.GUI.register;

import fr.ecp.IS1220.group5.project.util.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by alexandre_carlier on 19/12/2016.
 */
public class RestaurantRegisterPanel extends RegisterPanel{

    private JTextField nameTextField;
    private JTextField usernameTextField;
    private JTextField addressXTextField;
    private JTextField addressYTextField;
    private JPasswordField passwordTextField;

    public RestaurantRegisterPanel(){

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

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel,BoxLayout.X_AXIS));
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(30);
        usernameTextField.setMaximumSize(usernameTextField.getPreferredSize());
        usernamePanel.add(usernameLabel);
        usernamePanel.add(Box.createHorizontalGlue());
        usernamePanel.add(usernameTextField);
        this.add(usernamePanel);

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
        addressXTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){

                }else{
                    e.consume();
                }
            }
        });
        addressYTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)){

                }else{
                    e.consume();
                }
            }
        });
        this.add(addressPanel);

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
    public String getUserame(){
        return usernameTextField.getText();
    }

    public String getPassword(){
        return passwordTextField.getText();
    }

    public Coordinate getAddress(){
        if (addressXTextField.getText().isEmpty()||addressYTextField.getText().isEmpty()){
            return null;
        }
        else {
            return new Coordinate(Double.parseDouble(addressXTextField.getText()), Double.parseDouble(addressYTextField.getText()));
        }
    }


    public String getUserType() {
        return "Restaurant";
    }
}
