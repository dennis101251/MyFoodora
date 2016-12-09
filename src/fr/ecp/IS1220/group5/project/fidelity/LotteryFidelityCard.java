package fr.ecp.IS1220.group5.project.fidelity;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Alexandre on 18/11/2016.
 */
public class LotteryFidelityCard extends FidelityCard {

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
        return "Lottery FidelityCard";
    }
}
