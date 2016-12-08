package fr.ecp.IS1220.group5.project;

import java.math.BigDecimal;

/**
 * <b>The class that represents a Fidelity card</b>
 * <p>
 *     There are 3 differents types of cards:
 * </p>
 *     <ul>
 *         <li>The Basic Fidelity Card</li>
 *         <li>The Point Fidelity Card</li>
 *         <li>The Lottery Fidelity Card</li>
 *     </ul>
 *
 *
 * @see BasicFidelityCard
 * @see PointFidelityCard
 * @see LotteryFidelityCard
 */
public abstract class FidelityCard {

    protected int points = 0;
    public abstract BigDecimal compute_discounted_price(BigDecimal price);

    public void addPoints(int newPoints){points = this.points + newPoints;}
    public int getPoints() {
        return points;
    }
    public String getFidelityCardName(){return "FidelityCard";}
}
