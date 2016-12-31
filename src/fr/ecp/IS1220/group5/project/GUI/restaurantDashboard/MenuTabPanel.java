package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.GUI.RestaurantDashboard;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.menu.Food;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class MenuTabPanel extends JPanel {

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    RestaurantDashboard restaurantDashboard;

    Item[] items;
    Meal[] meals;

    JPanel headerPanel;
    JPanel workingPanel;

    JPanel itemsPanel;
    JPanel mealsPanel;

    JList<String> itemsList;
    JList<String> mealsList;


    public MenuTabPanel(RestaurantDashboard restaurantDashboard){
        super();
        this.restaurantDashboard = restaurantDashboard;
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(new JLabel("The Menu"), c);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.1;
        this.add(headerPanel,c);

        workingPanel = new JPanel();
        workingPanel.setLayout(new GridBagLayout());


        //Items panel
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridBagLayout());

        items = myFoodoraSystem.getItems();
        itemsList = new JList<>();
        itemsList.setModel(listFood(items));
        itemsList.setVisibleRowCount(10);
        itemsList.setFixedCellHeight(25);
        itemsList.setFixedCellWidth(150);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane1 = new JScrollPane(itemsList);
        JLabel customersPanelLabel = new JLabel("Items");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        itemsPanel.add(customersPanelLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        itemsPanel.add(scrollPane1,c);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        workingPanel.add(itemsPanel,c);

        //Meals Panel
        mealsPanel = new JPanel();
        mealsPanel.setLayout(new GridBagLayout());

        meals = myFoodoraSystem.getMeals();
        mealsList = new JList<>();
        mealsList.setModel(listFood(meals));
        mealsList.setVisibleRowCount(10);
        mealsList.setFixedCellHeight(25);
        mealsList.setFixedCellWidth(150);
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane2 = new JScrollPane(mealsList);
        JLabel restaurantsPanelLabel = new JLabel("Meals");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        mealsPanel.add(restaurantsPanelLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        mealsPanel.add(scrollPane2,c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        workingPanel.add(mealsPanel,c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        this.add(workingPanel,c);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridBagLayout());

        JButton removeItemButton = new JButton("Remove item");
        removeItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeItemFromMenu();
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        buttonsPanel.add(removeItemButton, c);

        JButton removeMealButton = new JButton("Remove meal");
        removeMealButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMealFromMenu();
            }
        });
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        buttonsPanel.add(removeMealButton, c);

        JButton setMealOfTheWeekButton = new JButton("Set meal-of-the-week (MOTW)");
        setMealOfTheWeekButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setMealOfTheWeek();
            }
        });
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        buttonsPanel.add(setMealOfTheWeekButton, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        this.add(buttonsPanel, c);
    }

    private void removeItemFromMenu() {
        if (itemsList.getSelectedIndex() != -1) {
            ((Restaurant) myFoodoraSystem.getCurrentUser()).removeItemAtIndex(itemsList.getSelectedIndex());
            myFoodoraSystem.saveMenu();
            restaurantDashboard.update();
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"You must select an item","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeMealFromMenu() {
        if (mealsList.getSelectedIndex() != -1) {
            ((Restaurant) myFoodoraSystem.getCurrentUser()).removeMealAtIndex(mealsList.getSelectedIndex());
            myFoodoraSystem.saveMenu();
            restaurantDashboard.update();
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"You must select a meal","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setMealOfTheWeek(){
        if (mealsList.getSelectedIndex() != -1) {
            for (int i = 0; i < meals.length; i++) {
                meals[i].setMealOfTheWeek(i == mealsList.getSelectedIndex());
            }

            myFoodoraSystem.saveMenu();
            restaurantDashboard.update();
        }else{
            JOptionPane.showMessageDialog(new JFrame(),"You must select a meal","Error", JOptionPane.ERROR_MESSAGE);
        }

    }


    public DefaultListModel<String> listFood(Food[] dishes){
        DefaultListModel<String> nameModel = new DefaultListModel<>();
        for (Food food: dishes){

            nameModel.addElement(food.getName() + " : " + Money.display(food.getPrice()) + ((food instanceof Meal && ((Meal) food).isMealOfTheWeek()) ? " (MOTW)" : ""));
        }
        return nameModel;
    }

    public void update() {

        items = myFoodoraSystem.getItems();
        itemsList.setModel(listFood(items));

        meals = myFoodoraSystem.getMeals();
        for (Meal meal : meals){
            meal.updatePrice();
        }
        mealsList.setModel(listFood(meals));

    }
}
