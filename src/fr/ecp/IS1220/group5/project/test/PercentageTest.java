package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.util.Percentage;
import org.junit.Test;

import java.math.BigDecimal;

/**
 * Created by dennis101251 on 2016/12/8.
 */
public class PercentageTest {
    @Test
    public void display() throws Exception {
        System.out.println(Percentage.display(BigDecimal.valueOf(0.0002)));
        System.out.println(Percentage.display(BigDecimal.valueOf(0.000002)));
    }
}