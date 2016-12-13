package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.menu.Food;

/**
 * <b>The class which represents the sort of food</b>
 * <p>
 *     There are 2 types of sort:
 * </p>
 *     <ul>
 *         <li>The sort of meal</li>
 *         <li>The sort of item</li>
 *     </ul>
 *
 * <p> The data structure of sort</p>
 * <p> 1st parameter: Food</p>
 * <p> 2nd parameter: Quantity</p>
 *
 * @see SortMeal
 * @see SortItem
 *
 * Created by dennis101251 on 2016/12/13.
 */
public abstract class Sort {

    private int quantity;

    public void addQuantity(){
        quantity ++;
    }

    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    public int getQuantity(){return this.quantity;}

    public abstract Food getType();
}
