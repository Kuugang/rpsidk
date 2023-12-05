package entity;

import handlers.ImageHandler;
import main.Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Enemy extends Entity implements Rotate, SpawnPoints{
    public double x;
    public double y;
    public int enemyType;
    public int attackTimer = 150;
    public int attackCooldown = 0;
    private BufferedImage aura;
    public Enemy(Game game, int enemyType){
        this.speed = 1;
        this.enemyType = enemyType;
        this.game = game;
        Point2D.Double spawn = validSpawnPoint(game);
        this.x = spawn.x;
        this.y = spawn.y;
        getImage();
    }

    public void getImage(){
        if(this.enemyType == 1){
            this.image = ImageHandler.enemyRockImage;
            this.aura = ImageHandler.enemyRockAura;
        }else if(this.enemyType == 2){
            this.image = ImageHandler.enemyPaperImage;
            this.aura = ImageHandler.enemyPaperAura;
        }else{
            this.image = ImageHandler.enemyScissorImage;
            this.aura = ImageHandler.enemyScissorsAura;
        }
        this.mask = new Area(this.game.maskHandler.addMask(this));
    }
    public void update() {
        double directionX = this.game.player.x - ((this.x));
        double directionY = this.game.player.y - ((this.y));

        double angle = Math.atan2(directionY, directionX);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;

        this.x += dx;
        this.y += dy;

        // this.x -= this.game.player.speed_x_right * 0.5;
        // this.x -= this.game.player.speed_x_left * 0.5;
        // this.y -= this.game.player.speed_y_up * 0.5;
        // this.y -= this.game.player.speed_y_down * 0.5;

        if (this.game.maskHandler.getMask(this) != null) {
            Area newMask = this.game.maskHandler.getMask(this);
            AffineTransform at = AffineTransform.getTranslateInstance(this.x, this.y);
            directionX = this.game.player.x - (this.x + (double) this.image.getWidth() / 2);
            directionY = this.game.player.y - (this.y + (double) this.image.getHeight() / 2);
            double rotationAngleInRadians = Math.atan2(directionY, directionX);

            at.rotate(rotationAngleInRadians);
            this.mask.reset();
            this.mask.add(newMask);
            this.mask.transform(at);
        }
        this.attackCooldown++;
        if(attackCooldown >= attackTimer){
            attackCooldown = attackTimer;
        }
    }
    public void rotate(BufferedImage image, AffineTransform at){
        double directionX = this.game.player.x - (this.x + (double)image.getWidth() / 2);
        double directionY = this.game.player.y - (this.y + (double)image.getHeight() / 2);
        double rotationAngleInRadians = Math.atan2(directionY, directionX);
        at.rotate(rotationAngleInRadians, image.getWidth() / 2.0, image.getHeight() / 2.0);
    }

    public void draw(Graphics2D g2) {
        AffineTransform at = AffineTransform.getTranslateInstance(this.x - this.image.getWidth() / 2.0, this.y - this.image.getHeight() / 2.0);
        AffineTransform auraAt = AffineTransform.getTranslateInstance(this.x - this.aura.getWidth() / 2.0, this.y - this.aura.getHeight() / 2.0);

        rotate(this.image, at);
        rotate(this.aura, auraAt);

        g2.setColor(Color.RED);

        g2.drawImage(this.aura, auraAt, null);
        g2.drawImage(this.image, at, null);

        // g2.draw(this.mask);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

}