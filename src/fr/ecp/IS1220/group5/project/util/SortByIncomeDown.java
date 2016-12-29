package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.user.Restaurant;

import java.util.Comparator;

/**
 * Created by dennis101251 on 2016/12/29.
 */
public class SortByIncomeDown implements Comparator<Restaurant> {
    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return (int) (o2.getIncome().doubleValue() - o1.getIncome().doubleValue());
    }
}
