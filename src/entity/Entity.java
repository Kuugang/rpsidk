package entity;


import main.Game;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int id;
    public double x, y;
    //todo getter and setter for this
    protected int health;
    protected int maxHealth;
    public Game game;
    public Rectangle colRect;
    /**
     * Comments for guide */
    public double speed; // Only Enemies. e.g rps and twin
    public boolean isActive = true; // Enemies, bullets, buff and bullet.
    public BufferedImage image; // All entity
    public Area mask;

    protected int HEALTH_BAR_WIDTH;
    protected final int HEALTH_BAR_HEIGHT = 10;


    public abstract void getImage();
    public abstract void draw(Graphics2D g2);
    public abstract void update();

    public void drawMask(Graphics2D g2){
        g2.draw(mask);
    }


    public void drawColRect(Graphics2D g2){
        g2.draw(colRect);
    }
}