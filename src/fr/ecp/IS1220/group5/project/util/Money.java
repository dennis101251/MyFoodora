package fr.ecp.IS1220.group5.project.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Alexandre on 18/11/2016.
 */
public class Money {

    public static String display(BigDecimal price){
        return NumberFormat.getCurrencyInstance(Locale.FRANCE).format(price.doubleValue());
    }

    public static BigDecimal applyDiscountFactor(BigDecimal price, BigDecimal discountFactor){
        return price.subtract(price.multiply(discountFactor));
    }

}
