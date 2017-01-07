package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.CommandLine;
import fr.ecp.IS1220.group5.project.menu.Order;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Financial;
import org.junit.Test;

/**
 * Created by dennis101251 on 2016/12/25.
 */
public class CommandLineTest {
    @Test
    public void runtest() throws Exception {
        Order.delateOrders();
        Userlist.delateUserFile();
        Financial.deleteFinancial();

        CommandLine commandLine = new CommandLine();
        commandLine.runtest("TestScenario1.txt");
    }

}