package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by dennis101251 on 2016/12/27.
 */
public class userTabPanel extends JPanel {
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
    DefaultListModel<String> customersNames;
    DefaultListModel<String> restaurantsName;
    DefaultListModel<String> couriersName;
    UserInfoPanal userInfoPanal;
    int[] size = {5,20,200};

    public userTabPanel(){
        super();
        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();

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
        headerPanel.add(new JLabel("        Total Client: " + myFoodoraSystem.getNumOfAllCLients()),c);
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.1;
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
        JLabel customersPanelLabel = new JLabel("Customer");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        customersPanel.add(customersPanelLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        customersPanel.add(scrollPane1,c);

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
        JLabel restaurantsPanelLabel = new JLabel("Restaurant");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        restaurantsPanel.add(restaurantsPanelLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        restaurantsPanel.add(scrollPane2,c);

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
        JLabel couriersPanelLabel = new JLabel("Courier");
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0.2;
        couriersPanel.add(couriersPanelLabel,c);
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        couriersPanel.add(scrollPane3,c);

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

        userInfoPanal = new UserInfoPanal();
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 1;
        this.add(userInfoPanal,c);
    }

    public static DefaultListModel<String> listUser(User[] users){
        DefaultListModel<String> nameModel = new DefaultListModel<>();
        for (User user: users
                ) {
            if (!user.getStatus()){
                nameModel.addElement(user.getUsername() + "(disable)");
            }
            else {
                nameModel.addElement(user.getUsername());
            }
        }
        return nameModel;
    }
}
