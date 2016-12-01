package Test;
import fr.ecp.IS1220.group5.project.CommandLine;
import fr.ecp.IS1220.group5.project.Coordinate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by dennis101251 on 2016/12/1.
 */
public class CommandLineTest {

    @Ignore
    public void loginUserTest() throws Exception {
        CommandLine commandLine = new CommandLine();
        commandLine.loginUser("ly","123456");
        String username = commandLine.getUser().getUsername();
        Assert.assertTrue(username.equals("ly"));
    }

    @Ignore
    public void registerCustomerTest(){
        CommandLine commandLine = new CommandLine();
        String firstname = "Danyi";
        String lastname = "Wu";
        String username = "WDY";
        String password = "501";
        String address = "30:20";
        Coordinate coordinate = commandLine.String2Coordinate(address);
        String email = "danyi.wu@student.ecp.fr";
        String phone = "0650102722";
        commandLine.registerCustomer(firstname,lastname,username,password,coordinate,email,phone);
    }

    @Test
    public void showRestaurant(){
        new CommandLine().showResataurant();
    }
}