package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.menu.Foods;

/**
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

    public abstract Foods getType();
}
