package fr.ecp.IS1220.group5.project.test;

import fr.ecp.IS1220.group5.project.util.Coordinate;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alexandre on 08/12/2016.
 */
public class CoordinateTest {
    @Test
    public void getDistance() throws Exception {
        Coordinate point1 = new Coordinate(3,4);
        Coordinate point2 = new Coordinate(0,0);

        Assert.assertEquals(5, Coordinate.getDistance(point1, point2), 0.001);
    }

}