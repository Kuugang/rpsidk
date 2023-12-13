package entity.buffs;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.util.Random;

import entity.Entity;
import main.Game;

public class PlayerBuff extends Entity implements Buff{
    private int frameIndex;
    private static PlayerBuff instance = null;
    private BufferedImage[] images;
    private int buffType;
    Random random;
    private PlayerBuff(Game game) {
        random = new Random();
        this.game = game;
        spawn();
        this.buffType = random.nextInt(3);
        this.id = 9;
        getImage();
        colRect = this.mask.getBounds();
    }

    public static PlayerBuff getInstance(Game game) {
        if (instance == null) {
            instance = new PlayerBuff(game);
        }
        return instance;
    }

    @Override
    public void getImage() {
        this.images = this.game.imageHandler.getImage(this.id);
        this.mask = new Area(this.game.maskHandler.getMask(this.id));
    }

    public void spawn() {
        this.x = (int) (Math.random() * this.game.SCREENSIZE.width);
        this.y = (int) (Math.random() * this.game.SCREENSIZE.height);
    }

    public void applyBuff() {
        switch (buffType + 1) {
            case 1:
                this.game.player.reloadTime -= 1;
                System.out.println("ATK SPEED");
                break;
            case 2:
                this.game.player.top_speed  += 1;
                System.out.println("MOVE SPEED");
                break;
            default:
                break;
        }
                this.isActive = false;
        instance = null;
    }


    @Override
    public void update() {
        if (images != null && images.length > 0) {
            frameIndex = (int) ((System.currentTimeMillis() / 100) % images.length);
            this.image = images[frameIndex];
            Area newMask = this.game.maskHandler.getMask(this.id);
            AffineTransform at = AffineTransform.getTranslateInstance(this.x - images[frameIndex].getWidth() / 2.0, this.y - images[frameIndex].getHeight() / 2.0);
            at = AffineTransform.getTranslateInstance(this.x, this.y);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
            this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        if (images != null && images.length > 0) {
            AffineTransform at = AffineTransform.getTranslateInstance(this.x - images[frameIndex].getWidth() / 2.0, this.y - images[frameIndex].getHeight() / 2.0);
            g2.drawImage(images[frameIndex], at, null);
        }
    }
}
