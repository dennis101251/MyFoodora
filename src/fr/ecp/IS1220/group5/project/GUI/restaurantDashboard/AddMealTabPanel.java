package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.GUI.RestaurantDashboard;
import fr.ecp.IS1220.group5.project.MyFoodoraSystem;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.menu.*;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * Created by alexandre_carlier on 27/12/2016.
 */
public class AddMealTabPanel extends JPanel {

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    RestaurantDashboard restaurantDashboard;

    JTextField nameTextField;

    JRadioButton halfMealButton;
    JRadioButton fullMealButton;

    JPanel choicePanel;
    JRadioButton starterAndMainDishButton;
    JRadioButton mainDishAndDessertButton;

    JRadioButton standardButton;
    JRadioButton vegetarianButton;
    JRadioButton glutenFreeButton;

    JPanel starterPanel;
    JPanel mainDishPanel;
    JPanel dessertPanel;

    String[] starterStrings;
    String[] mainDishStrings;
    String[] dessertStrings;

    JComboBox<String> startersComboBox;
    JComboBox<String> mainDishesComboBox;
    JComboBox<String> dessertsComboBox;


    public AddMealTabPanel(RestaurantDashboard restaurantDashboard){
        this.restaurantDashboard = restaurantDashboard;
        this.setLayout(new GridLayout(8, 1));

        //Name
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel,BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(15);
        nameTextField.setMaximumSize(nameTextField.getPreferredSize());

        namePanel.add(nameLabel);
        namePanel.add(Box.createHorizontalGlue());
        namePanel.add(nameTextField);

        this.add(namePanel);


        //Category
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel,BoxLayout.X_AXIS));
        JLabel categoryLabel = new JLabel("Category:");

        JPanel categoryRadioPanel = new JPanel();

        halfMealButton = new JRadioButton("Half Meal");
        halfMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                halfMeal();
            }
        });

        fullMealButton = new JRadioButton("Full Meal");
        fullMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fullMeal();
            }
        });
        fullMealButton.setSelected(true);


        ButtonGroup categoryGroup = new ButtonGroup();
        categoryGroup.add(halfMealButton);
        categoryGroup.add(fullMealButton);

        categoryRadioPanel.add(halfMealButton);
        categoryRadioPanel.add(fullMealButton);

        categoryPanel.add(categoryLabel);
        categoryPanel.add(Box.createHorizontalGlue());
        categoryPanel.add(categoryRadioPanel);
        this.add(categoryPanel);

        //Choice: Starter and Main dish OR Main Dish and Dessert
        choicePanel = new JPanel();
        choicePanel.setVisible(false);
        choicePanel.setLayout(new BoxLayout(choicePanel,BoxLayout.X_AXIS));
        JLabel choiceLabel = new JLabel("Choice:");

        JPanel choiceRadioPanel = new JPanel();

        starterAndMainDishButton = new JRadioButton("Starter + Main Dish");
        starterAndMainDishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                starterAndMainDish();
            }
        });
        starterAndMainDishButton.setSelected(true);

        mainDishAndDessertButton = new JRadioButton("Main Dish + Dessert");
        mainDishAndDessertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainDishAndDessert();
            }
        });

        ButtonGroup choiceGroup = new ButtonGroup();
        choiceGroup.add(starterAndMainDishButton);
        choiceGroup.add(mainDishAndDessertButton);

        choiceRadioPanel.add(starterAndMainDishButton);
        choiceRadioPanel.add(mainDishAndDessertButton);

        choicePanel.add(choiceLabel);
        choicePanel.add(Box.createHorizontalGlue());
        choicePanel.add(choiceRadioPanel);
        this.add(choicePanel);

        //Type
        JPanel typePanel = new JPanel();
        typePanel.setLayout(new BoxLayout(typePanel,BoxLayout.X_AXIS));
        JLabel typeLabel = new JLabel("Type:");

        JPanel typeRadioPanel = new JPanel();

        standardButton = new JRadioButton("Standard");
        standardButton.setSelected(true);

        vegetarianButton = new JRadioButton("Vegetarian");

        glutenFreeButton  = new JRadioButton("Gluten Free");

        ButtonGroup typeGroup = new ButtonGroup();
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


        //Starter
        starterStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.Starter);

        starterPanel = new JPanel();
        starterPanel.setLayout(new BoxLayout(starterPanel,BoxLayout.X_AXIS));

        starterPanel.add(new JLabel("Starter"));

        startersComboBox = new JComboBox<>(starterStrings);
        starterPanel.add(startersComboBox);

        this.add(starterPanel);

        //Main Dish
        mainDishStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.MainDish);

        mainDishPanel = new JPanel();
        mainDishPanel.setLayout(new BoxLayout(mainDishPanel,BoxLayout.X_AXIS));

        mainDishPanel.add(new JLabel("Main Dish"));

        mainDishesComboBox = new JComboBox<>(mainDishStrings);
        mainDishPanel.add(mainDishesComboBox);

        this.add(mainDishPanel);

        //Dessert
        dessertStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.Dessert);

        dessertPanel = new JPanel();
        dessertPanel.setLayout(new BoxLayout(dessertPanel,BoxLayout.X_AXIS));

        dessertPanel.add(new JLabel("Dessert"));

        dessertsComboBox =  new JComboBox<>(dessertStrings);
        dessertPanel.add(dessertsComboBox);

        this.add(dessertPanel);


        //Create
        JPanel createPanel = new JPanel();
        JButton createButton = new JButton("Create");
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createMeal();
            }
        });
        createPanel.add(createButton);
        this.add(createPanel);

    }

    private void starterAndMainDish() {

        starterPanel.setVisible(true);
        dessertPanel.setVisible(false);

    }

    private void mainDishAndDessert() {

        starterPanel.setVisible(false);
        dessertPanel.setVisible(true);


    }

    private void halfMeal() {

        choicePanel.setVisible(true);

        if (starterAndMainDishButton.isSelected()){
            starterAndMainDish();
        } else { //mainDishAndDessertButton.isSelected()
            mainDishAndDessert();
        }


    }

    private void fullMeal() {

        choicePanel.setVisible(false);
        starterPanel.setVisible(true);
        dessertPanel.setVisible(true);

    }

    private void createMeal() {

        String mealName = nameTextField.getText();
        MealCategory mealCategory;
            if (halfMealButton.isSelected()){
                mealCategory = MealCategory.HalfMeals;
            } else{
                mealCategory = MealCategory.FullMeals;
            }

        MealType mealType;
            if (standardButton.isSelected()){
                mealType = MealType.Standard;
            } else if (vegetarianButton.isSelected()){
                mealType = MealType.Vegetarian;
            } else {
                mealType = MealType.GlutenFree;
            }

        String starter = (startersComboBox.getSelectedIndex() >= 0) ? starterStrings[startersComboBox.getSelectedIndex()] : null;
        String mainDish = (mainDishesComboBox.getSelectedIndex() >= 0) ? mainDishStrings[mainDishesComboBox.getSelectedIndex()] : null;
        String dessert = (dessertsComboBox.getSelectedIndex()>= 0) ? dessertStrings[dessertsComboBox.getSelectedIndex()]: null;

        if (mealCategory == MealCategory.FullMeals){
            if ((starter == null) || (mainDish == null) || (dessert == null)){
                System.out.println("Error: You must choose a starter, a main dish and a dessert.");
            } else {
                myFoodoraSystem.createMeal(mealName, mealCategory, mealType);
                myFoodoraSystem.addDish2Meal(starter, mealName);
                myFoodoraSystem.addDish2Meal(mainDish, mealName);
                myFoodoraSystem.addDish2Meal(dessert, mealName);
                success();
            }
        } else { //Meal category = half meal
            if (starterAndMainDishButton.isSelected()){

                if ((starter == null) || (mainDish == null)){
                    System.out.println("Error: You must choose a starter and a main dish.");
                } else {
                    myFoodoraSystem.createMeal(mealName, mealCategory, mealType);
                    myFoodoraSystem.addDish2Meal(starter, mealName);
                    myFoodoraSystem.addDish2Meal(mainDish, mealName);
                    success();


                }

            } else { // mainDishAndDessertButton.isSelected()

                if ((mainDish == null) || (dessert == null)){
                    System.out.println("Error: You must choose a main dish and a dessert.");
                } else {
                    myFoodoraSystem.createMeal(mealName, mealCategory, mealType);
                    myFoodoraSystem.addDish2Meal(mainDish, mealName);
                    myFoodoraSystem.addDish2Meal(dessert, mealName);
                    success();
                }

            }
        }


    }

    public void success(){

        myFoodoraSystem.saveMenu();
        restaurantDashboard.update();
        JOptionPane.showMessageDialog(new JFrame(), "This meal was successfully created!","Success",JOptionPane.INFORMATION_MESSAGE);
        nameTextField.setText("");
        fullMealButton.setSelected(true);
        starterAndMainDishButton.setSelected(true);
        standardButton.setSelected(true);
        fullMeal();

    }

    public void update() {

        starterStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.Starter);
        mainDishStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.MainDish);
        dessertStrings = myFoodoraSystem.getItemStringsOfCategory(ItemCategory.Dessert);

        startersComboBox.setModel(new DefaultComboBoxModel<>(starterStrings));
        mainDishesComboBox.setModel(new DefaultComboBoxModel<>(mainDishStrings));
        dessertsComboBox.setModel(new DefaultComboBoxModel<>(dessertStrings));

    }

}
