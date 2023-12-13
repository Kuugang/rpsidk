package handlers;

import main.Game;

import entity.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import entity.buffs.AttackSpeedBuff;
import entity.bullets.Bullet;
import entity.smiley.Smiley;

public class EntityHandler{
    Game game;
    CollisionHandler collisionHandler;
    Player player;

    public CopyOnWriteArrayList<Entity> entities;

    private Timer timer;
    private double summonRate = 0.1;
    Random random;
    private long startTime;
    private long interval  = 1_000;
    private int elapsedTimeInSeconds;
    private boolean isPaused;

    public EntityHandler(Game game){
        random = new Random();
        startTime = System.currentTimeMillis();
        this.game = game;
        this.player = game.player;
        this.collisionHandler = new CollisionHandler(game.player, game);
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(this.player);

        isPaused = true;
        timer = new Timer();
        timer.scheduleAtFixedRate(new SpawnTask(), 0, interval);
    }

    public void pauseSpawnTask() {
        timer.cancel(); 
        isPaused = true;
    }

    public void resumeSpawnTask() {
        if (isPaused) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new SpawnTask(), 0, interval);
            isPaused = false;
        }
    }

    public void reset(){
        for(Entity e : entities){
            if(e instanceof Boss){
                ((Boss)e).destroyInstance();
            }
        }
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(this.player);
        this.player.bullets = new CopyOnWriteArrayList<>();
    }

    private class SpawnTask extends TimerTask {
        @Override
        public void run() {
            if(game.gameState == game.mainGameState){
                if(!game.paused){
                    spawnEntity();
                    long currentTime = System.currentTimeMillis();
                    elapsedTimeInSeconds = (int) ((currentTime - startTime) / 1000.0);
                    if(elapsedTimeInSeconds != 0 && elapsedTimeInSeconds % 60 == 0 && summonRate < 0.5){
                        summonRate = (double)((elapsedTimeInSeconds / 60) + 1) / 10; 
                    }
                }
            }
        }
    }


    public void spawnEntity(){
        if(this.elapsedTimeInSeconds % 1 == 0){
            // if(!entities.contains(Twins.getInstance(game))){
                // entities.add(0, Twins.getInstance(game));
            // }
            // if(!entities.contains(Smiley.getInstance(game))){
            //     entities.add(0, Smiley.getInstance(game));
            // }
        }

        if(Math.random() < summonRate){
            int n = 0;
            for(int i = 0; i < 3; i++){
                n = random.nextInt(3);
                if(n == 0)entities.add(0, new RockEnemy(game));
                if(n == 1)entities.add(0, new PaperEnemy(game));
                if(n == 2)entities.add(0, new ScissorEnemy(game));
            }
        }

        if(Math.random() < .2){
            if(!entities.contains(AttackSpeedBuff.getInstance(game))){
                entities.add(0, AttackSpeedBuff.getInstance(game));
            }
        }
    }

    public void update(){
        if(this.elapsedTimeInSeconds != 0 && this.elapsedTimeInSeconds % 120 == 0){
            for(Entity e: entities){
                if(e instanceof Enemy){
                    ((Enemy)e).setHealth(elapsedTimeInSeconds / 120);
                }
            }
        }
        for (Entity e : entities) {
            if(e.isActive){
                e.update();
            }else{
                entities.remove(e);
            }
        }

        collisionHandler.checkCollisions(this.entities, player.bullets);
    }

    public void draw(Graphics2D g2){
        for (Bullet bullet : player.bullets) {
            bullet.draw(g2);
        }
        for(Entity e : entities){
            if(e.isActive){
                e.draw(g2);

                if(KeyHandler.oneToggled){
                    if(e.mask != null && e.colRect != null){
                        g2.setColor(Color.RED);
                        e.drawMask(g2);
                        g2.setColor(Color.BLUE);
                        e.drawColRect(g2);
                    }
                }
            }
        }
    }
}