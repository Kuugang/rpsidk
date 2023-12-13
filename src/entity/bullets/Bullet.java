package entity.bullets;

import main.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import entity.CloneableEntity;
import entity.Entity;

public class Bullet extends Entity implements CloneableEntity{
    public int bulletType;
    public double angle;
    public double dx;
    public double dy;
    public Bullet(Game game, double x, double y, double angle){
        this.speed = 8;
        this.bulletType = 0;
        this.game = game;
        this.x = x;
        this.y = y;
        this.angle = angle;
    }

    public void getImage(){
        this.image = this.game.imageHandler.getImage(bulletType + 1)[0];
        this.id = bulletType + 1;
        this.mask = new Area(this.game.maskHandler.getMask(id));
        this.colRect = this.mask.getBounds();
    }

    public void initClone(double angle, int bulletType, double x, double y){
        this.angle = angle;
        this.bulletType = bulletType;
        this.getImage();
        this.x = x;
        this.y = y;
    }

    public void update(){
        this.dx = Math.cos(angle) * this.speed;
        this.dy = Math.sin(angle) * this.speed;

        this.x +=  this.dx;
        this.y +=  this.dy;

        if(this.x > game.getWidth() || this.x < 0 || this.y > game.getHeight() || this.y < 0){
            this.isActive = false;
        }

        resetMask();
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }

    public void resetMask(){
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        at.rotate(this.angle);
        this.mask.reset();
        this.mask.add(this.game.maskHandler.getMask(this.id));
        this.mask.transform(at);
    }

    public void draw(Graphics2D g2){
        AffineTransform at =  AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        at.rotate(this.angle, image.getWidth() / 2.0, image.getHeight() / 2.0);
        g2.drawImage(image, at, null);
        g2.setColor(Color.RED);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    public CloneableEntity clone() {
        try {
            CloneableEntity clonedObject = (Bullet) super.clone();
            return clonedObject;
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}