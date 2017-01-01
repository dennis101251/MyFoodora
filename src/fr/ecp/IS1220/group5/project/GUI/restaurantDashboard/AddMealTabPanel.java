package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.DuplicateNameException;
import fr.ecp.IS1220.group5.project.exception.EmptyNameException;
import fr.ecp.IS1220.group5.project.exception.IncompatibleFoodTypeException;
import fr.ecp.IS1220.group5.project.exception.TooManyItemsException;
import fr.ecp.IS1220.group5.project.menu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    private FoodType foodType = FoodType.Standard;
    JRadioButton standardButton;
    JRadioButton vegetarianButton;
    JRadioButton glutenFreeButton;

    JPanel starterPanel;
    JPanel mainDishPanel;
    JPanel dessertPanel;

    String[] starters;
    String[] mainDishes;
    String[] desserts;

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
        standardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMealTabPanel.this.foodType = FoodType.Standard;
                update();
            }
        });
        standardButton.setSelected(true);

        vegetarianButton = new JRadioButton("Vegetarian");
        vegetarianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMealTabPanel.this.foodType = FoodType.Vegetarian;
                update();
            }
        });

        glutenFreeButton  = new JRadioButton("Gluten Free");
        glutenFreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddMealTabPanel.this.foodType = FoodType.GlutenFree;
                update();
            }
        });

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
        starters = myFoodoraSystem.getItems(ItemCategory.Starter, foodType);

        starterPanel = new JPanel();
        starterPanel.setLayout(new BoxLayout(starterPanel,BoxLayout.X_AXIS));

        starterPanel.add(new JLabel("Starter"));

        startersComboBox = new JComboBox<>(starters);
        starterPanel.add(startersComboBox);

        this.add(starterPanel);

        //Main Dish
        mainDishes = myFoodoraSystem.getItems(ItemCategory.MainDish, foodType);

        mainDishPanel = new JPanel();
        mainDishPanel.setLayout(new BoxLayout(mainDishPanel,BoxLayout.X_AXIS));

        mainDishPanel.add(new JLabel("Main Dish"));

        mainDishesComboBox = new JComboBox<>(mainDishes);
        mainDishPanel.add(mainDishesComboBox);

        this.add(mainDishPanel);

        //Dessert
        desserts = myFoodoraSystem.getItems(ItemCategory.Dessert, foodType);

        dessertPanel = new JPanel();
        dessertPanel.setLayout(new BoxLayout(dessertPanel,BoxLayout.X_AXIS));

        dessertPanel.add(new JLabel("Dessert"));

        dessertsComboBox =  new JComboBox<>(desserts);
        dessertPanel.add(dessertsComboBox);

        this.add(dessertPanel);


        //Create
        JPanel createPanel = new JPanel();
        JButton createButton = new JButton("Create Meal");
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

        FoodType foodType;
            if (standardButton.isSelected()){
                foodType = FoodType.Standard;
            } else if (vegetarianButton.isSelected()){
                foodType = FoodType.Vegetarian;
            } else {
                foodType = FoodType.GlutenFree;
            }

        String starter = (startersComboBox.getSelectedIndex() >= 0) ? starters[startersComboBox.getSelectedIndex()] : null;
        String mainDish = (mainDishesComboBox.getSelectedIndex() >= 0) ? mainDishes[mainDishesComboBox.getSelectedIndex()] : null;
        String dessert = (dessertsComboBox.getSelectedIndex()>= 0) ? desserts[dessertsComboBox.getSelectedIndex()]: null;

        try {

            if (mealCategory == MealCategory.FullMeals){
                if ((starter == null) || (mainDish == null) || (dessert == null)){
                    System.out.println("Error: You must choose a starter, a main dish and a dessert.");
                    JOptionPane.showMessageDialog(new JFrame(),"You must choose a starter, a main dish and a dessert.","Error",JOptionPane.ERROR_MESSAGE);
                } else {

                    myFoodoraSystem.createMealGUI(mealName, mealCategory, foodType);
                    myFoodoraSystem.addDish2MealGUI(starter, mealName);
                    myFoodoraSystem.addDish2MealGUI(mainDish, mealName);
                    myFoodoraSystem.addDish2MealGUI(dessert, mealName);
                    success();
                }
            } else { //Meal category = half meal
                if (starterAndMainDishButton.isSelected()){

                    if ((starter == null) || (mainDish == null)){
                        System.out.println("Error: You must choose a starter and a main dish.");
                        JOptionPane.showMessageDialog(new JFrame(),"You must choose a starter and a main dish.","Error",JOptionPane.ERROR_MESSAGE);
                    } else {
                        myFoodoraSystem.createMealGUI(mealName, mealCategory, foodType);
                        myFoodoraSystem.addDish2MealGUI(starter, mealName);
                        myFoodoraSystem.addDish2MealGUI(mainDish, mealName);
                        success();


                    }

                } else { // mainDishAndDessertButton.isSelected()

                    if ((mainDish == null) || (dessert == null)){
                        System.out.println("Error: You must choose a main dish and a dessert.");
                        JOptionPane.showMessageDialog(new JFrame(),"You must choose a main dish and a dessert.","Error",JOptionPane.ERROR_MESSAGE);
                    } else {
                        myFoodoraSystem.createMealGUI(mealName, mealCategory, foodType);
                        myFoodoraSystem.addDish2MealGUI(mainDish, mealName);
                        myFoodoraSystem.addDish2MealGUI(dessert, mealName);
                        success();
                    }

                }
            }
        } catch (EmptyNameException e) {
            JOptionPane.showMessageDialog(new JFrame(),"The meal's name must not be empty.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (DuplicateNameException e) {
            JOptionPane.showMessageDialog(new JFrame(),"This meal's name already exists.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (IncompatibleFoodTypeException e) {
            JOptionPane.showMessageDialog(new JFrame(),"The category of this item isn't compatible with the meal's one.","Error", JOptionPane.ERROR_MESSAGE);
        } catch (TooManyItemsException e) {
            JOptionPane.showMessageDialog(new JFrame(),"There is already an item of this type OR there are too many items in this meal.","Error", JOptionPane.ERROR_MESSAGE);
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

        starters = myFoodoraSystem.getItems(ItemCategory.Starter, foodType);
        mainDishes = myFoodoraSystem.getItems(ItemCategory.MainDish, foodType);
        desserts = myFoodoraSystem.getItems(ItemCategory.Dessert, foodType);

        startersComboBox.setModel(new DefaultComboBoxModel<>(starters));
        mainDishesComboBox.setModel(new DefaultComboBoxModel<>(mainDishes));
        dessertsComboBox.setModel(new DefaultComboBoxModel<>(desserts));

    }

}
