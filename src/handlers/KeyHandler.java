package handlers;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import main.Game;

public class KeyHandler implements KeyListener {
    public static boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, shiftPressed, tabPressed, escToggled;

    private Game game;
    public KeyHandler(Game game){
        this.game = game;
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        char keyChar = e.getKeyChar();
        if (keyChar == KeyEvent.VK_ESCAPE) {
            escToggled = !escToggled;
        }

        if(keyChar == KeyEvent.VK_TAB){
            this.game.player.toggleBulletType();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = true;
        }

        if(code == KeyEvent.VK_A){
            leftPressed = true;
        }

        if(code == KeyEvent.VK_S){
            downPressed = true;
        }

        if(code == KeyEvent.VK_D){
            rightPressed= true;
        }

        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }

        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = true;
        }

        if(code == KeyEvent.VK_TAB){
            tabPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W){
            upPressed = false;
        }

        if(code == KeyEvent.VK_A){
            leftPressed = false;
        }

        if(code == KeyEvent.VK_S){
            downPressed = false;
        }

        if(code == KeyEvent.VK_D){
            rightPressed= false;
        }

        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }

        if(code == KeyEvent.VK_SHIFT){
            shiftPressed = false;
        }

        if(code == KeyEvent.VK_TAB){
            tabPressed = false;
        }
    }
}
