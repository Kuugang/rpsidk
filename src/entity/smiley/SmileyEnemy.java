package entity.smiley;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.awt.geom.Area;

import entity.Enemy;
import main.Game;
import main.UtilityTool;

public class SmileyEnemy extends Enemy implements UtilityTool{
    AffineTransform at;
    private int side;
    Random random = new Random();
    public SmileyEnemy(Game game) {
        super(game);
        this.id = 10;
        this.damage = 1;
        this.speed = random.nextInt(3) + 1;
        generateRandomStartPosition();
        getImage();
    }

    @Override
    public void getImage() {
        switch (side) {
            case 0:
                this.image = this.game.imageHandler.getImage(10)[5];
                this.image = rotateImage(this.image, 180);
                break;
            case 1:
                this.image = this.game.imageHandler.getImage(10)[5];
                this.image = rotateImage(this.image, -90);
                break;
            case 2:
                this.image = this.game.imageHandler.getImage(10)[5];
                break;
            case 3:
                this.image = this.game.imageHandler.getImage(10)[5];
                this.image = rotateImage(image,-270);
                break;
            default:
                break;
        }
        
        this.image = scaleImage(image,this.image.getWidth() / 2, this.image.getHeight() / 2);
        this.mask = new Area(this.game.maskHandler.getMask(10)[5]);
        this.mask = new Area(rotateMaskArea(mask, 90));
        this.mask = new Area(scaleMaskArea(mask, .5));
        this.colRect = getTransformedBounds(this.mask);
    }

    private void generateRandomStartPosition() {
        int minX = 50;
        int maxX = this.game.getWidth();

        int minY = 50;
        int maxY = this.game.getHeight();

        side = random.nextInt(4); 
        // side = 3;
        switch (side) {
            case 0:
                x = random.nextInt(maxX - minX) + minX;
                y = 0;
                break;
            case 1: //right side
                x = this.game.window.getWidth() + 50; 
                y = random.nextInt(maxY - minY) + minY;
                break;
            case 2: // Bottom side
                x = random.nextInt(this.game.window.getWidth());
                y = this.game.window.getHeight() + 50;
                break;
            case 3: //left side
                x = -50;
                y = random.nextInt(this.game.window.getHeight());
                break;
        }
    }

    public void move() {
        switch (side) {
            case 0:
                this.y += speed; 
                if(this.y > this.game.getHeight())
                    isActive = false;
                break;
            case 1:
                x -= speed;
                if(this.x < -10)
                    isActive = false;
                break;
            case 2:
                y -= speed;
                if(this.y < -10)
                    isActive = false;
                break;
            case 3:
                x += speed;
                if(this.x > this.game.getWidth())
                    isActive = false;
                break;
            default:
                break;
        }
    }

    public void updateMask(){
        Area newmask = this.game.maskHandler.getMask(10)[5];
        at = AffineTransform.getTranslateInstance(this.x, this.y);
        switch (side) {
            case 0:
                newmask = rotateMaskArea(newmask, 180);
                break;
            case 1:
                newmask = rotateMaskArea(newmask, -90);
                break;
            case 2:
                break;
            case 3:
                newmask = rotateMaskArea(newmask, -270);
                break;
            default:
                break;
        }
        newmask = scaleMaskArea(newmask, .5);
        this.colRect = getTransformedBounds(newmask);

        this.mask.reset();
        this.mask.add(newmask);
        this.mask.transform(at);
    }


    public void update(){
        at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        updateMask();
        move();
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }

    public void draw(Graphics2D g2){
        at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
