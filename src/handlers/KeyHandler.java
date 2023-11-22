package main;

import entity.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed, shiftPressed, tabPressed;
    Player player;
    public KeyHandler(Player player){
        this.player = player;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int code = e.getKeyCode();
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
            player.toggleBulletType();
        }
    }
}