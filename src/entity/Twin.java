package entity;

import handlers.Sound;
import main.FPS;
import main.Game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Twin extends Enemy implements Sound, HealthBarEntity{
    public boolean isShooting;

    private int isShootingDuration;
    private int isShootingTimer;
    private int maxStrokeWidth;
    private int minStrokeWidth;
    private int strokeWidth;
    private int laserShotSoundCount = 0;
    Line2D line;
    public double playerX;
    public double playerY;
    double directionX;
    double directionY;
    private Point2D.Double destination;

    public Twin(Game game, int isShootingDuration, int isShootingTimer, int maxStrokeWidth, int minStrokeWidth) {
        super(game, 4);
        this.id = 8;
        this.damage = 2;
        this.isShootingDuration = isShootingDuration;
        this.isShootingTimer = isShootingTimer;
        this.maxStrokeWidth = maxStrokeWidth;
        this.minStrokeWidth = minStrokeWidth;
        this.strokeWidth = this.minStrokeWidth;
        this.HEALTH_BAR_WIDTH = (int) (this.image.getWidth() * 0.75);
        getNewDestination();
    }

    public Point2D.Double validSpawnPoint(Game game) {
        double x = (int) (Math.random() * (game.getWidth() - 100 + 1)) + 100;
        double y = (int) (Math.random() * (game.getHeight() - 100 + 1)) + 100;
        Point2D.Double spawn = new Point2D.Double(x, y);
        return spawn;
    }

    public void getImage(){
        this.image = this.game.imageHandler.getImage(id)[0];
        this.mask = new Area(this.game.maskHandler.getMask(8));
    }

    public void shoot(){
        if(FPS.timer > 950000000 && !isShooting){
            if(Math.random() < 0.2){
                this.isShooting = true;
                this.isShooting = true;
            }
        }

        if(isShootingTimer > 60){
            BasicStroke stroke = new BasicStroke(this.strokeWidth);
            Shape lineShape = stroke.createStrokedShape(this.line);
            Area lineArea = new Area(lineShape);
            lineArea.intersect(this.game.player.mask);
            if(!lineArea.isEmpty()){
                this.game.player.takeDamage(this.damage, true);
            }
        }
    }


    public void update(){
        move();
        shoot();

        if(this.isShootingTimer < 30){
            directionX = this.game.player.x - (this.x);
            directionY = this.game.player.y - (this.y);
        }

        updateMask();
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }

    public void updateMask(){
        Area newMask = this.game.maskHandler.getMask(this.id);
        AffineTransform at = AffineTransform.getTranslateInstance(this.x , this.y);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }

    public void move(){
        if(FPS.timer > 950000000){
            if(Math.random() < 0.5){
                getNewDestination();
            }
        }

        if(!isShooting){
            Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
            double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
            double dx = Math.cos(angle) * this.speed;
            double dy = Math.sin(angle) * this.speed;

            this.x += dx * this.speed;
            this.y += dy * this.speed;
        }
    }

    public void getNewDestination(){
        this.destination = validSpawnPoint(this.game);
    }

    public void rotate(BufferedImage image, AffineTransform at){
        if(this.isShootingTimer < 30){
            directionX = this.game.player.x - (this.x);
            directionY = this.game.player.y - (this.y);
        }

        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void shootingAnimation(Graphics2D g2){
        if (isShootingTimer < isShootingDuration) {
            if(isShootingTimer < 30){
                playerX = this.game.player.x;
                playerY = this.game.player.y;
            }

            double dx = playerY - this.y;
            double dy = playerX - this.x;

            this.line = new Line2D.Double(playerX + (dy * 100), playerY + (dx * 100), this.x, this.y);
            g2.setStroke(new BasicStroke(strokeWidth));
            isShootingTimer++;

            if(isShootingTimer > 60){
                g2.setColor(Color.RED);
                strokeWidth = maxStrokeWidth;
                if(laserShotSoundCount < 1){
                    playSE(4);
                    laserShotSoundCount++;
                }
            }else{
                strokeWidth = minStrokeWidth + ((maxStrokeWidth - minStrokeWidth) * isShootingTimer / isShootingDuration);
            }
            g2.draw(this.line);
        } else {
            this.isShooting = false;
            isShootingTimer = 0;
            strokeWidth = minStrokeWidth;
            laserShotSoundCount = 0;
        }
    }

    public void draw(Graphics2D g2, int health, int maxHealth){
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        rotate(image, at);

        g2.setColor(Color.BLUE);

        if (this.isShooting) {
            shootingAnimation(g2);
        }

        g2.setStroke(new BasicStroke(1));
        g2.drawImage(this.image, at, null);

        drawHealthBar(g2, x, y, health, maxHealth, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
};