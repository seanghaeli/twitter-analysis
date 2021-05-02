package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import static org.junit.Assert.*;

public class Task4Tests {

    @Test
    public void dft_allblack() {
        Image originalImg = new Image("resources/tests/custom/task4/dft_test_allblack.png");
        double[][] phase = {{0.0, 0.0}, {0.0, 0.0}};
        double[][] magnitude = {{0.0, 0.0}, {0.0, 0.0}};
        ImageTransformer t = new ImageTransformer(originalImg);
        assertEquals(new DFTOutput(magnitude, phase), t.dft());
    }

    @Test
    public void dft_allwhite() {
        Image originalImg = new Image("resources/tests/custom/task4/allwhite.png");
        double[][] phase = {{0.0, 0.0}, {0.0, 0.0}};
        double[][] magnitude = {{1020.0, 0.0}, {0.0, 0.0}};
        ImageTransformer t = new ImageTransformer(originalImg);
        assertEquals(new DFTOutput(magnitude, phase), t.dft());
    }

}
