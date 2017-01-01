package fr.ecp.IS1220.group5.project.GUI.customerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.user.Customer;
import fr.ecp.IS1220.group5.project.util.GBC;
import fr.ecp.IS1220.group5.project.util.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by dennis101251 on 2016/12/31.
 */
public class InfoBoardFrame extends JFrame implements WindowListener{

    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    protected ArrayList<Message> messages;

    private InfoBoardFrame self = this;

    JPanel mainPanel;

    JTabbedPane allInfoPanel;
    JPanel basicInfoPanel;
        JLabel name;
        JLabel numOfMessage;
    JPanel listPanel;
        JList<String> titleList;
    JPanel contentPanel;
        JTextArea messageArea;
        JButton star;
        JButton delete;
    JPanel notifyPanel;
        JLabel notifiedLabel;
        JButton notifiedButton;
    JPanel contactPanel;
        JLabel contactTypeLabel;
        JRadioButton phoneButton = new JRadioButton("Phone");
        JRadioButton emailButton = new JRadioButton("Email");
        ActionListener listener;
        JLabel phoneLabel;
        JLabel emailLabel;
        JButton newContact;
    int[] size = {5,20,230};

    Message message;

    private static InfoBoardFrame infoBoardFrame;

    public static InfoBoardFrame getIntance(){
        if (infoBoardFrame == null){
            infoBoardFrame = new InfoBoardFrame();
            return infoBoardFrame;
        }
        else {
            return infoBoardFrame;
        }
    }

    private InfoBoardFrame(){
        super();
        myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

        this.setSize(400,600);
        this.setResizable(true);
        this.setTitle("Info Board");

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setLayout(new GridBagLayout());
        this.setContentPane(mainPanel);

        //BasicInfoPanel
        basicInfoPanel = new JPanel(new GridBagLayout());
        name = new JLabel("Hi! " + myFoodoraSystem.getCurrentUser().getName());
        basicInfoPanel.add(name,new GBC(0,0));
        numOfMessage = new JLabel("     You have " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getMessages().size() + " message");
        basicInfoPanel.add(numOfMessage, new GBC(1,0));
        mainPanel.add(basicInfoPanel,new GBC(0,0));

        //ListPanel
        listPanel = new JPanel();
        titleList = new JList<>();
        messages = ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getMessages();
        titleList.setModel(listTitle(((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getMessages()));
        titleList.setVisibleRowCount(size[0]);
        titleList.setFixedCellHeight(size[1]);
        titleList.setFixedCellWidth(size[2]);
        titleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        titleList.isSelectedIndex(0);
        titleList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                enableButton(true);
                message = messages.get(titleList.getSelectedIndex());
                messageArea.setText(message.showMessage());
                self.pack();
            }
        });
        JScrollPane scrollPane = new JScrollPane(titleList);
        listPanel.add(scrollPane);
//        mainPanel.add(listPanel,new GBC(0,1));

        //Content Panel
        contentPanel = new JPanel(new GridBagLayout());
        messageArea = new JTextArea("No message has been selected",8,20);
        messageArea.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Helvetica",Font.PLAIN,14));
        contentPanel.add(messageArea,new GBC(0,0,2,1));
        star = new JButton("Star");
        star.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message.star();
                ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.updateMessage(message);
                myFoodoraSystem.saveUser();
                messages = ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getMessages();
                titleList.setModel(listTitle(messages));
            }
        });
        delete = new JButton("Delete");

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.deleteMessage(message);
                myFoodoraSystem.saveUser();
                messages = ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getMessages();
                titleList.setModel(listTitle(messages));
                messageArea.setText("No message has been selected");
            }
        });
        enableButton(false);
        contentPanel.add(star,new GBC(0,1,1,1).setFill(GridBagConstraints.BOTH).setWeight(1,0));
        contentPanel.add(delete,new GBC(1,1,1,1).setFill(GridBagConstraints.BOTH).setWeight(1,0));
