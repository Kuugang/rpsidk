package entity.smiley;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import entity.Enemy;
import main.Game;

public abstract class Hand extends Enemy{
    Smiley smiley;

    protected Point2D.Double destination;
    AffineTransform at;
    Area thumbsUpMask;
    Area middleFingerMask;

    public Hand(Game game, Smiley smiley) {
        super(game);
        this.smiley = smiley;
        this.id = 10;
        getImage();
        this.destination = getNewDestination();
    }

    public abstract Point2D.Double getNewDestination();

    public void getImage(){
        this.image = this.game.imageHandler.getImage(id)[3];
        this.mask = new Area(this.game.maskHandler.getMask(id)[3]);
        this.thumbsUpMask = new Area(this.game.maskHandler.getMask(id)[3]);
        this.middleFingerMask = new Area(this.game.maskHandler.getMask(id)[5]);
    }
   
    public void updateMask(){
        Area newMask = null;
        if(smiley.attack1){
            newMask = this.middleFingerMask; 
        }else{
            newMask = this.thumbsUpMask;
        }
        AffineTransform at = AffineTransform.getTranslateInstance(this.x , this.y);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }    

    public void update(){
        this.destination = getNewDestination();
        Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
        double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;
        this.x += dx;
        this.y += dy;
        at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        updateMask();
    }

    public void draw(Graphics2D g2){
        g2.setColor(Color.RED);
        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
