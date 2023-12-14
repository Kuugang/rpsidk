package entity;

import main.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity implements Rotate, SpawnPoints{
    public double x;
    public double y;
    public int enemyType;
    public int attackTimer = 150;
    public int attackCooldown = 0;
    protected BufferedImage aura;
    private int health;
    protected int damage;
    public boolean isAttacking;
    public Enemy(Game game, int enemyType){
        this.health = 1;
        this.damage = 1;
        this.speed = 1;
        this.enemyType = enemyType;
        this.id = enemyType + 4;
        this.game = game;
        Point2D.Double spawn = validSpawnPoint(game);
        this.x = spawn.x;
        this.y = spawn.y;
        getImage();
        if(!(this instanceof Boss)){
            this.colRect = this.mask.getBounds();
        }
    }

    public Enemy(Game game){
        this.game = game; 
        this.speed = 1;
    }

    public int getDamage(){
        return this.damage;
    }

    public void updateMask(double directionX, double directionY){
        Area newMask = this.game.maskHandler.getMask(this.id)[0];
        AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
        directionX = this.game.player.x - (this.x + (double) this.image.getWidth() / 2);
        directionY = this.game.player.y - (this.y + (double) this.image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }


    public void takeDamage(int damage){
        this.health -= damage;
        if(this.health <= 0)
            this.isActive = false;
    }

    public void setHealth(int health){
        this.health = health;
    }

    public int getHealth(){
        return this.health; 
    }

    public void rotate(BufferedImage image, AffineTransform at){
        double directionX = this.game.player.x - (this.x + (double)image.getWidth() / 2);
        double directionY = this.game.player.y - (this.y + (double)image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }


    public void update() {
        double directionX = this.game.player.x - ((this.x));
        double directionY = this.game.player.y - ((this.y));

        double angle = Math.atan2(directionY, directionX);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx;
        this.y += dy;

        updateMask(directionX, directionY);

        this.attackCooldown++;
        if(attackCooldown >= attackTimer){
            attackCooldown = attackTimer;
        }
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        AffineTransform auraAt = AffineTransform.getTranslateInstance(this.x - this.aura.getWidth() / 2.0, this.y - this.aura.getHeight() / 2.0);

        rotate(this.image, at);
        rotate(this.aura, auraAt);

        g2.setColor(Color.RED);

        g2.drawImage(this.aura, auraAt, null);
        g2.drawImage(this.image, at, null);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}