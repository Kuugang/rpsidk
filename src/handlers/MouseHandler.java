package handlers;

import main.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    public boolean shoot;
    public boolean leftClicked;

    Game game;
    public MouseHandler(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            if(this.game.gameState == game.mainMenuState){
                if(this.game.mainMenu.playHovered){
                    this.game.reset();
                }
                if(this.game.mainMenu.highScoresHovered){
                    this.game.mainMenu.state = 1;
                }
                if(this.game.mainMenu.quitHovered){
                    System.exit(0);
                }
                if(this.game.mainMenu.backHovered){
                    this.game.mainMenu.state = 0;
                }
            }

            if(this.game.paused){
                if(this.game.pauseMenu.resumeHovered){
                    KeyHandler.escToggled = false;
                    this.game.paused = false;
                }
                if(this.game.pauseMenu.mainMenuHovered){
                    KeyHandler.escToggled = false;
                    this.game.paused = false;
                    this.game.gameState = game.mainMenuState;
                }
                if(this.game.pauseMenu.quitHovered){
                    System.exit(0);
                }
            }

            if(this.game.gameState == this.game.gameOverState){
                if(this.game.gameOverMenu.mainMenuHovered){
                    this.game.gameState = 0;
                }
                if(this.game.gameOverMenu.retryHovered){
                    this.game.reset();
                }
                if(this.game.gameOverMenu.quitHovered){
                    System.exit(0);
                }
            }
        }
    }

    /**
     * Invoked when a mouse button is pressed.
     *
     * @param e The MouseEvent containing information about the event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Get the code representing the pressed mouse button
        int code = e.getButton();

        // Check if the left mouse button (BUTTON1) is pressed
        if (code == MouseEvent.BUTTON1) {
            // Indicate that a shooting action is triggered
            shoot = true;

            // Indicate that the left mouse button is clicked
            leftClicked = true;

            // Check if teleport buff is active
            if (game.player.teleportBuffIsActive) {
                // Deactivate shooting during teleportation
                shoot = false;

                // Get the adjusted coordinates for teleportation, considering player's image dimensions
                int mouseX = e.getX() - game.player.image.getWidth() / 2;
                int mouseY = e.getY() - game.player.image.getHeight() / 2;

                // Trigger teleportation logic with a duration of 1000 milliseconds (1 second)
                game.player.teleportPlayerTo(mouseX, mouseY); // BLINK BLINK
                game.player.teleportPlayerTo(mouseX, mouseY, 1000); // FORCE STAFF

                // Deactivate the teleport buff after usage
                game.player.teleportBuffIsActive = false;
            }
        }
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if(code == 1){
            shoot = false;
            leftClicked = false;
        }
        if(code == 3){
            this.game.player.toggleBulletType();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
