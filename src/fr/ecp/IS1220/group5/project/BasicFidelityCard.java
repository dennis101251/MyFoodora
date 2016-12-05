package fr.ecp.IS1220.group5.project;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Alexandre on 18/11/2016.
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
