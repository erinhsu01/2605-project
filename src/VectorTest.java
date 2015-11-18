/**
 * junit tests for Vector
 * Currently has:
 * 2 errors because of floating point arithmetic
 *
 * Should be fine if rounded within 12 places from the decimal
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VectorTest {
    Vector v1;
    Vector v2;
    Vector v3;
    Vector v4;
    Vector expectedVector;
    String expectedExceptionMessage;
    double delta;

    @Before
    public void setUp() throws Exception {
        // v1 = <> (no elements)
        v1 = new Vector();

        // v2 = <0, 0, 0, 0>
        v2 = new Vector(4);

        // v3 = <1.0, 2.0, 3.0, 4.0>
        double[] three = {1, 2, 3, 4};
        v3 = new Vector(three);

        // v4 = <1.5, -2.5, 3.75, 4.25>
        double[] four = {1.5, -2.5, 3.75, 4.25};
        v4 = new Vector(four);

        // difference between expected and actual (if doubles) must be within delta
        delta = 0.000001;
    }

    @Test
    public void testDot() throws Exception {
        // testing <0, 0, 0, 0>.<1, 2, 3, 4>
        assertEquals(0, v2.dot(v3), delta);

        // testing <1, 2, 3, 4>.<1.5, -2.5, 3.75, 4.25>
        assertEquals(24.75, v3.dot(v4), delta);

        // testing <>.<1, 2, 3, 4>
        expectedExceptionMessage = "Vectors must be of the same dimensions";
        String actualExceptionMessage = null;
        try {
            v1.dot(v3);
        } catch (Exception e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals("Dot product w/vectors of different sizes fails",
                expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    public void testMagnitude() throws Exception {
        // testing magnitude of <>
        assertEquals(0, v1.magnitude(), delta);

        // testing magnitude of <0, 0, 0, 0>
        assertEquals(0, v2.magnitude(), delta);

        // testing magnitude of <1.5, -2.5, 3.75, 4.25>
        assertEquals(6.373774392, v4.magnitude(), delta);
    }

    @Test
    public void testUnitVector() throws Exception {
        expectedExceptionMessage = "Vector has no direction";
        String actualExceptionMessage = null;

        // testing unit vector of <>
        try {
            v1.unitVector();
        } catch (Exception e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals("Unit vector for empty vector fails",
                expectedExceptionMessage, actualExceptionMessage);

        actualExceptionMessage = null;

        // testing unit vector of <0, 0, 0, 0>
        try {
            v2.unitVector();
        } catch (Exception e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals("Unit vector for zero vector fails",
                expectedExceptionMessage, actualExceptionMessage);

        // testing unit vector of <1, 2, 3, 4>
        double[] exp1 = {
                (1.0 / Math.sqrt(30.0)),
                Math.sqrt(2.0 / 15),
                Math.sqrt(3.0 / 10),
                (2 * Math.sqrt(2.0 / 15))
        };
        expectedVector = new Vector(exp1);
        assertEquals("Unit vector method fails", expectedVector,
                v3.unitVector());

        // testing unit vector of <1.5, -2.5, 3.75, 4.25>
        double[] exp2 = {
                0.235339362165487830464,
                -0.39223227027581305077,
                0.588348405413719576,
                0.6667948594688821863
        };
        expectedVector = new Vector(exp2);
        assertEquals("Unit vector method fails", expectedVector,
                v4.unitVector());
    }

    @Test
    public void testProjectedOnto() throws Exception {

    }

    @Test
    public void testPlus() throws Exception {
        // testing <0, 0, 0, 0> + <1, 2, 3, 4>
        double[] exp = {1, 2, 3, 4};
        expectedVector = new Vector(exp);
        Assert.assertEquals("Adding vectors totally wrong",
                expectedVector, v2.plus(v3));

        // testing <1, 2, 3, 4> + <1.5, -2.5, 3.75, 4.25>
        double[] exp2 = {2.5, -0.5, 6.75, 8.25};
        expectedVector = new Vector(exp2);
        Assert.assertEquals("Adding vectors fails",
                expectedVector, v3.plus(v4));

        // testing <> + <1.5, -2.5, 3.75, 4.25>
        expectedExceptionMessage = "Vectors must be of the same dimensions";
        String actualExceptionMessage = null;
        try {
            v1.plus(v4);
        } catch (Exception e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals("Adding vectors w/ different sizes fails",
                expectedExceptionMessage, actualExceptionMessage);

    }

    @Test
    public void testMinus() throws Exception {
        // testing <0, 0, 0, 0> - <1, 2, 3, 4>
        double[] exp = {-1, -2, -3, -4};
        expectedVector = new Vector(exp);
        assertEquals("Minus method fails", expectedVector, v2.minus(v3));

        // testing <1.5, -2.5, 3.75, 4.25> - <1, 2, 3, 4>
        double[] exp2 = {0.5, -4.5, 0.75, 0.25};
        expectedVector = new Vector(exp2);
        assertEquals("Minus method fails", expectedVector, v4.minus(v3));

        // testing <> - <1.5, -2.5, 3.75, 4.25>
        expectedExceptionMessage = "Vectors must be of the same dimensions";
        String actualExceptionMessage = null;
        try {
            v1.minus(v4);
        } catch (Exception e) {
            actualExceptionMessage = e.getMessage();
        }
        assertEquals("Minus method for vectors w/ different sizes fails",
                expectedExceptionMessage, actualExceptionMessage);
    }

    @Test
    public void testTimes() throws Exception {
        // testing <0, 0, 0, 0> * 4
        expectedVector = new Vector(4);
        Assert.assertEquals("Multiplying by scalar doesn't work with 0 vector",
                expectedVector, v2.times(4.5));

        // testing <1.5, -2.5, 3.75, 4.25> * -1
        double[] exp1 = {-1.5, 2.5, -3.75, -4.25};
        expectedVector = new Vector(exp1);
        Assert.assertEquals("Multiplying by a scalar fails for negative numbers",
                expectedVector, v4.times(-1));

        // testing <1, 2, 3, 4> * 10.1
        double[] exp2 = {10.1, 20.2, 30.3, 40.4};
        expectedVector = new Vector(exp2);
        Assert.assertEquals("Multiplying by scalar fails",
                expectedVector, v3.times(10.1));
    }

    @Test
    public void testToMatrix() throws Exception {
        Matrix expectedMatrix;

        // testing <> to matrix
        expectedMatrix = new Matrix();
        assertEquals("Converting empty vector to matrix fails",
                expectedMatrix, v1.toMatrix());

        // testing <0, 0, 0, 0> to matrix
        expectedMatrix = new Matrix(4, 1);
        assertEquals("Converting zero vector to matrix fails",
                expectedMatrix, v2.toMatrix());

        // testing <1.5, -2.5, 3.75, 4.25> to matrix
        double[][] exp = {{1.5}, {-2.5}, {3.75}, {4.25}};
        expectedMatrix = new Matrix(exp);
        assertEquals("Converting vector to matrix fails",
                expectedMatrix, v4.toMatrix());
    }
}