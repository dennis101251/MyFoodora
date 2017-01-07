package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.user.Courier;
import fr.ecp.IS1220.group5.project.user.Restaurant;
import fr.ecp.IS1220.group5.project.user.User;
import fr.ecp.IS1220.group5.project.user.Userlist;
import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by dennis101251 on 2016/12/11.
 */
public class UserlistTest {
    @Test
    public void saveUsersAndRetrieveUsers() throws Exception {
        Userlist.delateUserFile();
        Userlist userlist1 = new Userlist();

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));
        userlist1.addUser(restaurant);

        userlist1.saveUsers();

        Userlist userlist2 = new Userlist();
        userlist2.retrieveUsers();
        Assert.assertTrue(!userlist2.getUsers().isEmpty());
    }

    @Test
    public void removeUser() throws Exception {

        Userlist userlist = new Userlist();

        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));
        userlist.addUser(restaurant);

        Assert.assertTrue(userlist.getUsers().get(0).getName() == "Pizzeria");
        userlist.removeUser(restaurant);

        Assert.assertTrue(userlist.getUsers().isEmpty());
    }

    @Test
    public void updateUser() throws Exception {
        Userlist userlist = new Userlist();

        Courier courier1 = new Courier("Bill","BG","123456","Gates",new Coordinate(10,10),"123456");
        userlist.addUser(courier1);

        Assert.assertTrue(((Courier) userlist.getUsers().get(0)).getDeliveredOrdersCounter() == 0);

        courier1.setDeliveredOrdersCounter(10);

        userlist.updateUser(courier1);

        Assert.assertTrue(((Courier) userlist.getUsers().get(0)).getDeliveredOrdersCounter() == 10);
    }

    @Test
    public void delateUserFileTest(){
        Userlist userlist1 = new Userlist();
        Restaurant restaurant = new Restaurant("Pizzeria", "pizzeria", "123456", new Coordinate(1,3));
        userlist1.addUser(restaurant);

        userlist1.saveUsers();
        File file = new File("tmp/users.ser");
        Assert.assertTrue(file.exists());

        Userlist.delateUserFile();
        file = new File("tmp/users.ser");
        Assert.assertTrue(!file.exists());
    }

}