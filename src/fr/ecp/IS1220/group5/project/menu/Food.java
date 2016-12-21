package fr.ecp.IS1220.group5.project.menu;

import java.math.BigDecimal;

/**
 *
 * In order to sort the list of item and meals in a more general way
 * We create the interface Food to deal with this problem
 *
 * @see Item
 * @see Meal
 *
 * Created by dennis101251 on 2016/12/13.
 */
public interface Food {
    public String getName();

    public BigDecimal getPrice();

}
