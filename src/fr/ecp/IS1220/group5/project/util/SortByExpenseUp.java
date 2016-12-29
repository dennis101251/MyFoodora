package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.user.Customer;

import java.util.Comparator;

/**
 * Created by dennis101251 on 2016/12/29.
 */
public class SortByExpenseUp implements Comparator<Customer> {
    @Override
    public int compare(Customer o1, Customer o2) {return (int) (o1.getTotalExpense().doubleValue() - o2.getTotalExpense().doubleValue());}
}
