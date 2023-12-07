package handlers;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BulletHandler{
    private static Map<Integer, BufferedImage> bulletImages = new HashMap<>();

    public static BufferedImage getBulletImage(int bulletType) {
        if (!bulletImages.containsKey(bulletType)) {
            BufferedImage image = null;
            if (bulletType == 1) {
                image = ImageHandler.rockBulletImage;
            } else if (bulletType == 2) {
                image = ImageHandler.paperBulletImage;
            } else if(bulletType == 3){
                image = ImageHandler.scissorBulletImage;
            }
            bulletImages.put(bulletType, image);
        }
        return bulletImages.get(bulletType);
    }
}