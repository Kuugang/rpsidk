package entity.smiley;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import entity.Enemy;
import handlers.ImageHandler;
import main.Game;

public class LeftHand extends Enemy{
    Smiley smiley;

    private Point2D.Double destination;

    public LeftHand(Smiley smiley, Game game){
        super(game, 4);
        this.smiley = smiley;
        this.x = this.smiley.x + 100;
        this.y = this.smiley.y;
        getImage();
    }

    @Override
    public void getImage() {
        this.image = ImageHandler.thumbsUp;
    }

    public void  getNewDestination(){
        double x = (int) (Math.random() * 100) + this.smiley.x;
        double y = (int) (Math.random() * 100) + this.smiley.y - 50;
        this.destination = new Point2D.Double(x, y);
    }


    @Override
    public void update() {
        if(this.destination != null){
            Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
            double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
            double dx = Math.cos(angle) * this.speed;
            double dy = Math.sin(angle) * this.speed;
            this.x += dx;
            this.y += dy;
        }
    }


    @Override
    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        g2.drawImage(this.image, at, null);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}