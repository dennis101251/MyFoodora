package fr.ecp.IS1220.group5.project.util;

import java.util.Comparator;

/**
 * implement the interface of comparator
 *
 * Created by dennis101251 on 2016/12/13.
 */
public class SortByQuantityUp implements Comparator<Sort> {

    @Override
    public int compare(Sort o1, Sort o2) {
        return o1.getQuantity() - o2.getQuantity();
    }
}
