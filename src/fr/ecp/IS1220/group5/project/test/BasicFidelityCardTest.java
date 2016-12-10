package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.fidelity.BasicFidelityCard;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by alexandre_carlier on 10/12/2016.
 */
public class BasicFidelityCardTest {
    @Test
    public void compute_discounted_price() throws Exception {
        BasicFidelityCard basicFidelityCard = new BasicFidelityCard();

        BigDecimal price = new BigDecimal("11.99");
        BigDecimal discountedPrice = basicFidelityCard.compute_discounted_price(price);

        Assert.assertEquals(price, discountedPrice);


    }

}