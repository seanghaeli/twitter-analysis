package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

public class Task3Tests {


    @Test
    public void cossim_doubleblack() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_allblack.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_allblack.png");
        double expectedOutcome = 1;
        double outcome = ImageProcessing.cosineSimilarity(firstImg, secondImg);
        assertEquals(expectedOutcome, outcome, 0.0001);
    }

    @Test
    public void cossim_doublewhite() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_allwhite.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_allwhite.png");
        double expectedOutcome = 1;
        double outcome = ImageProcessing.cosineSimilarity(firstImg, secondImg);
        assertEquals(expectedOutcome, outcome, 0.0001);
    }

    @Test
    public void cossim_oneblack() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_allblack.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_checker.png");
        double expectedOutcome = 0;
        double outcome = ImageProcessing.cosineSimilarity(firstImg, secondImg);
        assertEquals(expectedOutcome, outcome, 0.0001);
    }

    @Test
    public void cossim_checker_contrast() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_checkerinv.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_checker.png");
        double expectedOutcome = 0;
        double outcome = ImageProcessing.cosineSimilarity(firstImg, secondImg);
        assertEquals(expectedOutcome, outcome, 0.0001);
    }

    @Test
    public void cossim_mismatched_dim_exception() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_2x2_checkerinv.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_4x2_purpleblue.png");
        String message = "";
        try {
            ImageProcessing.cosineSimilarity(firstImg, secondImg);
        } catch (IllegalArgumentException iae) {
            message = iae.getMessage();
        }
        String expectedMessage = "width and height of images must match";
        assertEquals(expectedMessage, message);
    }

    @Test
    public void cossim_4x2() {
        Image firstImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_4x2_redgreen.png");
        Image secondImg =
            new Image("resources/tests/custom/cossim/cosinesimilarity_4x2_purpleblue.png");
        double expectedOutcome = 0.89031001;
        double outcome = ImageProcessing.cosineSimilarity(firstImg, secondImg);
        assertEquals(expectedOutcome, outcome, 0.0001);
    }

    @Test
    public void rotate_30() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/12003-r30.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(30);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_45() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/12003-r45.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(45);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_75() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/12003-r75.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(75);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_180() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/12003-r180.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(180);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_54_6() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/custom/task3/12003-r54_6.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(54.6);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_90() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/custom/task3/12003-r90.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(90);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_230() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/custom/task3/12003-r230.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(230);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_289() {
        Image originalImg = new Image("resources/12003.jpg");
        Image expectedImg = new Image("resources/tests/custom/task3/12003-r289.png");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(289);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, expectedImg), 0.01);
    }

    @Test
    public void rotate_360() {
        Image originalImg = new Image("resources/12003.jpg");
        ImageTransformer t = new ImageTransformer(originalImg);
        Image outputImage = t.rotate(360);

        assertEquals(1, ImageProcessing.cosineSimilarity(outputImage, originalImg), 0.01);
    }

}
