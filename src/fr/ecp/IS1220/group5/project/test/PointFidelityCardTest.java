package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.fidelity.BasicFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.PointFidelityCard;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 * Created by alexandre_carlier on 10/12/2016.
 */
public class PointFidelityCardTest {
    @Test
    public void when100PointsOrMoreAreOnTheFidelityCardThen10PercentDiscountOnTheNextOrder(){

        PointFidelityCard pointFidelityCard = new PointFidelityCard();
        pointFidelityCard.addPoints(100);

        BigDecimal price = new BigDecimal("50");
        BigDecimal discountedPrice = pointFidelityCard.compute_discounted_price(price);

        Assert.assertEquals(new BigDecimal("45.0"), discountedPrice);

    }

    @Test
    public void whenPriceUnder10EurosThenNoPointIsAdded(){

        PointFidelityCard pointFidelityCard = new PointFidelityCard();
        int nbPointsBefore = pointFidelityCard.getPoints();

        BigDecimal price = new BigDecimal("9.99");
        BigDecimal discountedPrice = pointFidelityCard.compute_discounted_price(price);

        int nbPointsAfter = pointFidelityCard.getPoints();

        Assert.assertEquals(nbPointsBefore, nbPointsAfter);

    }

    @Test
    public void whenPriceIsHigherThan10EurosThenPointsAreAdded(){

        PointFidelityCard pointFidelityCard = new PointFidelityCard();
        int nbPointsBefore = pointFidelityCard.getPoints();

        BigDecimal price = new BigDecimal("29.99");
        BigDecimal discountedPrice = pointFidelityCard.compute_discounted_price(price);

        int nbPointsAfter = pointFidelityCard.getPoints();

        Assert.assertEquals(nbPointsBefore + 2, nbPointsAfter);

    }

}