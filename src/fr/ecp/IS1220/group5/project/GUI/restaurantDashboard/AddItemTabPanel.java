package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.DuplicateNameException;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.menu.FoodType;
import fr.ecp.IS1220.group5.project.menu.ItemCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * Created by alexandre_carlier on 27/12/2016.
 */
public class AddItemTabPanel extends JPanel implements ActionListener {

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    RestaurantDashboard restaurantDashboard;
    JTextField nameTextField;
    JTextField priceTextField;
    ButtonGroup categoryGroup;
        JRadioButton starterButton;
        JRadioButton mainDishButton;
        JRadioButton dessertButton;

    ButtonGroup typeGroup;
        JRadioButton standardButton;
        JRadioButton vegetarianButton;
        JRadioButton glutenFreeButton;

    public AddItemTabPanel(RestaurantDashboard restaurantDashboard){
        this.restaurantDashboard = restaurantDashboard;
        this.setLayout(new GridLayout(5, 1));

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(15);
        nameTextField.setMaximumSize(nameTextField.getPreferredSize());

        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameTextField);

        this.add(namePanel);


        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel,BoxLayout.X_AXIS));
        JLabel priceLabel = new JLabel("Price:");
        priceTextField = new JTextField(15);
        priceTextField.setMaximumSize(priceTextField.getPreferredSize());

        pricePanel.add(priceLabel);
        pricePanel.add(Box.createHorizontalGlue());
        pricePanel.add(priceTextField);

        this.add(pricePanel);


        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel,BoxLayout.X_AXIS));
        JLabel categoryLabel = new JLabel("Category:");

        JPanel categoryRadioPanel = new JPanel();

        starterButton = new JRadioButton("Starter");
        starterButton.addActionListener(this);

        mainDishButton = new JRadioButton("Main Dish");
        mainDishButton.addActionListener(this);
        mainDishButton.setSelected(true);

        dessertButton  = new JRadioButton("Dessert");
        dessertButton.addActionListener(this);

        categoryGroup = new ButtonGroup();
        categoryGroup.add(starterButton);
        categoryGroup.add(mainDishButton);
        categoryGroup.add(dessertButton);

        categoryRadioPanel.add(starterButton);
        categoryRadioPanel.add(mainDishButton);
        categoryRadioPanel.add(dessertButton);

        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createHorizontalGlue());
        categoryPanel.add(categoryRadioPanel);
        this.add(categoryPanel);



        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel,BoxLayout.X_AXIS));
        JLabel typeLabel = new JLabel("Type:");

        JPanel typeRadioPanel = new JPanel();

        standardButton = new JRadioButton("Standard");
        standardButton.addActionListener(this);
        standardButton.setSelected(true);

        vegetarianButton = new JRadioButton("Vegetarian");
        vegetarianButton.addActionListener(this);

        glutenFreeButton  = new JRadioButton("Gluten Free");
        glutenFreeButton.addActionListener(this);

        typeGroup = new ButtonGroup();
        typeGroup.add(standardButton);
        typeGroup.add(vegetarianButton);
        typeGroup.add(glutenFreeButton);

        typeRadioPanel.add(standardButton);
        typeRadioPanel.add(vegetarianButton);
        typeRadioPanel.add(glutenFreeButton);

        typePanel.add(typeLabel);
        typePanel.add(Box.createHorizontalGlue());
        typePanel.add(typeRadioPanel);
        this.add(typePanel);

        JPanel createPanel = new JPanel();
        JButton createButton = new JButton("Create Item");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                addItem();

            }
        });
        createPanel.add(createButton);
        this.add(createPanel);

    }

    private void addItem() {

        ItemCategory itemCategory;
            if (starterButton.isSelected()){
                itemCategory = ItemCategory.Starter;
            } else if (mainDishButton.isSelected()) {
                itemCategory = ItemCategory.MainDish;
            } else { //dessertButton.isSelected()
                itemCategory = ItemCategory.Dessert;
            }


        FoodType foodType;
            if (standardButton.isSelected()){
                foodType = FoodType.Standard;
            } else if (vegetarianButton.isSelected()){
                foodType = FoodType.Vegetarian;
            } else { //glutenFreeButton.isSelected()
                foodType = FoodType.GlutenFree;
            }

        try {

            BigDecimal price = new BigDecimal(priceTextField.getText());

            myFoodoraSystem.createItemGUI(nameTextField.getText(), price, itemCategory, foodType);
            myFoodoraSystem.saveMenu();
            restaurantDashboard.update();
            nameTextField.setText("");
            priceTextField.setText("");
            categoryGroup.clearSelection();
            mainDishButton.setSelected(true);
            typeGroup.clearSelection();
            standardButton.setSelected(true);
            JOptionPane.showMessageDialog(new JFrame(), "This item was successfully created!","Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (EmptyNameException e) {
            JOptionPane.showMessageDialog(new JFrame(),"The item's name must not be empty.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (DuplicateNameException e) {
            JOptionPane.showMessageDialog(new JFrame(),"This item's name already exists.","Error", JOptionPane.ERROR_MESSAGE);
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(new JFrame(),"Invalid price format \nIt must not be empty and contain only digits and a dot.","Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
