package entity.smiley;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import main.Game;

public class RightHand extends Hand{
    public RightHand(Smiley smiley, Game game){
        super(game, smiley);
        this.id = 10;
        this.x = this.smiley.x + 100;
        this.y = this.smiley.y;
        getImage();
        this.colRect = this.mask.getBounds();
    }

    public Point2D.Double  getNewDestination(){
        double x = Math.random() * 300 + this.smiley.destination.x;
        double y = (int) (Math.random() * 400 ) + this.smiley.destination.y - 200;
        return new Point2D.Double(x, y);
    }

    public void updateMask(){
        Area newMask = null;
        if(smiley.attack1){
            newMask = this.game.maskHandler.getMask(this.id)[5];
        }else{
            newMask = this.game.maskHandler.getMask(this.id)[3];
        }
        AffineTransform at = AffineTransform.getTranslateInstance(this.x , this.y);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }    

    @Override
    public void update() {
        super.update();
    }
}