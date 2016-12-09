package fr.ecp.IS1220.group5.project.old_classes;

import fr.ecp.IS1220.group5.project.old_classes.CustomerFactory;
import fr.ecp.IS1220.group5.project.user.Customer;

import java.io.IOException;

/**
 * Created by dennis101251 on 2016/11/18.
 */
public class CustomerFactoryTest {
    public static void main(String[] args) throws IOException {

        /**
         * Write a console line app to test the CustomerFactory class
         */

        CustomerFactory customerFactory = new CustomerFactory();
        Customer customer = (Customer) customerFactory.createUser();
        if (customer == null){
            System.out.println("no customer");
        }
        else {
            System.out.println(customer.toString());;
        }
    }
}
