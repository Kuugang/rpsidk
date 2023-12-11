package handlers;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class MaskHandler {
    static HashMap<Integer, Area> masks = new HashMap<>();;
    private static Area addMask(int entityId){
        BufferedImage image = null;
        switch (entityId) {
            case 1:
                image = ImageHandler.playerFrames[0];
                break;
            case 2:
                image = ImageHandler.rockBulletImage;
                break;
            case 3:
                image = ImageHandler.paperBulletImage;
                break;
            case 4:
                image = ImageHandler.scissorBulletImage;
                break;
            case 5:
                image = ImageHandler.enemyRockImage;
                break;
            case 6:
                image = ImageHandler.enemyPaperImage;
                break;
            case 7:
                image = ImageHandler.enemyScissorImage;
                break;
            case 8:
                image = ImageHandler.boss1;
            case 9:
                image = ImageHandler.orbImages[0];
            default:
                break;
        }

        Area mask = createMaskFromTransparency(image, 0 - image.getWidth() / 2, 0 - image.getHeight() / 2);
        masks.put(entityId, mask);
        return mask;
    }

    public static Area getMask(int entityId){
        if(!masks.containsKey(entityId)){
            Area mask = addMask(entityId);
            masks.put(entityId, mask);
            return mask;
        }
        return masks.get(entityId);
    }

    public static Area createMaskFromTransparency(BufferedImage image, double x, double y) {
        Area mask = new Area();

        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int alpha = (image.getRGB(i, j) >> 24) & 0xFF;
                if (alpha != 0) {
                    Rectangle2D.Double pixelRect = new Rectangle2D.Double(x + i, y + j, 1, 1);
                    mask.add(new Area(pixelRect));
                }
            }
        }
        return mask;
    }
}