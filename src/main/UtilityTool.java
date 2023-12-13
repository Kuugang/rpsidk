package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A utility class for common image-related operations.
 */
public interface UtilityTool {

    /**
     * Scales an image to the specified width and height using the provided BufferedImage.
     *
     * @param original The original image to be scaled.
     * @param width    The desired width of the scaled image.
     * @param height   The desired height of the scaled image.
     * @return A new BufferedImage representing the scaled image.
     */
    public default BufferedImage scaleImage(BufferedImage original, int width, int height) {
        // Create a new BufferedImage with the specified width, height, and image type.
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());

        // Get the Graphics2D object of the scaled image for drawing operations.
        Graphics2D g2 = scaledImage.createGraphics();

        // Draw the original image onto the scaled image with the specified width and height.
        g2.drawImage(original, 0, 0, width, height, null);

        // Dispose of the Graphics2D object to release resources.
        g2.dispose();

        // Return the scaled image.
        return scaledImage;
    }

    public default BufferedImage applyOpacity(BufferedImage image, float opacity) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = result.createGraphics();

        // Apply opacity to each pixel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = new Color(image.getRGB(x, y), true);
                color = new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) (opacity * color.getAlpha()));
                result.setRGB(x, y, color.getRGB());
            }
        }

        g.dispose();
        return result;
    }

    public default BufferedImage flipHorizontal(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage flippedImage = new BufferedImage(width, height, originalImage.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                flippedImage.setRGB((width - 1) - x, y, originalImage.getRGB(x, y));
            }
        }

        return flippedImage;
    }

}

