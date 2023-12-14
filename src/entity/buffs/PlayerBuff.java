package entity.buffs;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.util.Random;

import entity.Entity;
import handlers.EntityHandler;
import main.Game;

public class PlayerBuff extends Entity implements Buff{
    private int frameIndex;
    private static PlayerBuff instance = null;
    private BufferedImage[] images;
    BufferedImage[] copiedImages = new BufferedImage[91];
    private int buffType;
    EntityHandler entityHandler;
    Random random;

    private PlayerBuff(Game game) {
        this.entityHandler = game.entityHandler;
        random = new Random();
        this.game = game;
        spawn();
        this.buffType = random.nextInt(6) + 1;
        // this.buffType = 5;
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
    
    public void destroyInstance(){
        this.isActive = false;
        instance = null;
    }

    @Override
    public void getImage() {
        this.images = this.game.imageHandler.getImage(this.id);

        for (int i = 0; i < images.length; i++) {
            BufferedImage originalImage = images[i];
            BufferedImage copiedImage = new BufferedImage(
                    originalImage.getWidth(),
                    originalImage.getHeight(),
                    originalImage.getType());
            
            Graphics g = copiedImage.getGraphics();
            g.drawImage(originalImage, 0, 0, null);
            g.dispose();
            copiedImages[i] = copiedImage;
        }

        for(int i = 0; i < images.length; i++){
            for (int y = 0; y < images[i].getHeight(); y++) {
                for (int x = 0; x < images[i].getWidth(); x++) {
                    int rgba = images[i].getRGB(x, y);
                    int alpha = (rgba >> 24) & 0xFF;
                    int red = (rgba >> 16) & 0xFF;
                    int green = (rgba >> 8) & 0xFF;
                    int blue = rgba & 0xFF;
                    switch (buffType) {
                        case 1:
                            red = Math.min((int) (red * 3), 255);
                            break;
                        case 2:
                            continue;
                        case 3:
                            red = Math.min((int) (red * 1.5), 255);
                            green = Math.min((int) (green * 1.5), 255);
                            break;
                        case 4:
                            int grayValue = (int) (0.2126 * red + 0.7152 * green + 0.0722 * blue);
                            int newRGBA = (alpha << 24) | (grayValue << 16) | (grayValue << 8) | grayValue;
                            copiedImages[i].setRGB(x, y, newRGBA);
                            continue;
                        case 5:
                            green = Math.min((int) (green * 1.5), 255);
                            break;
                        case 6:
                            red = Math.min((int) (red * 1.5), 255);
                            blue = Math.min((int) (blue * 1.5), 255);
                        default:
                            break;
                    }
                    int newRGBA = (alpha << 24) | (red << 16) | (green << 8) | blue;
                    copiedImages[i].setRGB(x, y, newRGBA);
                }
            }
        }

        this.mask = new Area(this.game.maskHandler.getMask(this.id)[0]);
    }

    public void spawn() {
        this.x = (int) (Math.random() * this.game.SCREENSIZE.width);
        this.y = (int) (Math.random() * this.game.SCREENSIZE.height);
    }

    public void applyBuff() {
        switch (buffType) {
            case 1:
                this.game.player.setMaxHealth(this.game.player.getMaxHealth() + 2);
                this.game.player.setHealth(this.game.player.getMaxHealth());
                System.out.println("MAX HEALTH");
                break;
            case 2:
                this.game.player.reloadTime -= 1;
                System.out.println("ATK SPEED");
                break;
            case 3:
                this.game.player.top_speed  += 0.2;
                System.out.println("MOVE SPEED");
                break;
            case 4:
                entityHandler.freezeEnemy();
                System.out.println("ENEMY FREEZE");
                break;
            case 5:
                this.game.player.dashBuffIsActive = true;
                System.out.println("DASH");
                break;
            case 6:
                this.game.player.teleportBuffIsActive = true;
                System.out.println("TELEPORT");
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
            Area newMask = this.game.maskHandler.getMask(this.id)[0];
            AffineTransform at = AffineTransform.getTranslateInstance(this.x - images[frameIndex].getWidth() / 2.0, this.y - images[frameIndex].getHeight() / 2.0);
            at = AffineTransform.getTranslateInstance(this.x, this.y);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
            this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
        }
    }

    public BufferedImage changeImageColor(BufferedImage originalImage){
        BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < originalImage.getHeight(); y++) {
            for (int x = 0; x < originalImage.getWidth(); x++) {
                int rgba = originalImage.getRGB(x, y);
                int alpha = (rgba >> 24) & 0xFF;
                int red = (rgba >> 16) & 0xFF;
                int green = (rgba >> 8) & 0xFF;
                int blue = rgba & 0xFF;
                switch (buffType) {
                    case 1:
                        red = Math.min((int) (red * 100), 255);
                        break;
                    case 2:
                        return originalImage;
                    case 3:
                        red = Math.min((int) (red * 100), 255);
                        green = Math.min((int) (green * 100), 255);
                        break;
                    case 4:
                        ColorSpace colorSpace = ColorSpace.getInstance(ColorSpace.CS_GRAY); // Change to the desired color space
                        ColorConvertOp op = new ColorConvertOp(colorSpace, null);
                        image = op.filter(images[frameIndex], null);
                        return image;
                    case 5:
                        green = Math.min((int) (green * 100), 255);
                        break;
                    case 6:
                        red = Math.min((int) (red * 100), 255);
                        blue = Math.min((int) (blue * 100), 255);
                    default:
                        break;
                }
                int newRGBA = (alpha << 24) | (red << 16) | (green << 8) | blue;
                image.setRGB(x, y, newRGBA);
            }
        }
        return image;
    }

    @Override
    public void draw(Graphics2D g2) {
        if (images != null && images.length > 0) {
            AffineTransform at = AffineTransform.getTranslateInstance(this.x - images[frameIndex].getWidth() / 2.0, this.y - images[frameIndex].getHeight() / 2.0);
            g2.drawImage(copiedImages[frameIndex], at, null);
        }
    }
}
