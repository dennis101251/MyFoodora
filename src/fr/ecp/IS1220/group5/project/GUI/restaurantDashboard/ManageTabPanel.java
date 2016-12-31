package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.GUI.RestaurantDashboard;
import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.SortByQuantityDown;
import fr.ecp.IS1220.group5.project.util.SortByQuantityUp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * Created by alexandre_carlier on 27/12/2016.
 */
public class ManageTabPanel extends JPanel {

    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    RestaurantDashboard restaurantDashboard;

    JTextField genericDiscountFactorTextField;
    JTextField specialDiscountFactorTextField;

    JList<String> itemsList;
    JList<String> mealsList;

    public ManageTabPanel(RestaurantDashboard restaurantDashboard){

        this.restaurantDashboard = restaurantDashboard;

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();


        //Generic discount factor
        JPanel genericDiscountFactorPanel = new JPanel();
        genericDiscountFactorPanel.setLayout(new BoxLayout(genericDiscountFactorPanel,BoxLayout.X_AXIS));
        JLabel genericDiscountFactorLabel = new JLabel("Generic Discount Factor: ");
        genericDiscountFactorPanel.add(genericDiscountFactorLabel);

        genericDiscountFactorTextField = new JTextField(getGenericDiscountFactor(), 15);
        genericDiscountFactorPanel.add(genericDiscountFactorTextField);

        JButton saveGenericDiscountFactorButton = new JButton("Save");
        saveGenericDiscountFactorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGenericDiscountFactor();
            }
        });
        genericDiscountFactorPanel.add(saveGenericDiscountFactorButton);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        this.add(genericDiscountFactorPanel, c);


        //Special discount factor
        JPanel specialDiscountFactorPanel = new JPanel();
        specialDiscountFactorPanel.setLayout(new BoxLayout(specialDiscountFactorPanel,BoxLayout.X_AXIS));
        JLabel specialDiscountFactorLabel = new JLabel("Special Discount Factor: ");
        specialDiscountFactorPanel.add(specialDiscountFactorLabel);

        specialDiscountFactorTextField = new JTextField(getSpecialDiscountFactor(),15);
        specialDiscountFactorPanel.add(specialDiscountFactorTextField);

        JButton saveSpecialDiscountFactorButton = new JButton("Save");
        saveSpecialDiscountFactorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setSpecialDiscountFactor();
            }
        });
        specialDiscountFactorPanel.add(saveSpecialDiscountFactorButton);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        this.add(specialDiscountFactorPanel, c);


        //Shipped orders
        JPanel shippedOrders = new JPanel();
        shippedOrders.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        shippedOrders.add(new JLabel("Shipped orders"), c);


        //Items panel
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridBagLayout());

        JPanel sortItemsButtons = new JPanel(new GridLayout(1,2));
        JButton sortItemsDownButton = new JButton("Down");
        sortItemsDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemsList.setModel(myFoodoraSystem.sortItems(new SortByQuantityDown()));
            }
        });

        JButton sortItemsUpButton = new JButton("Up");
        sortItemsUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemsList.setModel(myFoodoraSystem.sortItems(new SortByQuantityUp()));
            }
        });
        sortItemsButtons.add(sortItemsDownButton);
        sortItemsButtons.add(sortItemsUpButton);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        itemsPanel.add(sortItemsButtons, c);

        itemsList = new JList<>(myFoodoraSystem.sortItems(new SortByQuantityDown()));
        itemsList.setVisibleRowCount(10);
        itemsList.setFixedCellHeight(25);
        itemsList.setFixedCellWidth(150);
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneItems = new JScrollPane(itemsList);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        itemsPanel.add(scrollPaneItems, c);


        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        shippedOrders.add(itemsPanel, c);

        //Meals panel
        JPanel mealsPanel = new JPanel();
        mealsPanel.setLayout(new GridBagLayout());

        JPanel sortMealsButtons = new JPanel(new GridLayout(1,2));
        JButton sortMealsDownButton = new JButton("Down");
        sortMealsDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mealsList.setModel(myFoodoraSystem.sortMeals(new SortByQuantityDown()));
            }
        });
        JButton sortMealsUpButton = new JButton("Up");
        sortMealsUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mealsList.setModel(myFoodoraSystem.sortMeals(new SortByQuantityUp()));
            }
        });
        sortMealsButtons.add(sortMealsDownButton);
        sortMealsButtons.add(sortMealsUpButton);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        mealsPanel.add(sortMealsButtons, c);

        mealsList = new JList<>(myFoodoraSystem.sortMeals(new SortByQuantityDown()));
        mealsList.setVisibleRowCount(10);
        mealsList.setFixedCellHeight(25);
        mealsList.setFixedCellWidth(150);
        mealsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPaneMeals = new JScrollPane(mealsList);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mealsPanel.add(scrollPaneMeals, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        shippedOrders.add(mealsPanel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        this.add(shippedOrders, c);

    }

    public void setGenericDiscountFactor(){

        try{

            BigDecimal genericDiscountFactor = new BigDecimal(genericDiscountFactorTextField.getText());
            myFoodoraSystem.setGenericDiscountFactor(genericDiscountFactor);
            restaurantDashboard.update();

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(new JFrame(),"Invalid number format","Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    public void setSpecialDiscountFactor(){

        try{

            BigDecimal specialDiscountFactor = new BigDecimal(specialDiscountFactorTextField.getText());
            myFoodoraSystem.setSpecialDiscountFactor(specialDiscountFactor);
            restaurantDashboard.update();

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(new JFrame(),"Invalid number format","Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getGenericDiscountFactor(){

        return ((Restaurant) myFoodoraSystem.getCurrentUser()).getGenericDiscountFactor().toString();

    }

    public String getSpecialDiscountFactor(){

        return ((Restaurant) myFoodoraSystem.getCurrentUser()).getSpecialDiscountFactor().toString();

    }

}
