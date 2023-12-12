package entity.smiley;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import entity.Boss;
import entity.Enemy;
import handlers.ImageHandler;
import handlers.MaskHandler;
import main.FPS;
import main.Game;

public class Smiley extends Enemy implements Boss {
    private static Smiley instance = null;
    private int health;
    private Point2D.Double destination;

    private LeftHand leftHand;

    private Smiley(Game game) {
        super(game, 5);
        this.id = 10;
        this.health = 50;
        this.maxHealth = 50;
        this.destination = validSpawnPoint(game);
        leftHand = new LeftHand(this, game);
    }

    public Point2D.Double validSpawnPoint(Game game) {
        double x = (int) (Math.random() * (game.getWidth() - 100 + 1)) + 100;
        double y = (int) (Math.random() * (game.getHeight() - 100 + 1)) + 100;
        Point2D.Double spawn = new Point2D.Double(x, y);
        return spawn;
    }

    public static Smiley getInstance(Game game) {
        if (instance == null) {
            instance = new Smiley(game);
        }
        return instance;
    }

    public void getImage(){
        this.image = ImageHandler.smiley;
        this.mask = new Area(this.game.maskHandler.getMask(this.id));
    }

    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            this.isActive = false;
            instance = null;
        }
    }

    @Override
    public void destroyInstance() {
        instance = null;
    }

    public void update() {
        if(FPS.timer > 950000000){
            if(Math.random() < 0.5){
                this.destination = validSpawnPoint(game);
            }
            leftHand.getNewDestination();
        }

        Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
        double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx * this.speed;
        this.y += dy * this.speed;
        leftHand.update();
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        g2.drawImage(this.image, at, null);
        leftHand.draw(g2);
    }
}