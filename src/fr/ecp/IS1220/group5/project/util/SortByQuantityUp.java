package fr.ecp.IS1220.group5.project.util;

import java.util.Comparator;

/**
 * Created by dennis101251 on 2016/12/13.
 */
public class SortByQuantityUp implements Comparator<Sort> {

//    @Override
//    public int compare(Object o1, Object o2) {
//        Sort sort1 = (Sort) o1;
//        Sort sort2 = (Sort) o2;
//        if (sort1.getQuantity() > sort2.getQuantity()){
//            return 1;
//        }
//        else {
//            return 0;
//        }
//    }

    @Override
    public int compare(Sort o1, Sort o2) {
        return o1.getQuantity() - o2.getQuantity();
    }
}
