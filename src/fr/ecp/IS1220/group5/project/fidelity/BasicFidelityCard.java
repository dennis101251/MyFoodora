package fr.ecp.IS1220.group5.project.fidelity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b>The class that represents the default fidelity card of a customer.</b>
 * It extends the <b>FidelityCard</b> abstract class.
 *
 * @see FidelityCard
 */
public class BasicFidelityCard extends FidelityCard implements Serializable{
    /**
     * In the case of the basic fidelity card, the discounted price is the price itself.
     * @param price the price from which the discount is computed.
     * @return
     */
    public BigDecimal compute_discounted_price(BigDecimal price){
        return price;
    }

    @Override
    public String getFidelityCardName() {
        return "BasicFidelityCard";
    }
}
