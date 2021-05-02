package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class Task1Tests {

    @Test
    public void test_Mirroring1() {
        Image originalImg = new Image("resources/tests/custom/task1/mirror1_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/mirror1_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.mirror();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Mirroring2() {
        Image originalImg = new Image("resources/tests/custom/task1/mirror2.png");
        Image expectedImg = new Image("resources/tests/custom/task1/mirror2.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.mirror();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Mirroring3() {
        Image originalImg = new Image("resources/tests/custom/task1/mirror2.png");
        Image expectedImg = new Image("resources/tests/custom/task1/mirror2.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.mirror();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Negative1() {
        Image originalImg = new Image("resources/tests/custom/task1/negative1_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/negative1_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.negative();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Negative2() {
        Image originalImg = new Image("resources/tests/custom/task1/negative2_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/negative2_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.negative();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Posterize1() {
        Image originalImg = new Image("resources/tests/custom/task1/posterize1_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/posterize1_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.posterize();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Posterize2() {
        Image originalImg = new Image("resources/tests/custom/task1/posterize2_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/posterize2_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.posterize();
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Clip1() throws ImageProcessingException {
        Image originalImg = new Image("resources/tests/custom/task1/clip1_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/clip1_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage;
        outputImage = t.clip(new Rectangle(0, 0, 2, 2));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Clip2() throws ImageProcessingException {
        Image originalImg = new Image("resources/tests/custom/task1/clip2_og.png");
        Image expectedImg = new Image("resources/tests/custom/task1/clip2_test.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage;
        outputImage = t.clip(new Rectangle(1, 1, 2, 4));
        assertEquals(expectedImg, outputImage);
    }

    @Test
    public void test_Clip_throw() throws ImageProcessingException {
        Image originalImg = new Image("resources/tests/custom/task1/clip1_og.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        String message = "";
        try {
            t.clip(new Rectangle(0, 0, 10, 10));
        } catch (ImageProcessingException e){
            message = e.getMessage();
        }
        String expectedMessage = "Specified rectangle does not fit completely within the image.";
        assertEquals(message, expectedMessage);
    }

    private boolean compareImages(Image inImg, Image outImg) {

        if(inImg.width() != outImg.width() || inImg.height() != outImg.height()) {
            return false;
        }

        for(int col = 0; col < inImg.width(); col++) {
            for (int row = 0; row < inImg.height(); row++) {
                Color color = inImg.get(col, row);
                if (!color.equals(outImg.get(col, row))) {
                    return false;
                }
            }
        }
        return true;
    }
}
