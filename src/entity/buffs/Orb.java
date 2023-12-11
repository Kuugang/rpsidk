package entity.buffs;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import entity.Entity;
import handlers.ImageHandler;
import handlers.MaskHandler;
import main.Game;

public class Orb extends Entity {
    int frameIndex;
    int id;
    private long respawnTimer;
    private static final long RESPAWN_INTERVAL = 100000000;
    public Orb(Game game) {
        this.game = game;
        respawn();
        this.id = 9;
        getImage();
    }

    private void setScreenResolution() {
        // Get the screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Set x and y to random values within the screen size
        this.x = (int) (Math.random() * screenSize.width);
        this.y = (int) (Math.random() * screenSize.height);
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
//        g2.draw(mask);
    }

    public void respawn() {
        // Reset orb position or any other necessary initialization
        setScreenResolution();

        // Set the respawn timer
        respawnTimer = System.currentTimeMillis() + RESPAWN_INTERVAL;
    }

    public long getRespawnTimer() {
        return respawnTimer;
    }
}
