package fr.ecp.IS1220.group5.project.fidelity;

import fr.ecp.IS1220.group5.project.util.Money;

import java.math.BigDecimal;

/**
 * <b>The class that represents the point fidelity card of a customer.</b>
 * It extends the <b>FidelityCard</b> abstract class.
 */
public class PointFidelityCard extends FidelityCard {
    /**
     * In the case of the point fidelity card, the customer gains 1 point for each 10 euros spent. Once he reaches 100 points, he receives a 10% discount.
     *
     * This method computes the discounted price, and updates the number of points.
     *
     * @param price the price from which the discount is computed.
     * @return the discounted price.
     */
    public BigDecimal compute_discounted_price(BigDecimal price){
        this.points += (price.divide(new BigDecimal(10))).intValue();

        if (this.points >= 100) {
            this.points -= 100;
            return Money.applyDiscountFactor(price, new BigDecimal("0.1"));
        }else{
            return price;
        }
    }

    @Override
    public String getFidelityCardName() {
        return "Point FidelityCard";
    }
}
