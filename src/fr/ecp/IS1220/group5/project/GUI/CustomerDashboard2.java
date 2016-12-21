package fr.ecp.IS1220.group5.project.GUI;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.menu.Food;
import fr.ecp.IS1220.group5.project.menu.Item;
import fr.ecp.IS1220.group5.project.menu.Meal;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by alexandre_carlier on 20/12/2016.
 */
public class CustomerDashboard2 extends JFrame implements Observer{

    MyFoodoraSystemGUI myFoodoraSystem;

    Restaurant[] restaurants;
    Food[] dishes;


    JPanel mainPanel;

    JPanel headerPanel;
    JPanel orderPanel;

    JPanel leftPanel;
    JPanel rightPanel;

    JList<String> list;
    JList<String> list2;
    JList<String> list3;

    private Order order;

    public CustomerDashboard2(){

        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
        restaurants = myFoodoraSystem.getRestaurants();

        this.setSize(500, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.setContentPane(mainPanel);

        //Header

        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("Welcome " + myFoodoraSystem.getCurrentUser().getName() + "!");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        headerPanel.add(welcomeLabel, c);

        JButton logoutButton = new JButton("Log out");
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        headerPanel.add(logoutButton, c);


        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        mainPanel.add(headerPanel, c);

        //Order
        orderPanel = new JPanel();
        orderPanel.setLayout(new GridBagLayout());



        //Left panel
        leftPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        leftPanel.setLayout(new GridLayout(4, 1));

        leftPanel.add(new JLabel("Choose a restaurant:"));

        String[] restaurantsNames = myFoodoraSystem.getRestaurantsNames();
        myFoodoraSystem.setCurrentRestaurant(restaurants[0]);

        list = new JList<>(restaurantsNames);
        list.setVisibleRowCount(5);
        list.setFixedCellHeight(20);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.isSelectedIndex(0);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                updateMenuList();

            }

        });
        JScrollPane scrollPane = new JScrollPane(list);

        leftPanel.add(scrollPane);

        leftPanel.add(new JLabel("Choose your dishes: (double-click to select)"));

        dishes = myFoodoraSystem.getMenu();
        String[] dishesNames = myFoodoraSystem.getMenuNames();

        list2 = new JList<>(dishesNames);
        list2.setVisibleRowCount(5);
        list2.setFixedCellHeight(20);
        list2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    updateOrder();
                }

            }

        });
        JScrollPane scrollPane2 = new JScrollPane(list2);
        leftPanel.add(scrollPane2);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.65;
        c.weighty = 1;
        orderPanel.add(leftPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.1;
        c.weighty = 1;
        orderPanel.add(new JPanel(), c);

        //Right panel
        rightPanel = new JPanel();
        rightPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
        rightPanel.setLayout(new GridLayout(4, 1));

        rightPanel.add(new JLabel("Your order"));

        order = new Order(myFoodoraSystem.getCurrentRestaurant(), (Customer) myFoodoraSystem.getCurrentUser(), myFoodoraSystem.getDelivery_cost_price(), myFoodoraSystem.getMarkup_percentage(), myFoodoraSystem.getService_fee());
        order.addObserver(this);


        String[] chosenDishes = {};
        list3 = new JList<>(chosenDishes);
        list3.setVisibleRowCount(5);
        list3.setFixedCellHeight(20);
        JScrollPane scrollPane3 = new JScrollPane(list3);
        rightPanel.add(scrollPane3);

        rightPanel.add(new JLabel("Total price: 5€"));

        JPanel endOrderPanel = new JPanel();
        JButton endOrder = new JButton("End order");
        endOrderPanel.add(endOrder);
        rightPanel.add(endOrderPanel);

        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.25;
        c.weighty = 1;
        orderPanel.add(rightPanel, c);


        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        mainPanel.add(orderPanel, c);

        this.setVisible(true);


    }

    public static void main(String[] args) {

        CustomerDashboard2 dashboard = new CustomerDashboard2();

    }

    public void updateMenuList(){

        myFoodoraSystem.setCurrentRestaurant(restaurants[list.getSelectedIndex()]);
        order.setRestaurant(restaurants[list.getSelectedIndex()]);
        order.empty();

        String[] dishesNames = myFoodoraSystem.getMenuNames();

        DefaultListModel<String> model = new DefaultListModel<>();
        for(String dishName : dishesNames)
            model.addElement(dishName);

        list2.setModel(model);

    }


    public void updateOrder(){

        Food food = dishes[list2.getSelectedIndex()];

        if (food instanceof Item){
            order.addItem((Item) food);
        }else{
            order.addMeal((Meal) food);
        }

    }

    @Override
    public void update(Observable o, Object arg) {

        //When the order is changed.
        DefaultListModel<String> model = new DefaultListModel<>();
        for(Food food: order.getFood()) {
            model.addElement(food.getName());
        }
        list3.setModel(model);


    }
}
