package fr.ecp.IS1220.group5.project;

import java.util.ArrayList;

/**
 * Created by dennis101251 on 2016/11/16.
 */
public class UserlistTest {
    public static void main(String[] args) {
        Coordinate coordinate = new Coordinate(10,2);
        Customer customer = new Customer("D","Z","Z",coordinate,"X","1");
        ArrayList<User> users = new ArrayList<>();
        users.add(customer);

        Userlist userlist = new Userlist(users);
        Customer customer2 = new Customer("D","1","2",coordinate,"X","1");
        userlist.addUser(customer2);

//        System.out.println(userlist.toString());
    }
}
