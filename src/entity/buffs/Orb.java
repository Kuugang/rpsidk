package entity.buffs;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import entity.Entity;
import handlers.ImageHandler;
import handlers.MaskHandler;
import main.Game;

public class Orb extends Entity {
    int frameIndex;
    int id;

    public Orb(Game game){
        this.game = game;
        x = 100;
        y = 200;
        this.id = 9;
        getImage();
    }

    @Override
    public void getImage() {
        this.image = ImageHandler.orbImages[frameIndex];
        this.mask = new Area(MaskHandler.getMask(this.id));
    }

    @Override
    public void update() {
        if (ImageHandler.orbImages != null && ImageHandler.orbImages.length > 0) {
            frameIndex = (int) ((System.currentTimeMillis() / 100) % ImageHandler.orbImages.length);
            this.image = ImageHandler.orbImages[frameIndex];
        }

        
        Area newMask = MaskHandler.getMask(this.id);

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        at = AffineTransform.getTranslateInstance(this.x, this.y);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }


    @Override
    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        g2.drawImage(image, at, null);
        g2.draw(mask);
    }
    

    
}
