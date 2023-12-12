package entity;

import java.awt.Color;
import java.awt.Graphics2D;

public interface HealthBarEntity {
    default void drawHealthBar(Graphics2D g2, double x, double y, int health, int maxHealth, int healthBarWidth, int healthBarHeight){
        int currentHealthbarWidth = (int) ((double)  health /  maxHealth * healthBarWidth);

        g2.setColor(Color.RED);
        g2.fillRect((int)x - healthBarWidth / 2, (int)y - 70, healthBarWidth, healthBarHeight);

        g2.setColor(Color.GREEN);
        g2.fillRect((int)x - healthBarWidth/ 2, (int)y - 70, currentHealthbarWidth, healthBarHeight);

        g2.setColor(Color.BLACK);
        g2.drawRect((int)(x - healthBarWidth/ 2), (int)(y - 70), healthBarWidth, healthBarHeight);
    }
}
