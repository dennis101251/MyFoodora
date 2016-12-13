package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.menu.Item;

/**
 * Created by dennis101251 on 2016/12/13.
 */
public class SortItem extends Sort {
    private Item item;

    public SortItem(Item item){
        super();
        this.item = item;
        super.setQuantity(1);
    }

    @Override
    public Item getType(){
        return item;
    }

}
