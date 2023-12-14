package entity.smiley;

import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import main.Game;

public class LeftHand extends Hand{
    public LeftHand(Smiley smiley, Game game){
        super(game, smiley);
        this.id = 10;
        this.x = this.smiley.x - 100;
        this.y = this.smiley.y;
        getImage();
        this.colRect = this.mask.getBounds();
    }

    public Point2D.Double getNewDestination(){
        double x = Math.random() * -300 + this.smiley.destination.x;
        double y = (int) (Math.random() * 400 ) + this.smiley.destination.y - 200;
        return new Point2D.Double(x, y);
    }

    public void getImage(){
        super.getImage();
        Area flippedMask = new Area();
        for (PathIterator iterator = this.mask.getPathIterator(null); !iterator.isDone(); iterator.next()) {
            Path2D path = new Path2D.Double();
            double[] coords = new double[6];
            int type = iterator.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    path.moveTo(-coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    path.lineTo(-coords[0], coords[1]);
                    break;
                case PathIterator.SEG_QUADTO:
                    path.quadTo(-coords[0], coords[1], -coords[2], coords[3]);
                    break;
                case PathIterator.SEG_CUBICTO:
                    path.curveTo(-coords[0], coords[1], -coords[2], coords[3], -coords[4], coords[5]);
                    break;
                case PathIterator.SEG_CLOSE:
                    path.closePath();
                    break;
            }

            flippedMask.add(new Area(path));
        }
        thumbsUpMask = flippedMask;
    }

    public void updateMask(){
        Area newMask = null;
        if(smiley.attack1){
            newMask = this.game.maskHandler.getMask(this.id)[5];
        }else{
            newMask = thumbsUpMask;
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