package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;

/**
 * This class provides some simple operations involving
 * more than one image.
 */
public class ImageProcessing {

    /**
     * Compute the cosine similarity between two images.
     *
     * @param img1: the first image, is not null.
     * @param img2: the second image, in not null and matches img1 in dimensions.
     * @return the cosine similarity between the Images.
     * referenced by img1 and img2. If one of two inputs is black,
     * returns 0, if both inputs black, returns 1.
     */
    public static double cosineSimilarity(Image img1, Image img2) {

        if ((img1.width() != img2.width()) || (img1.height() != img2.height())) {
            throw new IllegalArgumentException("width and height of images must match");
        }

        ImageTransformer imgA = new ImageTransformer(img1);
        ImageTransformer imgB = new ImageTransformer(img2);

        Image imgAGray = imgA.grayscale();
        Image imgBGray = imgB.grayscale();

        int width = img1.width();
        int height = img2.height();

        long topSum = 0;
        long bottomSumFirst = 0;
        long bottomSumSecond = 0;

        boolean firstBlack = checkIfBlack(imgAGray);
        boolean secondBlack = checkIfBlack(imgBGray);

        if (firstBlack && secondBlack) {
            return 1;
        } else if (firstBlack) {
            return 0;
        } else if (secondBlack) {
            return 0;
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {

                int intAColor = imgAGray.get(col, row).getRed();
                int intBColor = imgBGray.get(col, row).getRed();

                topSum += intAColor * intBColor;
                bottomSumFirst += Math.pow(intAColor, 2);
                bottomSumSecond += Math.pow(intBColor, 2);
            }
        }

        if ((bottomSumFirst > 0.0001 && bottomSumSecond > 0.0001)) {
            return (double) topSum / (Math.sqrt(bottomSumFirst) * Math.sqrt(bottomSumSecond));
        } else {
            return 0;
        }
    }

    /**
     * Checks if an image contains only black pixels
     *
     * @param img is a grayscale image (i.e. the red, green, and blue channel values are the same
     *            for every pixel)
     * @return true if the image is black, and false otherwise
     */
    private static boolean checkIfBlack(Image img) {
        for (int col = 0; col < img.width(); col++) {
            for (int row = 0; row < img.height(); row++) {
                if (img.get(col, row).getRed() != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
