package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.user.Courier;

import java.util.Comparator;

/**
 * Created by dennis101251 on 2016/12/29.
 */
public class SortByOrderUp implements Comparator<Courier> {
    @Override
    public int compare(Courier o1, Courier o2) {
        return  (o1.getDeliveredOrdersCounter() - o2.getDeliveredOrdersCounter());
    }
}
