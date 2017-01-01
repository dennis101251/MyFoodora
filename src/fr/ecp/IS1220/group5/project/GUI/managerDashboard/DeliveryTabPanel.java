package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.util.GBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by dennis101251 on 2016/12/30.
 */
public class DeliveryTabPanel extends JPanel {

    private MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    JPanel mainPanel;
    JPanel policyPanel;
        JLabel nameLabel;
        JComboBox<String> policyBox;
        JButton confirmButton;
    JPanel mapPanel;

    public DeliveryTabPanel(){
        super(new GridBagLayout());

        //Policy panel
        policyPanel = new JPanel(new GridBagLayout());
        nameLabel = new JLabel("Delivery Policy: " + myFoodoraSystem.getDeliveryPolicy());
        policyPanel.add(nameLabel,new GBC(0,0));
        String[] policy = {"Fast Delivery","Fair Occupation Delivery"};
        policyBox = new JComboBox<>(policy);
        policyBox.setBorder(BorderFactory.createEmptyBorder(0,20,0,20));
        policyPanel.add(policyBox,new GBC(1,0));
        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int policy = policyBox.getSelectedIndex();
                switch (policy){
                    case 0:
                        myFoodoraSystem.setDeliveryPolicy("fastDelivery");
                        break;
                    case 1:
                        myFoodoraSystem.setDeliveryPolicy("fairOccupationDelivery");
                        break;
                }
                nameLabel.setText("Policy: " + myFoodoraSystem.getDeliveryPolicy());
            }
        });
        policyPanel.add(confirmButton, new GBC(2,0));

        this.add(policyPanel,new GBC(0,0).setFill(GridBagConstraints.BOTH));

        this.add(new MapPanel(),new GBC(0,1));
    }
}
