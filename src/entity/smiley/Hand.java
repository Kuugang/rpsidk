package entity.smiley;

import java.awt.geom.Area;

import entity.Enemy;
import main.Game;

public abstract class Hand extends Enemy{

    public Hand(Game game) {
        super(game);
    }

    public void getImage() {
        this.image = this.game.imageHandler.getImage(id)[0];
        this.mask = new Area(this.game.maskHandler.getMask(id));
    }
        
}
