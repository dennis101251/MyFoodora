package fr.ecp.IS1220.group5.project;

import java.math.BigDecimal;

/**
 * Created by Alexandre on 18/11/2016.
 */
public abstract class FidelityCard {

    protected int points;
    public abstract BigDecimal compute_discounted_price(BigDecimal price);

    public int getPoints() {
        return points;
    }
}
