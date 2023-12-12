package entity;

import java.awt.geom.Area;

import handlers.ImageHandler;
import main.Game;

public class ScissorEnemy extends Enemy{
    public ScissorEnemy(Game game) {
        super(game,3);
    }

    public void getImage(){
        this.image = this.game.imageHandler.getImage(id)[0];
        this.aura = this.game.imageHandler.getImage(id)[1];
        this.mask = new Area(this.game.maskHandler.getMask(this.id));
    }
}
