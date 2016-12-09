package fr.ecp.IS1220.group5.project.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by dennis101251 on 2016/12/8.
 */
public class Percentage {
    public static String display(BigDecimal price){
        NumberFormat percent = NumberFormat.getPercentInstance();
        percent.setMaximumFractionDigits(3);
        return percent.format(price);
    }
}
