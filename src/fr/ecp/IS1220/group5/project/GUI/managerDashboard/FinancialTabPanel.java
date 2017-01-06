package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import fr.ecp.IS1220.group5.project.MyFoodoraSystemGUI;
import fr.ecp.IS1220.group5.project.util.GBC;
import fr.ecp.IS1220.group5.project.util.Money;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;

/**
 * Created by dennis101251 on 2016/12/28.
 */
public class FinancialTabPanel extends JPanel {

    JPanel infoPanel;
        JLabel totalIncomeLabel;
        JLabel totalDeliveryCostLabel;
        JLabel totalProfitLabel;
        JLabel averageIncomeLabel;
        JLabel grossProfit;
    JPanel financialPanel;
        JPanel targetProfitPanel;
            JLabel targetProfitLabel;
            JTextField targetProfitText;
            JButton targetProfitConfirmButton;
        JPanel policyPanel;
            JLabel policyLabel;
            JComboBox policyBox;
            JButton policyConfirmButton;
    JPanel formulePanel;
        JLabel formule;
    JPanel parameterPanel;
        JPanel inputPanel;//1*3
            JPanel serviceFeePanel;
                JLabel serviceFeeLabel;
                JTextField serviceInput;
            JPanel markupPanel;
                JLabel markupLabel;
                JTextField markupInput;
            JPanel deliveryPricePanel;
                JLabel deliveryPriceLabel;
                JTextField deliveryPriceInput;
        JPanel buttonPanel;
            JButton parameterConfirmlButton;
            JButton resetButton;

    boolean point = false;


    MyFoodoraSystemGUI myFoodoraSystem = MyFoodoraSystemGUI.getInstance();

