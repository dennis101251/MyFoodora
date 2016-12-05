package fr.ecp.IS1220.group5.project;

import java.math.BigDecimal;

/**
 * Created by Alexandre on 18/11/2016.
 */
public class PointFidelityCard extends FidelityCard {

    public BigDecimal compute_discounted_price(BigDecimal price){
        this.points += (price.divide(new BigDecimal(10))).intValue();

        if (this.points >= 100) {
            this.points -= 100;
            return Money.applyDiscountFactor(price, new BigDecimal(0.10));
        }else{
            return price;
        }
    }

    @Override
    public String getFidelityCardName() {
        return "Point FidelityCard";
    }
}
