package fr.ecp.IS1220.group5.project.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * A useful class to display percentages
 */
public class Percentage {

    /**
     * Returns the percentage representation of a factor, e.g. 0,02% for 0.0002.
     * @param percent the factor to be displayed in percentage
     * @return the percent representation, as a string.
     */
    public static String display(BigDecimal percent){
        NumberFormat percentFormat = NumberFormat.getPercentInstance();
        percentFormat.setMaximumFractionDigits(3);
        return percentFormat.format(percent);
    }
}
