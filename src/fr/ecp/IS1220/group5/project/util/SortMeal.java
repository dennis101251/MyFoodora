package fr.ecp.IS1220.group5.project.util;

import fr.ecp.IS1220.group5.project.menu.Meal;

/**
 * Created by dennis101251 on 2016/12/13.
 */
public class SortMeal extends Sort{
    private Meal meal;

    public SortMeal(Meal meal) {
        super();
        super.setQuantity(1);
        this.meal = meal;
    }

    @Override
    public Meal getType(){
        return meal;
    }

}
