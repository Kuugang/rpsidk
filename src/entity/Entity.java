package entity;


import main.Game;

import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;

public abstract class Entity {
    public int id;
    public double x, y;
    //todo getter and setter for this
    private int health;
    public Game game;
    public double speed;
    public boolean isActive = true;
    public BufferedImage image;
    public Area mask;
    public abstract void getImage();
    public abstract void draw(Graphics2D g2);
    public abstract void update();
}