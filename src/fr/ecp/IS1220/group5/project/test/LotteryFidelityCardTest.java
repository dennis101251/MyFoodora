package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.fidelity.LotteryFidelityCard;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by alexandre_carlier on 10/12/2016.
 */
public class LotteryFidelityCardTest {
    /**
     * We cannot rigorously test this method as the result changes randomly, but we can compute if the average number of discounts fits to the given probability (0,01).
     *
     * More precisely, the discounted price is a random variable which has only two outputs, 0 and the initial price.
     * Thus, the expected value of this RV is 0,99*initial_price + 0,01 * 0.
     * We will compare this value to the computed one after 1000 experiences, and accept a delta of 0,1 euros.
     *
     * @throws Exception
     */
    @Test
    public void compute_discounted_price() throws Exception {

        LotteryFidelityCard lotteryFidelityCard = new LotteryFidelityCard();

        BigDecimal price = new BigDecimal("11.99");
        int nbExperiences = 1000;
        BigDecimal totalDiscountedPrice = new BigDecimal(0);

        for (int i = 0; i < nbExperiences; i++){

            totalDiscountedPrice = totalDiscountedPrice.add(lotteryFidelityCard.compute_discounted_price(price));

        }

        double averageDiscountedPrice = totalDiscountedPrice.doubleValue() / nbExperiences;
        double expectedValue = 0.99 * price.doubleValue();

        System.out.println(averageDiscountedPrice);
        System.out.println(expectedValue);

        Assert.assertEquals(expectedValue, averageDiscountedPrice, 0.1);

    }

}