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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
        headerPanel.add(new JLabel("Double-click to remove"), c);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.1;
        this.add(headerPanel,c);

        workingPanel = new JPanel();
        workingPanel.setLayout(new GridBagLayout());

        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridBagLayout());

        items = myFoodoraSystem.getItems();
        itemsList = new JList<>();
        itemsList.setModel(listFood(items));
        itemsList.setVisibleRowCount(10);
        itemsList.setFixedCellHeight(25);
        itemsList.setFixedCellWidth(150);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.isSelectedIndex(0);
        itemsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    removeItemFromMenu();
                }

            }

        });


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

        //Middle Panel
        mealsPanel = new JPanel();
        mealsPanel.setLayout(new GridBagLayout());

        meals = myFoodoraSystem.getMeals();
        mealsList = new JList<>();
        mealsList.setModel(listFood(meals));
        mealsList.setVisibleRowCount(10);
        mealsList.setFixedCellHeight(25);
        mealsList.setFixedCellWidth(150);
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mealsList.isSelectedIndex(0);
        mealsList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    removeMealFromMenu();
                }

            }

        });

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
    }

    private void removeItemFromMenu() {

        ((Restaurant) myFoodoraSystem.getCurrentUser()).removeItemAtIndex(itemsList.getSelectedIndex());
        myFoodoraSystem.saveMenu();
        restaurantDashboard.update();
    }

    private void removeMealFromMenu() {

        ((Restaurant) myFoodoraSystem.getCurrentUser()).removeMealAtIndex(mealsList.getSelectedIndex());
        myFoodoraSystem.saveMenu();
        restaurantDashboard.update();
    }


    public DefaultListModel<String> listFood(Food[] dishes){
        DefaultListModel<String> nameModel = new DefaultListModel<>();
        for (Food food: dishes){

            nameModel.addElement(food.getName() + " : " + Money.display(food.getPrice()));
        }
        return nameModel;
    }

    public void update() {

        items = myFoodoraSystem.getItems();
        itemsList.setModel(listFood(items));

        meals = myFoodoraSystem.getMeals();
        mealsList.setModel(listFood(meals));

    }
}
