package fr.ecp.IS1220.group5.project.fidelity;

import fr.ecp.IS1220.group5.project.fidelity.BasicFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.LotteryFidelityCard;
import fr.ecp.IS1220.group5.project.fidelity.PointFidelityCard;

import java.io.Serializable;
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
public abstract class FidelityCard implements Serializable {

    private static final long serialVersionUID = 2185346276548559322L;
    /**
     * the number of collected points.
     */
    protected int points = 0;

    /**
     * Computes the discounted price for a given price and updates the number of points (1 point for each 10 euros spent).
     * @param price the price from which the discount is computed.
     * @return the discounted price.
     */
    public abstract BigDecimal compute_discounted_price(BigDecimal price);

    /**
     * Adds a number of points to the fidelity card.
     * @param newPoints the number of points to be added to the card.
     */
    public void addPoints(int newPoints){points = this.points + newPoints;}

    /**
     * Return the number of points on the fidelity card.
     * @return the number of points on the fidelity card.
     */
    public int getPoints() {
        return points;
    }

    /**
     * Returns the selected program for the fidelity card.
     * @return the name of the fidelity card.
     */
    public abstract String getFidelityCardName();
}
