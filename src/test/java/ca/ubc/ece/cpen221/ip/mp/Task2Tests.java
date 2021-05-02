package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

public class Task2Tests {

    @Test
    public void test_denoise_red() {
        Image originalImg = new Image("resources/tests/custom/task2/3x3_red.png");
        Image expectedImg = new Image("resources/tests/custom/task2/3x3_red_denoised.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.denoise();
        assertTrue(compareImages(outputImage, expectedImg));
    }

    @Test
    public void test_weather() {
        Image originalImg = new Image("resources/95006.jpg");
        Image expectedImg = new Image("resources/tests/95006-weathered.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.weather();
        assertTrue(compareImages(outputImage, expectedImg));
    }

    @Test
    public void test_blockPaint_3x3() {
        Image originalImg = new Image("resources/216053.jpg");
        Image expectedImg = new Image("resources/tests/216053-seurat-3x3.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.blockPaint(3);
        assertTrue(compareImages(outputImage, expectedImg));
    }

    @Test
    public void test_blockPaint_4x4() {
        Image originalImg = new Image("resources/95006.jpg");
        Image expectedImg = new Image("resources/tests/95006-seurat-4x4.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.blockPaint(4);
        assertTrue(compareImages(outputImage, expectedImg));
    }

    @Test
    public void test_blockPaint_3x3_custom() {
        Image originalImg = new Image("resources/tests/custom/task2/4by4solidblocks.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.blockPaint(3);
        assertTrue(compareImages(outputImage, originalImg));
    }

    private boolean compareImages(Image inImg, Image outImg) {

        if (inImg.width() != outImg.width() || inImg.height() != outImg.height()) {
            return false;
        }

        for (int col = 0; col < inImg.width(); col++) {
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
