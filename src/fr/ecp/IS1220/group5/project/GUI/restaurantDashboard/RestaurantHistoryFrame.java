package fr.ecp.IS1220.group5.project.GUI.restaurantDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by dennis101251 on 2017/1/6.
 */
public class RestaurantHistoryFrame extends JFrame implements WindowListener{
    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    JPanel mainPanel;

    JPanel historyPanel;
    JLabel headerLabel;
    JTextArea orderArea;
    private static RestaurantHistoryFrame historyFrame;

    public static RestaurantHistoryFrame getInstance(){
        if (historyFrame == null){
            historyFrame = new RestaurantHistoryFrame();
            return historyFrame;
        }
        else {
            return historyFrame;
        }
    }

    private RestaurantHistoryFrame(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("History");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 60, 30));
        mainPanel.setLayout(new GridBagLayout());
        this.setContentPane(mainPanel);

        //History Panel
        historyPanel = new JPanel(new GridBagLayout());
        headerLabel = new JLabel("History of order");
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        historyPanel.add(headerLabel, new GBC(0,0).setFill(GridBagConstraints.BOTH));
        orderArea = new JTextArea(showHistory(),8,20);
        orderArea.setLineWrap(true);
        orderArea.setWrapStyleWord(true);
        orderArea.setEditable(false);
        orderArea.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        historyPanel.add(orderArea,new GBC(0,1));
        mainPanel.add(historyPanel,new GBC(0,1));


        this.pack();
        setVisible(true);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
    }

    private String showHistory(){
        Restaurant currentUser = (Restaurant) myFoodoraSystem.getCurrentUser();
        String string = null;
        if (!currentUser.getOrders().isEmpty()){
            for (int i = 0; i < ((Restaurant) currentUser).getOrders().size(); i++) {
                string = ">> " + (i+1) + ") Order" + "\n";
                string += ((Restaurant) currentUser).getOrders().get(i).toString();

                return string;
            }
        }
        else {
//            System.out.println("Your history of order is empty");
            return "Your history of order is empty";
        }
        return string;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        historyFrame = null;
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
