package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class UserTabPanel extends JPanel{
    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();
    Customer[] customers;
    Restaurant[] restaurants;
    Courier[] couriers;

    JPanel headerPanel;
    JPanel workingPanel;

    JPanel customersPanel;
    JPanel restaurantsPanel;
    JPanel couriersPanel;

    JList<String> customersList;
    JList<String> courierList;
    JList<String> restaurantList;
    JLabel clientsLabel;
    DefaultListModel<String> customersNames;
    DefaultListModel<String> restaurantsName;
    DefaultListModel<String> couriersName;
    UserInfoPanel userInfoPanal;
    int[] size = {5,20,200};

    public UserTabPanel(){
        super();
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        Border border = BorderFactory.createEmptyBorder(0,5,0,5);

        headerPanel = new JPanel();
        headerPanel.setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
//        c.anchor = GridBagConstraints.WEST;
        headerPanel.add(new JLabel("Double-click to select a user"),c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
//        c.anchor = GridBagConstraints.EAST;
        clientsLabel = new JLabel("        Total Client: " + myFoodoraSystem.getNumOfAllCLients());
        headerPanel.add(clientsLabel,c);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.1;
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
        this.add(headerPanel,c);

        workingPanel = new JPanel();
        workingPanel.setLayout(new GridBagLayout());

        customersPanel = new JPanel();
        customersPanel.setLayout(new GridBagLayout());

        customers = myFoodoraSystem.getCustomers();
        customersList = new JList<>();
        customersList.setModel(listUser(customers));
        customersList.setVisibleRowCount(size[0]);
        customersList.setFixedCellHeight(size[1]);
        customersList.setFixedCellWidth(size[2]);
        customersList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        customersList.isSelectedIndex(0);
        customersList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Customer customer = customers[customersList.getSelectedIndex()];
                if(e.getClickCount() == 2){
                    userInfoPanal.showInfo(customer);
                }
            }
        });

        JScrollPane scrollPane1 = new JScrollPane(customersList);
        JLabel customersPanelLabel = new JLabel("Customer - total expense");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        customersPanel.add(customersPanelLabel,c);

        JPanel orderPanel = new JPanel(new GridLayout(1,2));
        JButton customerUp = new JButton(("Up"));
        customerUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customers = myFoodoraSystem.getCustomersUp();
                customersList.setModel(listUser(myFoodoraSystem.getCustomersUp()));
            }
        });
        orderPanel.add(customerUp);

        JButton customerDown = new JButton(("Down"));
        customerDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customers = myFoodoraSystem.getCustomersDown();
                customersList.setModel(listUser(myFoodoraSystem.getCustomersDown()));
            }
        });
        orderPanel.add(customerDown);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        customersPanel.add(orderPanel,c);


        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        customersPanel.add(scrollPane1,c);

        customersPanel.setBorder(border);

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        workingPanel.add(customersPanel,c);

        //Middle Panel
        restaurantsPanel = new JPanel();
        restaurantsPanel.setLayout(new GridBagLayout());

        restaurants = myFoodoraSystem.getRestaurants();
        restaurantList = new JList<>();
        restaurantList.setModel(listUser(restaurants));
        restaurantList.setVisibleRowCount(size[0]);
        restaurantList.setFixedCellHeight(size[1]);
        restaurantList.setFixedCellWidth(size[2]);
        restaurantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        restaurantList.isSelectedIndex(0);
        restaurantList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Restaurant restaurant = restaurants[restaurantList.getSelectedIndex()];
                if(e.getClickCount() == 2){
                    userInfoPanal.showInfo(restaurant);
                }
            }
        });
        JScrollPane scrollPane2 = new JScrollPane(restaurantList);
        JLabel restaurantsPanelLabel = new JLabel("Restaurant - income");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        restaurantsPanel.add(restaurantsPanelLabel,c);

        JPanel orderPanel1 = new JPanel(new GridLayout(1,2));
        JButton restaurantUp = new JButton(("Up"));
        restaurantUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurants = myFoodoraSystem.getRestaurantsUp();
                restaurantList.setModel(listUser(myFoodoraSystem.getRestaurantsUp()));
            }
        });
        orderPanel1.add(restaurantUp);

        JButton restaurantDown = new JButton(("Down"));
        restaurantDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restaurants = myFoodoraSystem.getRestaurantsDown();
                restaurantList.setModel(listUser(myFoodoraSystem.getRestaurantsDown()));
            }
        });
        orderPanel1.add(restaurantDown);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        restaurantsPanel.add(orderPanel1,c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        restaurantsPanel.add(scrollPane2,c);

        restaurantsPanel.setBorder(border);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        workingPanel.add(restaurantsPanel,c);

        //Right Panel
        couriersPanel = new JPanel();
        couriersPanel.setLayout(new GridBagLayout());

        couriers = myFoodoraSystem.getCouriers();
        courierList = new JList<>();
        courierList.setModel(listUser(couriers));
        courierList.setVisibleRowCount(size[0]);
        courierList.setFixedCellHeight(size[1]);
        courierList.setFixedCellWidth(size[2]);
        courierList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courierList.isSelectedIndex(0);
        courierList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Courier courier = couriers[courierList.getSelectedIndex()];
                if(e.getClickCount() == 2){
                    userInfoPanal.showInfo(courier);
                }
            }
        });
        JScrollPane scrollPane3 = new JScrollPane(courierList);
        JLabel couriersPanelLabel = new JLabel("Courier - number of delivery");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        couriersPanel.add(couriersPanelLabel,c);

        JPanel orderPanel2 = new JPanel(new GridLayout(1,2));
        JButton courierUp = new JButton(("Up"));
        courierUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                couriers = myFoodoraSystem.getCouriersUp();
                courierList.setModel(listUser(myFoodoraSystem.getCouriersUp()));
            }
        });
        orderPanel2.add(courierUp);

        JButton courierDown = new JButton(("Down"));
        courierDown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                couriers = myFoodoraSystem.getCouriersDown();
                courierList.setModel(listUser(myFoodoraSystem.getCouriersDown()));
            }
        });
        orderPanel2.add(courierDown);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        couriersPanel.add(orderPanel2,c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        couriersPanel.add(scrollPane3,c);

        couriersPanel.setBorder(border);

        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        workingPanel.add(couriersPanel,c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        this.add(workingPanel,c);

        userInfoPanal = new UserInfoPanel();
        userInfoPanal.addObserver(this);
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        this.add(userInfoPanal,c);

    }

    public static DefaultListModel<String> listUser(User[] users){
        DefaultListModel<String> nameModel = new DefaultListModel<>();
        int i = 0;
        for (User user: users
                ) {
            i++;
            if (!user.getStatus()){
                nameModel.addElement(i + ") " + user.getUsername() + "(disable)");
            }
            else {
                if (user instanceof Customer){
                    nameModel.addElement(i + ") " + user.getUsername() + " - " + Money.display(((Customer) user).getTotalExpense()));
                }
                else if (user instanceof Restaurant){
                    nameModel.addElement(i + ") " + user.getUsername() + " - " + Money.display(((Restaurant) user).getIncome()));
                }
                else if (user instanceof Courier){
                    nameModel.addElement(i + ") " + user.getUsername() + " - " + ((Courier) user).getDeliveredOrdersCounter());
                }
            }
        }
        return nameModel;
    }

    public void updateInfo(){
        clientsLabel.setText("        Total Client: " + myFoodoraSystem.getNumOfAllCLients());
        customersList.setModel(listUser(myFoodoraSystem.getCustomers()));
        restaurantList.setModel(listUser(myFoodoraSystem.getRestaurants()));
        courierList.setModel(listUser(myFoodoraSystem.getCouriers()));
    }
}
