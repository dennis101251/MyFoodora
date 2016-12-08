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

    public BigDecimal compute_discounted_price(BigDecimal price){
        return price;
    }

    @Override
    public String getFidelityCardName() {
        return "Basic FidelityCard";
    }
}
