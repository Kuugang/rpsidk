package entity.smiley;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import entity.Enemy;
import main.Game;

public class RightHand extends Enemy{
    Smiley smiley;

    private Point2D.Double destination;

    public RightHand(Smiley smiley, Game game){
        super(game);
        this.id = 11;
        this.smiley = smiley;
        this.x = this.smiley.x + 100;
        this.y = this.smiley.y;
        getImage();
        this.colRect = this.mask.getBounds();
    }

    @Override
    public void getImage() {
        this.image = this.game.imageHandler.getImage(id)[0];
        this.mask = new Area(this.game.maskHandler.getMask(id));
    }

    public void  getNewDestination(){
        double x = Math.random() * 300 + this.smiley.destination.x;
        double y = (int) (Math.random() * 400 ) + this.smiley.destination.y - 200;
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
        // g2.setColor(Color.RED);
        g2.drawOval((int)destination.x, (int)destination.y, 2, 2);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}