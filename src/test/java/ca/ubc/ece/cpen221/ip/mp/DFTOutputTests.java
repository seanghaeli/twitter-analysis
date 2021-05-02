package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

public class DFTOutputTests {

    @Test
    public void test_DFTOutput_equals() {
        double[][] testArray = new double[][] { {1, 2}, {3, 4} };
        DFTOutput dftOutput1 = new DFTOutput(testArray, testArray);
        DFTOutput dftOutput2 = new DFTOutput(new double[2][2], new double[2][2]);

        assertFalse(dftOutput1.equals(dftOutput2));
    }

    @Test
    public void test_DFTOutput_constructor() throws IllegalArgumentException {
        String message = "";
        try {
            new DFTOutput(new double[1][1], new double[2][2]);
        } catch (IllegalArgumentException e){
            message = e.getMessage();
        }
        String expectedMessage = "amplitude and phase matrices should have the same dimensions";
        assertEquals(message, expectedMessage);
    }

    @Test
    public void test_DFTOutput_epsilon() {
        double[][] lessThanEpsilon = new double[][] { {1e-8, 1e-8}, {1e-8, 1e-8} };
        DFTOutput dftOutput1 = new DFTOutput(lessThanEpsilon, lessThanEpsilon);
        DFTOutput dftOutput2 = new DFTOutput(new double[2][2], new double[2][2]);
        assertTrue(dftOutput1.equals(dftOutput2));
    }

    @Test
    public void test_DFTOutput_hashcode() {
        double[][] testArray = new double[][] { {1, 2, 3}, {4, 5, 6} };
        DFTOutput dftOutput = new DFTOutput(testArray, testArray);
        int expectedHash = 6;
        assertEquals(expectedHash, dftOutput.hashCode());
    }
}
