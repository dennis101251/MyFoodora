package fr.ecp.IS1220.group5.project.fidelity;

import java.math.BigDecimal;
import java.util.Random;

/**
 /**
 * <b>The class that represents the lottery fidelity card of a customer.</b>
 * It extends the <b>FidelityCard</b> abstract class.
 *
 * @see FidelityCard
 */
public class LotteryFidelityCard extends FidelityCard {
    /**
     * In the case of the lottery fidelity card, there is a 0,01 probability to gain his meal for free.
     * @param price the price from which the discount is computed.
     * @return the discounted price.
     */
    public BigDecimal compute_discounted_price(BigDecimal price){

        //Probability to gain his meal for free: 0.01

        Random random = new Random();
        if (random.nextInt(100) == 42){
            System.out.println("What a lucky guy!");
            return new BigDecimal(0);
        }else{
            return price;
        }

    }

    @Override
    public String getFidelityCardName() {
        return "LotteryFidelityCard";
    }
}
