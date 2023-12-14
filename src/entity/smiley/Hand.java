package entity.smiley;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import entity.Enemy;
import main.Game;

public abstract class Hand extends Enemy{
    Smiley smiley;

    protected Point2D.Double destination;
    AffineTransform at;

    public Hand(Game game, Smiley smiley) {
        super(game);
        this.smiley = smiley;
        this.id = 10;
        getImage();
        this.destination = getNewDestination();
    }

    public abstract Point2D.Double getNewDestination();

    public abstract void getImage();
    
    public void update(){
        if(this.destination != null){
            this.destination = getNewDestination();
            Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
            double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
            double dx = Math.cos(angle) * this.speed;
            double dy = Math.sin(angle) * this.speed;
            this.x += dx;
            this.y += dy;
        }
        at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
    }

    public abstract void draw(Graphics2D g2);
}