//        mainPanel.add(contentPanel,new GBC(0,2));

        //Notify panel
        notifyPanel = new JPanel(new GridBagLayout());
        notifiedLabel = new JLabel("The state of notification");
        notifyPanel.add(notifiedLabel,new GBC(0,0).setFill(GridBagConstraints.BOTH));

        notifiedButton = new JButton(((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.isNotified().toString());
        notifiedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.isNotified()){
                    ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.setNotified_Off();
                    enableButton(false);
                    JOptionPane.showMessageDialog(new JFrame(),"You won't receive any message from system","Infoboard",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.setNotified_On();
                }
                myFoodoraSystem.saveUser();
                notifiedButton.setText(((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.isNotified().toString());
            }
        });
        notifyPanel.add(notifiedButton,new GBC(1,0).setFill(GridBagConstraints.BOTH));

        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = ((JRadioButton) e.getSource()).getText();
                switch (string){
                    case "Phone":
                        ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.setContactType_Phone();
                        myFoodoraSystem.saveUser();
                        break;
                    case "Email":
                        ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.setContactType_Email();
                        myFoodoraSystem.saveUser();
                        break;
                    default:
                }
                System.out.println();
            }
        };

        contactPanel = new JPanel(new GridBagLayout());
        contactTypeLabel = new JLabel("Contact type: ");
        contactPanel.add(contactTypeLabel,new GBC(0,0));
        phoneButton.addActionListener(listener);
        emailButton.addActionListener(listener);
        String type = ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getContactType();
        switch (type){
            case "email":
                emailButton.setSelected(true);
                break;
            case "phone":
                phoneButton.setSelected(true);
                break;
        }
        ButtonGroup group = new ButtonGroup();
        group.add(phoneButton);
        group.add(emailButton);
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.add(phoneButton,new GBC(0,0).setFill(GridBagConstraints.BOTH));
        buttonPanel.add(emailButton,new GBC(1,0).setFill(GridBagConstraints.BOTH));

        contactPanel.add(buttonPanel,new GBC(0,1));

        phoneLabel = new JLabel("Phone: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getPhone());
        phoneLabel.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        contactPanel.add(phoneLabel, new GBC(0,2));

        emailLabel = new JLabel("Email: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getEmail());
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));
        contactPanel.add(emailLabel, new GBC(0,3));



        newContact = new JButton("New Contact");
        newContact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewContactFrame newContactFrame = NewContactFrame.getInstance();
                newContactFrame.addObserver(self);
//                System.out.println("Phone: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getPhone());
            }
        });
        contactPanel.add(newContact,new GBC(0,4));

        allInfoPanel = new JTabbedPane();
        JPanel messageTabPanel = new JPanel(new GridBagLayout());
        messageTabPanel.add(listPanel,new GBC(0,0));
        messageTabPanel.add(contentPanel,new GBC(0,1));
        JPanel contactTabPanel = new JPanel(new GridBagLayout());
        contactTabPanel.add(notifyPanel,new GBC(0,0));
        contactTabPanel.add(contactPanel,new GBC(0,1));

        allInfoPanel.add("Message", messageTabPanel);
        allInfoPanel.add("Contact",contactTabPanel);

        mainPanel.add(allInfoPanel, new GBC(0,1));

        this.pack();
        setVisible(true);
        this.setLocationRelativeTo(null);

        this.addWindowListener(this);

    }

    public DefaultListModel<String> listTitle(ArrayList<Message> messages){
        DefaultListModel<String> titleModel = new DefaultListModel<>();
        int i = 0;
        for (Message message: messages){
            i++;
            String star;
            if (message.isStared()){
                star = "*";
            }
            else {
                star = "";
            }
            titleModel.addElement( i + ") " +star+ message.getTitle() +star+ " - " + message.getAuthor());
        }
        return titleModel;
    }

    public static void main(String[] args) {
        MyFoodoraSystemGUI myFoodoraSystemGUI = MyFoodoraSystemGUI.getInstance();
        myFoodoraSystemGUI.loginUser("WDY","501");
    }

    public void enableButton(boolean state){
        star.setEnabled(state);
        delete.setEnabled(state);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        infoBoardFrame = null;
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

    public void updateInfo(){
        phoneLabel.setText("Phone: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getPhone());
        emailLabel.setText("Email: " + ((Customer) myFoodoraSystem.getCurrentUser()).infoBoard.getEmail());
    }
}
