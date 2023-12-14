package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
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

    // public default BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
    //     int width = originalImage.getWidth();
    //     int height = originalImage.getHeight();

    //     AffineTransform transform = new AffineTransform();
    //     transform.rotate(Math.toRadians(degrees), width / 2, height / 2);

    //     BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
    //     rotatedImage.createGraphics().drawImage(originalImage, transform, null);

    //     return rotatedImage;
    // }


    public default BufferedImage rotateImage(BufferedImage originalImage, double degrees) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        double radians = Math.toRadians(degrees);

        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.floor(width * cos + height * sin);
        int newHeight = (int) Math.floor(height * cos + width * sin);

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        Graphics2D g2 = rotatedImage.createGraphics();

        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - width) / 2, (newHeight - height) / 2);
        transform.rotate(radians, width / 2.0, height / 2.0);
        g2.setTransform(transform);

        g2.drawImage(originalImage, 0, 0, null);
        g2.dispose();

        return rotatedImage;
    }

    public default Area rotateMaskArea(Area mask, double angleDegrees) {
        java.awt.Rectangle bounds = mask.getBounds();

        double centerX = bounds.getCenterX();
        double centerY = bounds.getCenterY();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angleDegrees), centerX, centerY);

        Area rotatedMask = new Area(mask);
        rotatedMask.transform(transform);

        return rotatedMask;
    }

    public default Area scaleMaskArea(Area mask, double scaleFactor) {
        AffineTransform transform = new AffineTransform();
        transform.scale(scaleFactor, scaleFactor);
        Area scaledMask = new Area(mask);
        scaledMask.transform(transform);
        return scaledMask;
    }

    public default Rectangle getTransformedBounds(Area area) {
        Rectangle bounds = new Rectangle();
        PathIterator iterator = area.getPathIterator(null);
        double[] coords = new double[6];
        AffineTransform transform = new AffineTransform();

        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            transform.transform(coords, 0, coords, 0, 1);

            if (type != PathIterator.SEG_CLOSE) {
                bounds.add((int) coords[0], (int) coords[1]);
            }

            if (type == PathIterator.SEG_QUADTO) {
                bounds.add((int) coords[2], (int) coords[3]);
            } else if (type == PathIterator.SEG_CUBICTO) {
                bounds.add((int) coords[4], (int) coords[5]);
            }

            iterator.next();
        }
        return bounds;
    }
}

