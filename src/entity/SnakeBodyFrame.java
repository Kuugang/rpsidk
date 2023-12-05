package entity;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.FontMetrics;
import javax.swing.ImageIcon;

import main.Game;

public class SnakeBodyFrame extends EntityWindow{
    private int xHeadPoly[] = {};
    private int yHeadPoly[] = {};
    private int number;
    public Polygon polygon;

    public SnakeBodyFrame(Game game, int UNIT_SIZE, int number) {
        super(game, UNIT_SIZE, UNIT_SIZE, false);
        this.polygon = new Polygon(xHeadPoly, yHeadPoly, xHeadPoly.length);
        this.number = number;
    }

    @Override
    public void getImage() {

    }

    @Override
    public void setWindowDefaults() {
        super.setWindowDefaults();
        ImageIcon img = new ImageIcon("src/resource/snek.png");
        window.setIconImage(img.getImage());
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        xHeadPoly = new int[]{10, this.getWidth() - 10, this.getWidth() - 10, this.getWidth() - 10, this.getHeight() - 10, 10, 10, 10}; 
        yHeadPoly = new int[]{10, 10, 10, this.getHeight() - 10, this.getHeight() - 10, this.getHeight() - 10, this.getHeight() - 10, 10};  

        this.polygon = new Polygon(xHeadPoly, yHeadPoly, xHeadPoly.length);

        g2.setStroke(new BasicStroke(4));
        g2.drawPolygon(polygon);

        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(Integer.toString(number));
        int textHeight = fm.getHeight();
        int x = (this.getWidth() - textWidth) / 2;
        int y = (this.getHeight() - textHeight) / 2 + fm.getAscent();

        g2.drawString(Integer.toString(number) , x, y);
    }
}
