package fr.ecp.IS1220.group5.project;

import java.math.BigDecimal;

/**
 * Created by Alexandre on 18/11/2016.
 */
public abstract class FidelityCard {

    protected int points = 0;
    public abstract BigDecimal compute_discounted_price(BigDecimal price);

    public void addPoints(int newPoints){points = this.points + newPoints;}
    public int getPoints() {
        return points;
    }
    public String getFidelityCardName(){return "FidelityCard";}
}
