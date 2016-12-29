package fr.ecp.IS1220.group5.project.GUI.managerDashboard;

import javax.swing.*;

/**
 * Created by dennis101251 on 2016/12/28.
 */
public class DefaultInfoPanel extends JPanel {
    public DefaultInfoPanel(){
        super();
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        JLabel text = new JLabel("No user has been selected");
        this.add(text);
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
        this.add(new JLabel(" "));
    }
}
