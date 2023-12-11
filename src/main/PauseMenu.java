package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class  PauseMenu extends Menu {

    public boolean mainMenuHovered;
    public boolean resumeHovered;
    public boolean quitHovered;

    public PauseMenu(Game game) {
        super(game);
    }

    @Override
    public void update(ArrayList<Point> points, Graphics2D g2) {
        mainMenuHovered = false;
        resumeHovered = false;
        quitHovered = false;

        int mainMenuStringLength = getTextWidth(g2, "Main Menu");
        int mainMenuStringHeight = getTextHeight(g2);
        if (game.mouseX > points.get(1).x &&
                game.mouseX < points.get(0).x + mainMenuStringLength &&
                game.mouseY > points.get(0).y - mainMenuStringHeight + 12 &&
                game.mouseY < points.get(0).y) {
            mainMenuHovered = true;
        }

        int resumeStringLength = getTextWidth(g2, "Resume");
        int resumeStringHeight = getTextHeight(g2);
        if (game.mouseX > points.get(1).x &&
                game.mouseX < points.get(1).x + resumeStringLength &&
                game.mouseY > points.get(1).y - resumeStringHeight + 12 &&
                game.mouseY < points.get(1).y) {
            resumeHovered = true;
        }

        int quitStringLength = getTextWidth(g2, "Quit");
        int quitStringHeight = getTextHeight(g2);
        if (game.mouseX > points.get(1).x &&
                game.mouseX < points.get(2).x + quitStringLength &&
                game.mouseY > points.get(2).y - quitStringHeight + 12 &&
                game.mouseY < points.get(2).y) {
            quitHovered = true;
        }
    }

    public void draw(Graphics2D g2) {
        ArrayList<Point> points = new ArrayList<>();
        g2.setColor(new Color(0, 0, 0, 0.7F));
        g2.fillRect(0, 0, game.window.getWidth(), game.window.getHeight());

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28F));
        g2.setColor(Color.GREEN);

        String title = "Paused";
        int titleOffsetY = 100;

        Point titlePoint = getCenteredTextPoint(this.game, g2, title, titleOffsetY);
        g2.drawString(title, titlePoint.x, titlePoint.y);

        String[] menuItems = {"Main Menu", "Resume", "Quit"};
        int[] menuItemsOffset = {200, 250, 300};

        for (int i = 0; i < menuItems.length; i++) {
            Point point = getCenteredTextPoint(this.game, g2, menuItems[i], menuItemsOffset[i]);
            g2.drawString(menuItems[i], point.x, point.y);
            points.add(point);
        }

        update(points, g2);
    }
}