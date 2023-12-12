package entity;

import java.awt.Graphics2D;

public interface Boss {
    public void destroyInstance();
    public void update();
    public void draw(Graphics2D g2);
}