    public FinancialTabPanel(){
        super();

        this.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        Border border2 = BorderFactory.createEmptyBorder(10,2,10,2);
        Border border1 = BorderFactory.createEmptyBorder(2,2,2,2);

        //Basic information panel
        infoPanel = new JPanel();
        infoPanel.setBorder(border2);
        infoPanel.setLayout(new GridBagLayout());

        totalIncomeLabel = new JLabel("Total income: " + Money.display(myFoodoraSystem.getTotal_income()));
        totalIncomeLabel.setBorder(border1);
        totalDeliveryCostLabel = new JLabel("Total delivery cost: " + Money.display(myFoodoraSystem.getTotal_delivery_cost()));
        totalDeliveryCostLabel.setBorder(border1);
        grossProfit = new JLabel("Total gross profit: " + Money.display(myFoodoraSystem.getTotal_profit_gross()));
        grossProfit.setBorder(border1);
        totalProfitLabel = new JLabel("Total profit: " + Money.display(myFoodoraSystem.getTotal_profit()));
        totalProfitLabel.setBorder(border1);
        averageIncomeLabel = new JLabel("Average income: " + Money.display(myFoodoraSystem.getAverageIncomePerCustomer() )+ "/person");
        averageIncomeLabel.setBorder(border1);
        infoPanel.add(totalIncomeLabel,new GBC(0,0).setIpad(100,20));
        infoPanel.add(totalDeliveryCostLabel,new GBC(1,0).setIpad(100,20));
        infoPanel.add(totalProfitLabel,new GBC(0,2).setIpad(100,20));
        infoPanel.add(grossProfit, new GBC(0,1).setIpad(100,20));
        infoPanel.add(averageIncomeLabel,new GBC(1,1).setIpad(100,20));

        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        this.add(infoPanel,c);

        //Financial Panel
        financialPanel = new JPanel();
        financialPanel.setBorder(border2);
        financialPanel.setLayout(new GridLayout(2,1,10,0));

        targetProfitPanel = new JPanel();
        targetProfitPanel.setLayout(new GridLayout(1,4));
        targetProfitPanel.add(new JLabel("Target profit: "));
        targetProfitLabel = new JLabel(Money.display(myFoodoraSystem.getTarget_profit()));
        targetProfitPanel.add(targetProfitLabel);
        targetProfitText = new JTextField(myFoodoraSystem.getTarget_profit().toString());
        targetProfitText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == '.')){
                    if (keyChar == '.'){
                        if (!point){
                            point = true;
                        }
                        else {
                            e.consume();
                        }
                    }
                }else{
                    e.consume();
                }
                String input = targetProfitText.getText();
                if (input.contains(".")){
                    point = true;
                }
                else {
                    point = false;
                }
            }
        });
        targetProfitPanel.add(targetProfitText);
        targetProfitConfirmButton = new JButton("Confirm");
        targetProfitConfirmButton.setSize(30,10);
        targetProfitConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = targetProfitText.getText();
                Double targetProfit = Double.parseDouble(input);
                myFoodoraSystem.setTarget_profit(targetProfit);
                targetProfitLabel.setText(Money.display(myFoodoraSystem.getTarget_profit()));
            }
        });
        targetProfitPanel.add(targetProfitConfirmButton);
        targetProfitPanel.setBorder(border1);
        financialPanel.add(targetProfitPanel);

        policyPanel = new JPanel();
        policyPanel.setLayout(new GridLayout(1,4));
        policyPanel.add(new JLabel("Profit policy: " ));
        policyLabel = new JLabel(myFoodoraSystem.getProfitPolicy());
        policyPanel.add(policyLabel);
        String[] policy= {"by service fee","by markup percentage","by delivery price"};
        policyBox = new JComboBox(policy);
        policyPanel.add(policyBox);
        policyConfirmButton = new JButton("Show results");
        policyConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int policy = policyBox.getSelectedIndex();
                if (policy == 0|| policy== 1|| policy== 2){
                    myFoodoraSystem.setProfitPolicy(policy);
                    policyLabel.setText(myFoodoraSystem.getProfitPolicy());
                    myFoodoraSystem.saveFinancial();

                    String[] para = myFoodoraSystem.getParameter();

                    serviceInput.setText(para[0]);
                    markupInput.setText(para[1]);
                    deliveryPriceInput.setText(para[2]);
                }
                else {
                    System.out.println("invalid input");
                }
            }
        });
        policyPanel.add(policyConfirmButton);
        policyPanel.setBorder(border1);
        financialPanel.add(policyPanel);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        this.add(financialPanel,c);

        formulePanel = new JPanel(new GridBagLayout());
        formule = new JLabel("Profit = Service fee + Order price * markup  - Distance * Delivery price");
        formulePanel.add(formule,new GBC(0,0));
        this.add(formule,new GBC(0,2));

        parameterPanel = new JPanel();
        parameterPanel.setBorder(border2);
        parameterPanel.setLayout(new GridLayout(2,1));

        inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(1,3));

        serviceFeePanel = new JPanel(new GridLayout(2,1));
        serviceFeeLabel = new JLabel("Service Fee");
        serviceFeePanel.add(serviceFeeLabel);
        serviceInput = new JTextField(myFoodoraSystem.getService_fee().toString());
        serviceInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == '.')){
                    if (keyChar == '.'){
                        if (!point){
                            point = true;
                        }
                        else {
                            e.consume();
                        }
                    }
                }else{
                    e.consume();
                }
                String input = serviceInput.getText();
                if (input.contains(".")){
                    point = true;
                }
                else {
                    point = false;
                }
            }
        });
        serviceFeePanel.add(serviceInput);

        markupPanel = new JPanel(new GridLayout(2,1));
        markupLabel = new JLabel("Markup Percentage");
        markupPanel.add(markupLabel);
        markupInput = new JTextField(myFoodoraSystem.getMarkup_percentage().toString());
        markupInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == '.')){
                    if (keyChar == '.'){
                        if (!point){
                            point = true;
                        }
                        else {
                            e.consume();
                        }
                    }
                }else{
                    e.consume();
                }
                String input = markupInput.getText();
                if (input.contains(".")){
                    point = true;
                }
                else {
                    point = false;
                }
            }
        });
        markupPanel.add(markupInput);

        deliveryPricePanel = new JPanel(new GridLayout(2,1));
        deliveryPriceLabel = new JLabel("Delivery price");
        deliveryPricePanel.add(deliveryPriceLabel);
        deliveryPriceInput = new JTextField(myFoodoraSystem.getDelivery_cost_price().toString());
        deliveryPriceInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                int keyChar = e.getKeyChar();

                if((keyChar >= KeyEvent.VK_0 && keyChar <= KeyEvent.VK_9)||(keyChar == '.')){
                    if (keyChar == '.'){
                        if (!point){
                            point = true;
                        }
                        else {
                            e.consume();
                        }
                    }
                }else{
                    e.consume();
                }
                String input = deliveryPriceInput.getText();
                if (input.contains(".")){
                    point = true;
                }
                else {
                    point = false;
                }
            }
        });
        deliveryPricePanel.add(deliveryPriceInput);

        inputPanel.add(serviceFeePanel);
        inputPanel.add(markupPanel);
        inputPanel.add(deliveryPricePanel);

        parameterPanel.add(inputPanel);

        buttonPanel = new JPanel(new GridBagLayout());
        parameterConfirmlButton = new JButton("Confirm");
        parameterConfirmlButton.setSize(20,10);
        parameterConfirmlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String servicefeeString = serviceInput.getText();
                String markupString = markupInput.getText();
                String deliveryPriceString = deliveryPriceInput.getText();

                myFoodoraSystem.setService_fee(new BigDecimal(servicefeeString));
                myFoodoraSystem.setMarkup_percentage(new BigDecimal(markupString));
                myFoodoraSystem.setDelivery_cost(new BigDecimal(deliveryPriceString));

                myFoodoraSystem.saveFinancial();
            }
        });
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        buttonPanel.add(parameterConfirmlButton,c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0.5;
        c.weighty = 0;
        buttonPanel.add(Box.createGlue(),c);
        resetButton = new JButton("Reset");
        resetButton.setSize(20,10);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serviceInput.setText(myFoodoraSystem.getService_fee().toString());
                markupInput.setText(myFoodoraSystem.getMarkup_percentage().toString());
                deliveryPriceInput.setText(myFoodoraSystem.getDelivery_cost_price().toString());
            }
        });
        c.gridx = 2;
        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0;
        buttonPanel.add(resetButton,c);

        parameterPanel.add(buttonPanel);
        c.gridx = 0;
        c.gridy = 3;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 0;
        c.weighty = 0;
        this.add(parameterPanel,c);

    }
}
