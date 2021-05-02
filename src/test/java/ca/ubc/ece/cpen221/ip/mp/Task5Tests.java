package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

public class Task5Tests {

    @Test
    public void test_greenScreen1() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen1_og.png");
        Image expectedImg = new Image("resources/2092.jpg");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.white, expectedImg);
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen2() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen2_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen2_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.blue,
            new Image("resources/tests/custom/task5/greenScreen2_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen3() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen3_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen3_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.white,
            new Image("resources/tests/custom/task5/greenScreen3_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen4() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen4_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen4_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen4_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen5() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen5_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen5_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.red,
            new Image("resources/tests/custom/task5/greenScreen5_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen6() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen6_og_test.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen6_og_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen6_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen7() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen7_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen7_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen7_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen8() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen8_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen8_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen8_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen9() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen9_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen9_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen9_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen10() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen10_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen10_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen10_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen11() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen11_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen11_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen11_replace.png"));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_greenScreen12() {
        Image originalImg = new Image("resources/tests/custom/task5/greenScreen12_og.png");
        Image expectedImg = new Image("resources/tests/custom/task5/greenScreen12_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.greenScreen(Color.black,
            new Image("resources/tests/custom/task5/greenScreen12_replace.png"));
        assertEquals(expectedImg, outputImage);
    }
}
