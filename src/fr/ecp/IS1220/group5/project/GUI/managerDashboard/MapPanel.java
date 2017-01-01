package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.DefaultXYDataset;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by dennis101251 on 2017/1/1.
 */
public class MapPanel extends JPanel {
    private MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();

    private ArrayList<User> clients;
    private XYTextAnnotation[] xyTextAnnotations;
    DefaultXYDataset xydataset;


    public MapPanel(){
        super();

        xydataset = new DefaultXYDataset ();

        myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();

        clients = myFoodoraSystemGUI.getAllClients();
        double[][] data=new double[2][clients.size()];

        //Get x
        int i = 0;
        for (User user: clients) {
            if (user instanceof Restaurant){
                data[0][i] = ((Restaurant) user).getAddress().getX();
                data[1][i] = ((Restaurant) user).getAddress().getY();
            }
            else if (user instanceof Customer){
                data[0][i] = ((Customer) user).getAddress().getX();
                data[1][i] = ((Customer) user).getAddress().getY();
            }
            else if (user instanceof Courier){
                data[0][i] = ((Courier) user).getPosition().getX();
                data[1][i] = ((Courier) user).getPosition().getY();
            }
            i++;
        }
        xydataset.addSeries("Tha map of clients", data);
        final JFreeChart chart = ChartFactory.createScatterPlot("","","",xydataset, PlotOrientation.VERTICAL,false,false,false);

        XYPlot xyplot = (XYPlot) chart.getPlot();

        for (int j = 0; j < clients.size(); j++) {
            xyplot.addAnnotation(new XYTextAnnotation(clients.get(j).getType() + clients.get(j).getId(),data[0][j]+1,data[1][j]+1));
        }

        ChartPanel panel = new ChartPanel(chart,true);
        add(panel);
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        jFrame.add(new MapPanel());
        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }
}
