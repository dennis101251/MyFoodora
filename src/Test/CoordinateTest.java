package Test;

import fr.ecp.IS1220.group5.project.Coordinate;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dennis101251 on 2016/12/8.
 */
public class CoordinateTest {
    @Test
    public void getDistance() throws Exception {
        Coordinate coordinate1 = new Coordinate(3,4);
        Coordinate coordinate2 = new Coordinate(0,0);

        Assert.assertEquals(Coordinate.getDistance(coordinate1,coordinate2),5,0.1);
    }

}