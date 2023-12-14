package handlers;

import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import main.Game;

public class MaskHandler {
    private HashMap<Integer, Area[]> masks = new HashMap<>();;
    Game game;

    public MaskHandler(Game game){
        this.game = game;
    } 

    private Area[] addMask(int entityId){
        BufferedImage images[] = this.game.imageHandler.getImage(entityId);
        Area masksArray[] = new Area[images.length];
        for(int i = 0; i < images.length; i++){
            masksArray[i] = createMaskFromTransparency(images[i], 0 - images[i].getWidth() / 2, 0 - images[i].getHeight() / 2);
        }
        masks.put(entityId, masksArray);
        return masksArray;
    }

    public Area[] getMask(int entityId){
        if(!masks.containsKey(entityId)){
            Area mask[] = addMask(entityId);
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