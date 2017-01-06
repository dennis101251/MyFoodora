package fr.ecp.IS1220.group5.project.GUI.customerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.exception.UserNotFoundException;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URL;

/**
 * Created by dennis101251 on 2017/1/1.
 *
 * Contain the information of order history and fidelity card
 */
public class HistoryFrame extends JFrame implements WindowListener{

    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    private String[] orders;

    private HistoryFrame self = this;

    JPanel mainPanel;

    JPanel fidelityPanel;
        ImageIcon icon;
        JLabel iconLabel;
        JLabel nameLabel;
        JLabel pointLabel;
    JPanel historyPanel;
        JLabel headerLabel;
        JTextArea orderArea;

    private static HistoryFrame historyFrame;

    public static HistoryFrame getInstance(){
        if (historyFrame == null){
            historyFrame = new HistoryFrame();
            return historyFrame;
        }
        else {
            return historyFrame;
        }
    }

    private HistoryFrame(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("History");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        this.setContentPane(mainPanel);

        //Fidelity panel
        fidelityPanel = new JPanel(new GridBagLayout());
        String fidelityString = ((Customer) myFoodoraSystem.getCurrentUser()).getFidelityCard().getFidelityCardName();
        switch (fidelityString){
            case "BasicFidelityCard":
                icon = createImage("/images/Basic.png","Basic");
                break;
            case "LotteryFidelityCard":
                icon = createImage("/images/Lottery.png","Lottory");
                break;
            case "PointFidelityCard":
                icon = createImage("/images/Point.png","Point");
                break;
            default:
                icon = createImage("/images/Basic.png","Basic");
        }
        iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,15));
        fidelityPanel.add(iconLabel,new GBC(0,0,1,2));
        nameLabel = new JLabel(myFoodoraSystem.getCurrentUser().getName() + ": " + fidelityString);
        fidelityPanel.add(nameLabel,new GBC(1,0,1,1).setFill(GridBagConstraints.BOTH));
        pointLabel = new JLabel( "Points: "+((Customer) myFoodoraSystem.getCurrentUser()).getFidelityCard().getPoints());
        fidelityPanel.add(pointLabel, new GBC(1,1,1,1).setFill(GridBagConstraints.BOTH));
        JButton changeFidelityButton = new JButton("Change fidelity card!");
        changeFidelityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userChoice = JOptionPane.showOptionDialog(new Frame(), "Which kind of fidelity card you want choose?","Fidelity card",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,new String[]{"Basic card","Lottery card","Point card"},null);
                switch (userChoice){
                    case 2:
                        //Point
                        try {
                            myFoodoraSystem.associateCard(myFoodoraSystem.getCurrentUser().getUsername(),"PointFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case 1:
                        //Lottery
                        try {
                            myFoodoraSystem.associateCard(myFoodoraSystem.getCurrentUser().getUsername(),"LotteryFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    case 0:
                        //Basic
                        try {
                            myFoodoraSystem.associateCard(myFoodoraSystem.getCurrentUser().getUsername(),"basicFidelityCard");
                        } catch (UserNotFoundException e1) {
                            e1.printStackTrace();
                        }
                        break;
                    default:
                }
                String fidelityString = ((Customer) myFoodoraSystem.getCurrentUser()).getFidelityCard().getFidelityCardName();
                nameLabel.setText(myFoodoraSystem.getCurrentUser().getName() + ": " + fidelityString);
                switch (fidelityString){
                    case "BasicFidelityCard":
                        icon = createImage("/images/Basic.png","Basic");
                        break;
                    case "LotteryFidelityCard":
                        icon = createImage("/images/Lottery.png","Lottory");
                        break;
                    case "PointFidelityCard":
                        icon = createImage("/images/Point.png","Point");
                        break;
                    default:
                        icon = createImage("/images/Basic.png","Basic");
                }
                iconLabel.setIcon(icon);

            }
        });
        fidelityPanel.add(changeFidelityButton, new GBC(1,2,1,1));

        mainPanel.add(fidelityPanel,new GBC(0,0));

        //History Panel
        historyPanel = new JPanel(new GridBagLayout());
        headerLabel = new JLabel("History of order");
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));
        historyPanel.add(headerLabel, new GBC(0,0).setFill(GridBagConstraints.BOTH));
        orderArea = new JTextArea(showHistory(),8,20);
        orderArea.setLineWrap(true);
        orderArea.setWrapStyleWord(true);
        orderArea.setEditable(false);
//        orderArea.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        historyPanel.add(orderArea,new GBC(0,1));
        mainPanel.add(historyPanel,new GBC(0,1));


        this.pack();
        setVisible(true);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
    }

    private ImageIcon createImage(String path, String description) {
        URL imgURL=getClass().getResource(path);
        if(imgURL!=null){
            ImageIcon imageIcon = new ImageIcon(imgURL,description);    // Icon由图片文件形成
            Image image = imageIcon.getImage();                         // 但这个图片太大不适合做Icon
            //    为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
            Image smallImage = image.getScaledInstance(64,64,Image.SCALE_SMOOTH);
            //    再由修改后的Image来生成合适的Icon
            ImageIcon smallIcon = new ImageIcon(smallImage);
            return smallIcon;
        }
        else
            System.out.println("couldn't find file " + path);
        return null;
    }

    private String showHistory(){
        Customer currentUser = (Customer) myFoodoraSystem.getCurrentUser();
        String string = null;
        if (!currentUser.getHistoryOfOrder().isEmpty()){
            for (int i = 0; i < ((Customer) currentUser).getHistoryOfOrder().size(); i++) {
                string = ">> " + (i+1) + ") Order" + "\n";
                string += ((Customer) currentUser).getHistoryOfOrder().get(i).toString();
                return string;
            }
        }
        else {
//            System.out.println("Your history of order is empty");
            return "Your history of order is empty";
        }
        return string;
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("marco","123456");
        HistoryFrame.getInstance();
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        historyFrame = null;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
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
