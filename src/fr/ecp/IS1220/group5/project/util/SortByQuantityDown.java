package fr.ecp.IS1220.group5.project.util;

import java.util.Comparator;

/**
 * Created by dennis101251 on 2016/12/13.
 */
public class SortByQuantityDown implements Comparator<Sort> {


    @Override
    public int compare(Sort o1, Sort o2) {
        return o2.getQuantity() - o1.getQuantity();
    }
}
