package ca.ubc.ece.cpen221.ip.mp;

import ca.ubc.ece.cpen221.ip.core.Image;
import ca.ubc.ece.cpen221.ip.core.ImageProcessingException;
import ca.ubc.ece.cpen221.ip.core.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This datatype (or class) provides operations for transforming an image.
 *
 * <p>The operations supported are:
 * <ul>
 *     <li>The {@code ImageTransformer} constructor generates an instance of an image that
 *     we would like to transform;</li>
 *     <li></li>
 * </ul>
 * </p>
 */

public class ImageTransformer {

    private Image image;
    private int width;
    private int height;

    /**
     * Creates an ImageTransformer with an image. The provided image is
     * <strong>never</strong> changed by any of the operations.
     *
     * @param img is not null
     */
    public ImageTransformer(Image img) {
        this.image = img;
        this.width = img.width();
        this.height = img.height();
    }

    /**
     * Obtain the grayscale version of the image.
     *
     * @return the grayscale version of the instance.
     */
    public Image grayscale() {
        Image gsImage = new Image(width, height);
        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                Color color = image.get(col, row);
                Color gray = Image.toGray(color);
                gsImage.set(col, row, gray);
            }
        }
        return gsImage;
    }

    /**
     * Returns the mirror image of an instance.
     *
     * @return the mirror image of the instance.
     */
    public Image mirror() {

        Image flipImg = new Image(width, height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                flipImg.setRGB(i, j, image.getRGB(width - 1 - i, j));
            }
        }
        return flipImg;
    }

    /**
     * <p>Returns the negative version of an instance.<br />
     * If the colour of a pixel is (r, g, b) then the colours of the same pixel
     * in the negative of the image are (255-r, 255-g, 255-b).</p>
     *
     * @return the negative of the instance.
     */
    public Image negative() {

        Image negImg = new Image(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color color = image.get(i, j);
                int newR = 255 - color.getRed();
                int newG = 255 - color.getGreen();
                int newB = 255 - color.getBlue();
                negImg.set(i, j, new Color(newR, newG, newB));
            }
        }

        return negImg;
    }

    /**
     * <p>Returns the posterized version of an instance.<br />
     * For each pixel, each colour is analyzed independently to produce a new image as follows:
     * <ul>
     * <li>if the value of the colour is between 0 and 64 (limits inclusive), set it to 32;</li>
     * <li>if the value of the colour is between 65 and 128, set it to 96;</li>
     * <li>if the value of the colour is between 129 and 255, set it to 222.</li>
     * </ul>
     * </p>
     *
     * @return the posterized version of the instance.
     */
    public Image posterize() {

        Image postImg = new Image(width, height);

        int r, g, b;
        Color color;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                color = image.get(col, row);
                r = color.getRed();
                g = color.getGreen();
                b = color.getBlue();

                r = posterizeValue(r);
                g = posterizeValue(g);
                b = posterizeValue(b);

                postImg.set(col, row, new Color(r, g, b));
            }
        }
        return postImg;
    }

    /**
     * <p>Returns the posterized channel value according to the specification for the posterize method</p>
     *
     * @param channel >= 0 && <= 255
     * @return the posterized channel value
     */
    private int posterizeValue(int channel) {

        if (channel <= 64) {
            return 32;
        } else if (channel <= 128) {
            return 96;
        } else {
            return 222;
        }
    }

    /**
     * Clip the image given a rectangle that represents the region to be retained.
     *
     * @param clippingBox is not null.
     * @return a clipped version of the instance.
     * @throws ImageProcessingException if the clippingBox does not fit completely
     *                                  within the image.
     */
    public Image clip(Rectangle clippingBox) throws ImageProcessingException {

        int rectWidth = clippingBox.xBottomRight - clippingBox.xTopLeft;
        int rectHeight = clippingBox.yBottomRight - clippingBox.yTopLeft;
        Image clipImg = new Image(rectWidth + 1, rectHeight + 1);

        if (clippingBox.xBottomRight >= image.width() || clippingBox.xTopLeft >= image.width()
            || clippingBox.yTopLeft >= image.height() ||
            clippingBox.yBottomRight >= image.height()) {
            throw new ImageProcessingException("Specified rectangle " +
                "does not fit completely within the image.");
        }

        for (int i = 0; i <= rectWidth; i++) {
            for (int j = 0; j <= rectHeight; j++) {
                clipImg.set(i, j, image.get(clippingBox.xTopLeft + i, clippingBox.yTopLeft + j));
            }
        }

        return clipImg;
    }

    /**
     * Denoise an image by replacing each pixel by the median value of that pixel and
     * all its neighbouring pixels. During this process, each colour channel is handled
     * separately.
     *
     * @return a denoised version of the instance.
     */
    public Image denoise() {

        Image output = new Image(width, height);

        int[][] redValues = new int[width][height];
        int[][] greenValues = new int[width][height];
        int[][] blueValues = new int[width][height];

        int red, green, blue;
        Color color;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                color = image.get(col, row);
                redValues[col][row] = color.getRed();
                greenValues[col][row] = color.getGreen();
                blueValues[col][row] = color.getBlue();
            }
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {

                red = getMedian(neighbouringPixels(redValues, col, row));
                green = getMedian(neighbouringPixels(greenValues, col, row));
                blue = getMedian(neighbouringPixels(blueValues, col, row));

                output.set(col, row, new Color(red, green, blue));
            }
        }

        return output;
    }

    /**
     * Returns the median value of an ArrayList containing integers.
     *
     * @param arrayList is not null or empty
     * @return the median value in the ArrayList
     */
    private int getMedian(ArrayList<Integer> arrayList) {

        arrayList.sort(Comparator.naturalOrder());
        int arraySize = arrayList.size();

        if (arraySize % 2 == 0) {
            return (arrayList.get(arraySize / 2) + arrayList.get(arraySize / 2 - 1)) / 2;
        } else {
            return (arrayList.get(arraySize / 2));
        }
    }


    /**
     * Returns an ArrayList containing the integers surrounding (and including) an integer at a
     * specified position in an array, in natural order (i.e. ascending order)
     *
     * @param values is not null and has the same dimensions as the image
     * @param col    is between 0 and width - 1
     * @param row    is between 0 and height - 1
     * @return an ArrayList containing the integers surrounding and including an element, with the
     * integers sorted in ascending order
     */
    private ArrayList<Integer> neighbouringPixels(int[][] values, int col, int row) {

        ArrayList<Integer> neighbours = new ArrayList<>();

        for (int i = row - 1; i <= row + 1; i++) {
            if (i >= 0 && i < height) {
                for (int j = col - 1; j <= col + 1; j++) {
                    if (j >= 0 && j < width) {
                        neighbours.add(values[j][i]);
                    }
                }
            }
        }

        neighbours.sort(Comparator.naturalOrder());
        return neighbours;
    }

    /**
     * Returns a weathered version of the image by replacing each pixel by the minimum value
     * of that pixel and all its neighbouring pixels. During this process, each colour channel
     * is handled separately.
     *
     * @return a weathered version of the image.
     */
    public Image weather() {

        Image weatheredImg = new Image(width, height);

        int[][] redValues = new int[width][height];
        int[][] greenValues = new int[width][height];
        int[][] blueValues = new int[width][height];

        int red, green, blue;
        Color color;

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {
                color = image.get(col, row);
                redValues[col][row] = color.getRed();
                greenValues[col][row] = color.getGreen();
                blueValues[col][row] = color.getBlue();
            }
        }

        for (int col = 0; col < width; col++) {
            for (int row = 0; row < height; row++) {

                red = neighbouringPixels(redValues, col, row).get(0);
                green = neighbouringPixels(greenValues, col, row).get(0);
                blue = neighbouringPixels(blueValues, col, row).get(0);

                weatheredImg.set(col, row, new Color(red, green, blue));
            }
        }

        return weatheredImg;
    }

    /**
     * Return a block paint version of the instance by treating the image as a
     * sequence of squares of a given size and replacing all pixels in a square
     * by the average value of all pixels in that square.
     * During this process, each colour channel is handled separately.
     *
     * @param blockSize the dimension of the square block, > 1.
     * @return the block paint version of the instance.
     * When the original image is not a perfect multiple of blockSize * blockSize,
     * the bottom rows and right columns are obtained by averaging the pixels that
     * fit the smaller rectangular regions. For example, if we have a 642 x 642 size
     * original image and the block size is 4 x 4 then the bottom two rows will use
     * 2 x 4 blocks, the rightmost two columns will use 4 x 2 blocks, and the
     * bottom-right corner will use a 2 x 2 block.
     * <p>
     * When the block is larger than the entire image, every pixel in the image will be replaced
     * with the average value of all of the pixels.
     */
    public Image blockPaint(int blockSize) {

        Image outImg = new Image(width, height);

        Color avgColor;

        for (int col = 0; col < width; col += blockSize) {
            for (int row = 0; row < height; row += blockSize) {

                avgColor = averageColor(col, row, blockSize);

                for (int i = col; i < (Math.min(col + blockSize, width)); i++) {
                    for (int j = row; j < (Math.min(row + blockSize, height)); j++) {
                        outImg.set(i, j, avgColor);
                    }
                }
            }
        }

        return outImg;
    }

    /**
     * Finds the average color (i.e. a color with the average red, green, and blue channel values)
     * in a block of pixels at a specified position in the image.
     *
     * @param col       is between 0 and width - 1 (inclusive)
     * @param row       is between 0 and height - 1 (inclusive)
     * @param blockSize is greater than 1
     * @return the average color of the pixels in the specified block. If blockSize is greater than
     * the height and width of the image, returns the average of every pixel in the image.
     */
    private Color averageColor(int col, int row, int blockSize) {

        Color color;
        int red, green, blue, counter;
        red = green = blue = counter = 0;

        for (int i = col; i < (Math.min(col + blockSize, width)); i++) {
            for (int j = row; j < (Math.min(row + blockSize, height)); j++) {

                color = image.get(i, j);
                red += color.getRed();
                green += color.getGreen();
                blue += color.getBlue();
                counter++;

            }
        }

        return new Color(red / counter, green / counter, blue / counter);
    }

    /**
     * Rotate an image by the given angle (degrees) clockwise about the centre of the image.
     * The centre of an image is the pixel at (width/2, height/2). The new regions
     * that may be created are given the colour white (<code>#ffffff</code>) with
     * maximum transparency (alpha = 255).
     *
     * @param degrees the angle to rotate the image by, 0 <= degrees <= 360.
     * @return a rotate version of the instance.
     */
    public Image rotate(double degrees) {

        double[] newUpperRight = rotatePointDouble(width / 2.0, height / 2.0, -degrees);
        double[] newLowerRight = rotatePointDouble(width / 2.0, -height / 2.0, -degrees);
        double[] newLowerLeft = rotatePointDouble(-width / 2.0, -height / 2.0, -degrees);
        double[] newUpperLeft = rotatePointDouble(-width / 2.0, height / 2.0, -degrees);

        int newHeight;
        int newWidth;

        if (degrees <= 90 || (degrees > 180 && degrees <= 270)) {
            newHeight = (int) (Math.abs(Math.floor(newUpperRight[1])) +
                Math.abs(Math.floor(newLowerLeft[1])));
            newWidth = (int) (Math.abs(Math.floor(newLowerRight[0])) +
                Math.abs(Math.floor(newUpperLeft[0])));
        } else {
            newHeight = (int) (Math.abs(Math.floor(newLowerRight[1])) +
                Math.abs(Math.floor(newUpperLeft[1])));
            newWidth = (int) (Math.abs(Math.floor(newUpperRight[0])) +
                Math.abs(Math.floor(newLowerLeft[0])));
        }

        int oldHeight = height;
        int oldWidth = width;

        Image outImage = new Image(newWidth, newHeight);

        double[] originalCoord;
        int original_x, original_y;

        for (int col = 0; col < newWidth; col++) {
            for (int row = 0; row < newHeight; row++) {

                originalCoord = rotatePointInt(col - newWidth / 2, row - newHeight / 2, degrees);
                original_x = (int) (originalCoord[0] + oldWidth / 2);
                original_y = (int) (originalCoord[1] + oldHeight / 2);


                if (original_x >= 0
                    && original_y >= 0
                    && original_x < oldWidth
                    && original_y < oldHeight) {
                    outImage.set(col, row, image.get(original_x, original_y));
                } else {
                    outImage.set(col, row, Color.WHITE);
                }
            }
        }

        return outImage;
    }

    /**
     * Rotates a point (x, y) clockwise by theta degrees and returns the new coordinates of the
     * point.
     *
     * @param x is a double
     * @param y is a double
     * @return a double array containing the x and y coordinates of the point after rotation
     */
    private double[] rotatePointDouble(double x, double y, double theta) {
        double newX = x * Math.cos(theta * Math.PI / 180) + y * Math.sin(theta * Math.PI / 180);
        double newY = y * Math.cos(theta * Math.PI / 180) - x * Math.sin(theta * Math.PI / 180);
        return new double[] {newX, newY};
    }

    /**
     * Rotates a point (x, y) clockwise by theta degrees and returns the new coordinates of the
     * point.
     *
     * @param x is an integer
     * @param y is an integer
     * @return a double array containing the x and y coordinates of the point after rotation
     */
    private double[] rotatePointInt(int x, int y, double theta) {
        double newX = x * Math.cos(theta * Math.PI / 180) + y * Math.sin(theta * Math.PI / 180);
        double newY = y * Math.cos(theta * Math.PI / 180) - x * Math.sin(theta * Math.PI / 180);
        return new double[] {newX, newY};
    }

    /**
     * Compute the discrete Fourier transform of the image and return the
     * amplitude and phase matrices as a DFTOutput instance. The values in phase are
     * between -pi and pi. If both the real and complex components of a complex number are zero,
     * the phase of that number is set to 0.
     *
     * @return the amplitude and phase of the DFT of the instance.
     */
    public DFTOutput dft() {
        double[][] magnitude = new double[width][height];
        double[][] phase = new double[width][height];

        int intensity;
        double a, b;

        ImageTransformer img = new ImageTransformer(image);
        Image grayImg = img.grayscale();

        for (int u = 0; u < width; u++) {
            for (int v = 0; v < height; v++) {

                a = b = 0;

                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        intensity = grayImg.get(i, j).getRed();
                        a += intensity * Math.cos(
                            2 * Math.PI * ((double) (u * i) / width + (double) (v * j) / height));
                        b += intensity * -Math.sin(
                            2 * Math.PI * ((double) (u * i) / width + (double) (v * j) / height));
                    }
                }

                magnitude[u][v] = Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));

                if (Math.abs(a) < Math.pow(10, -7)) {
                    if (Math.abs(b) < Math.pow(10, -7)) {
                        phase[u][v] = 0;
                    } else if (b > 0) {
                        phase[u][v] = Math.PI / 2;
                    } else {
                        phase[u][v] = -Math.PI / 2;
                    }

                } else {
                    phase[u][v] = Math.atan(b / a);
                }
            }
        }

        return new DFTOutput(magnitude, phase);
    }


    /**
     * Checks to see if the current pixel (x, y) is the correct colour, and if
     * any neighbouring pixels are part of the current region being searched
     *
     * @param c       the colour being checked for the region
     * @param checked 2d array of pixel locations that have been checked and confirmed
     *                for neighbouring pixels corresponding to the colour and region
     * @return whether or not there is a neighbouring pixel in region being searched
     */
    private boolean checkNeighbours(int x, int y, Color c, boolean[][] checked) {
        if (image.getRGB(x, y) == c.getRGB() && !checked[x][y]) {
            for (int i = x - 1; i <= x + 1; i++) {
                for (int j = y - 1; j <= y + 1; j++) {
                    if (i >= 0 && i < width && j >= 0 && j < height) {
                        if (checked[i][j]) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Replaces a background screen with a provided image.
     * <p>
     * This operation identifies the largest connected region of the image that matches
     * <code>screenColour</code> exactly. This operation determines a rectangle that bounds
     * the "green screen" region and overlays the <code>backgroundImage</code> over that
     * rectangle by aligning the top-left corner of the image with the top-left corner of the
     * rectangle. After determining the screen region, all pixels in that region matching
     * <code>screenColour</code> are replaced with corresponding pixels from
     * <code>backgroundImage</code>.
     * <p>
     * If <code>backgroundImage</code> is smaller
     * than the screen then the image is tiled over the screen.
     *
     * @param screenColour    the colour of the background screen, is not null
     * @param backgroundImage the image to replace the screen with, is not null
     * @return an image with provided image replacing the background screen
     * of the specified colour, tiling the screen with the background image if the
     * background image is smaller than the screen size.
     */
    public Image greenScreen(Color screenColour, Image backgroundImage) {

        Image screenImage = new Image(image);
        boolean[][] checked = new boolean[width][height];
        boolean[][] current = new boolean[width][height];
        boolean[][] largest = new boolean[width][height];
        int currentCount = 0;
        int largestCount = 0;
        int x_top = 0, x_bottom = 0, y_top = 0, y_bottom = 0;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (image.getRGB(i, j) == screenColour.getRGB() && !checked[i][j]) {
                    checked[i][j] = true;
                    current[i][j] = true;
                    currentCount++;
                    int minx = i, maxx = i;
                    int miny = j, maxy = j;
                    boolean newAddition = true;

                    while (newAddition == true) {
                        newAddition = false;
                        for (int k = 0; k < width; k++) {
                            for (int l = 0; l < height; l++) {

                                if (checkNeighbours(k, l, screenColour, checked)) {
                                    checked[k][l] = true;
                                    current[k][l] = true;
                                    currentCount++;
                                    newAddition = true;

                                    if (k < minx) {
                                        minx = k;
                                    } else if (k > maxx) {
                                        maxx = k;
                                    }
                                    if (l < miny) {
                                        miny = l;
                                    } else if (l > maxy) {
                                        maxy = l;
                                    }
                                }
                            }
                        }
                    }

                    if (currentCount > largestCount) {
                        for (int k = 0; k < width; k++) {
                            for (int l = 0; l < height; l++) {
                                largest[k][l] = current[k][l];
                            }
                        }
                        largestCount = currentCount;
                        x_top = maxx;
                        x_bottom = minx;
                        y_top = maxy;
                        y_bottom = miny;
                    }
                }
                current = new boolean[width][height];
                currentCount = 0;
            }
        }

        for (int i = x_bottom; i <= x_top; i++) {
            for (int j = y_bottom; j <= y_top; j++) {
                if (largest[i][j]) {
                    screenImage.set(i, j, backgroundImage
                        .get((i - x_bottom) % backgroundImage.width(),
                            (j - y_bottom) % backgroundImage.height()));
                }
            }
        }

        return screenImage;
    }

    /**
     * Align (appropriately rotate) an image of text that was improperly aligned.
     * This transformation can work properly only with text images.
     *
     * @return the aligned image.
     */
    public Image alignTextImage() {
        //TODO: Implement this method
        return null;
    }

}
