package handlers;

import main.Game;

import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import entity.*;
import entity.buffs.Orb;
import entity.bullets.Bullet;

public class EntityHandler{
    Game game;
    CollisionChecker collisionChecker;
    Player player;

    public CopyOnWriteArrayList<Entity> entities;

    private Timer timer;
    private double summonRate = 0.2;
    Random random;
    private long startTime;
    private long interval  = 1_000;
    private int elapsedTimeInSeconds;
    Orb orb;
    public EntityHandler(Game game){
        random = new Random();
        startTime = System.currentTimeMillis();
        this.game = game;
        this.player = game.player;
        this.collisionChecker = new CollisionChecker(game.player);
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(this.game.player);

        timer = new Timer();
        timer.scheduleAtFixedRate(new SummonTask(), 0, interval);
        
        orb = new Orb(game);
        this.entities.add(orb);
    }


    private class SummonTask extends TimerTask {
        @Override
        public void run() {
            summonEnemy();
            long currentTime = System.currentTimeMillis();
            elapsedTimeInSeconds = (int) ((currentTime - startTime) / 1000.0);
        }
    }

    public void reset(){
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(this.game.player);
    }

    public void summonEnemy(){
        if(this.elapsedTimeInSeconds != 0 && this.elapsedTimeInSeconds % 1 == 0){
            if(!entities.contains(Twins.getInstance(game))){
                entities.add(0, Twins.getInstance(game));
            }
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
    }

    public void update(){
        if(this.elapsedTimeInSeconds != 0 && this.elapsedTimeInSeconds % 60 == 0){
            for(Entity e: entities){
                if(e instanceof Enemy){
                    ((Enemy)e).setHealth(((Enemy)e).getHealth() + 1);
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

        
        collisionChecker.checkCollisions(this.entities, player.bullets);
    }

    public void draw(Graphics2D g2){
        for (Bullet bullet : player.bullets) {
            bullet.draw(g2);
        }
        for(Entity e : entities){
            if(e.isActive){
                e.draw(g2);
            }
        }
    }
}