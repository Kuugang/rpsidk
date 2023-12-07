package entity;

import java.awt.Graphics2D;
import main.Game;

public class Twins extends Enemy implements Boss {
    private static Twins instance = null;
    public Twin twin1;
    public Twin twin2;
    private int health;
    private int isShootingDuration = 100;
    private int isShootingTimer = 0;
    private int maxStrokeWidth = 20;
    private int minStrokeWidth = 1;

    private Twins(Game game) {
        super(game, 4);
        twin1 = new Twin(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);
        twin2 = new Twin(game, isShootingDuration, isShootingTimer, maxStrokeWidth, minStrokeWidth);
        this.health = 1;
    }

    public static Twins getInstance(Game game) {
        if (instance == null) {
            instance = new Twins(game);
        }
        return instance;
    }

    public void update() {
        twin1.update();
        twin2.update();


    }

    public void draw(Graphics2D g2) {
        twin1.draw(g2);
        twin2.draw(g2);
    }

    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            this.isActive = false;
            instance = null;
            this.game.player.score++;
        }
    }
}
