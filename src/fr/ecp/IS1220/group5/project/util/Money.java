package fr.ecp.IS1220.group5.project.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A useful class for money-related functions.
 */
public class Money {
    /**
     * Displays the price in euros, with the French format convention (e.g. 5,34 €)
     * @param price the price to be displayed
     * @return a string of the money representation.
     */
    public static String display(BigDecimal price){
        return NumberFormat.getCurrencyInstance(Locale.FRANCE).format(price.doubleValue());
    }

    /**
     * Applies a discount factor to a given price.
     * @param price the price to be discounted
     * @param discountFactor the discount factor to b apply to the price.
     * @return the discounted price = original_price * (1-disount_factor)
     */
    public static BigDecimal applyDiscountFactor(BigDecimal price, BigDecimal discountFactor){
        return price.subtract(price.multiply(discountFactor));
    }

}
