package entity.smiley;

import entity.Enemy;
import main.Game;

public class MFinger extends Enemy{
    
    public MFinger(Game game) {
        super(game);
        getImage();
    }

    @Override
    public void getImage() {
        this.image = this.game.imageHandler.getImage(10)[5];
        this.mask = this.game.maskHandler.getMask(10);
    }
}
