package entity.smiley;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import entity.Boss;
import entity.Enemy;
import main.Game;

public class Smiley extends Enemy implements Boss {
    private static Smiley instance = null;
    private int health;
    protected Point2D.Double destination;
    long lastExecutionTime = System.currentTimeMillis();

    private RightHand leftHand;
    private LeftHand rightHand;

    private Smiley(Game game) {
        super(game);
        this.id = 10;
        getImage();
        this.health = 50;
        this.maxHealth = 50;
        this.destination = validSpawnPoint(game);
        this.x = destination.x;
        this.y = destination.y;
        leftHand = new RightHand(this, game);
        rightHand = new LeftHand(this, game);
        this.colRect = this.mask.getBounds();
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
        this.image = this.game.imageHandler.getImage(this.id)[0];
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
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastExecutionTime >= 1000) {
            this.destination = validSpawnPoint(game);
            leftHand.getNewDestination();
            rightHand.getNewDestination();
            lastExecutionTime = currentTime;
        }

        Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
        double angle = Math.atan2(destinationdirection.y, destinationdirection.x);

        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx * this.speed;
        this.y += dy * this.speed;
        leftHand.update();
        rightHand.update();
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        g2.drawImage(this.image, at, null);
        g2.draw(mask);
        g2.draw(colRect);
        leftHand.draw(g2);
        rightHand.draw(g2);
    }
}