package entity;

import java.awt.geom.Area;
import main.Game;

public class PaperEnemy extends Enemy{
    public PaperEnemy(Game game){
        super(game,2);
        getImage();
    }

    public void getImage(){
        this.image = this.game.imageHandler.getImage(id)[0];
        this.aura = this.game.imageHandler.getImage(id)[1];
        this.mask = new Area(this.game.maskHandler.getMask(this.id)[0]);
    }
}
