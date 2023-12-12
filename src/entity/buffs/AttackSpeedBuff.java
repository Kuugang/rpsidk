package entity.buffs;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;

import entity.Entity;
import handlers.ImageHandler;
import handlers.MaskHandler;
import main.Game;

public class AttackSpeedBuff extends Entity implements Buff{
    private int frameIndex;
    private static AttackSpeedBuff instance = null;

    private AttackSpeedBuff(Game game) {
        this.game = game;
        respawn();
        this.id = 9;
        getImage();
    }

    public static AttackSpeedBuff getInstance(Game game) {
        if (instance == null) {
            instance = new AttackSpeedBuff(game);
        }
        return instance;
    }

    // private void setScreenResolution() {
    //     // Get the screen size
    //     Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //     // Set x and y to random values within the screen size
    //     this.x = (int) (Math.random() * screenSize.width);
    //     this.y = (int) (Math.random() * screenSize.height);
    // }

    @Override
    public void getImage() {
        this.image = ImageHandler.gems[frameIndex];
        this.mask = new Area(this.game.maskHandler.getMask(this.id));
    }

    public void respawn() {
        this.x = (int) (Math.random() * this.game.SCREENSIZE.width);
        this.y = (int) (Math.random() * this.game.SCREENSIZE.height);
    }

    public void applyBuff() {
        this.game.player.reloadTime -= 5;
        this.isActive = false;
        instance = null;
    }


    @Override
    public void update() {
        if (ImageHandler.gems != null && ImageHandler.orbImages.length > 0) {
            frameIndex = (int) ((System.currentTimeMillis() / 100) % ImageHandler.orbImages.length);
            this.image = ImageHandler.orbImages[frameIndex];
        }

        Area newMask = this.game.maskHandler.getMask(this.id);

        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        at = AffineTransform.getTranslateInstance(this.x, this.y);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }


    @Override
    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        g2.draw(this.mask);
        g2.drawImage(image, at, null);
    }
}
