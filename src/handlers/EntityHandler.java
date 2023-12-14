package handlers;

import main.Game;

import entity.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import entity.buffs.PlayerBuff;
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
    private boolean bossStatus = false;
    private int buffSpawnTime;

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

    public void addEntity(Entity e){
        entities.add(e);
    }

    public void spawnEntity(){
        for(Entity e:  entities){
            if(e instanceof Boss)
                bossStatus = true;
        }
        if(!bossStatus){
            if(this.elapsedTimeInSeconds != 0 && this.elapsedTimeInSeconds % 60 == 0){
                int type = random.nextInt(2);
                switch (type) {
                    case 0:
                        entities.add(0, Twins.getInstance(game));
                        break;
                    case 1:
                        entities.add(0, Smiley.getInstance(game));
                        break;
                    case 2:
                        //wa nay time:
                        break;
                    default:
                        break;
                }
            }
        }
        bossStatus = false;

        if(Math.random() < summonRate){
            int n = 0;
            for(int i = 0; i < 3; i++){
                n = random.nextInt(3);
                if(n == 0)entities.add(0, new RockEnemy(game));
                if(n == 1)entities.add(0, new PaperEnemy(game));
                if(n == 2)entities.add(0, new ScissorEnemy(game));
            }
        }

        if(Math.random() < 1){
            if(!entities.contains(PlayerBuff.getInstance(game))){
                entities.add(0, PlayerBuff.getInstance(game));
                buffSpawnTime = elapsedTimeInSeconds;
            }else{
                if(elapsedTimeInSeconds - buffSpawnTime == 20){
                    PlayerBuff pb = PlayerBuff.getInstance(game);
                    pb.destroyInstance();
                } 
            }
        }
    }

    public void update(){
        if(this.elapsedTimeInSeconds % 120 == 0){
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

    public void freezeEnemy() {
        for (Entity e : entities) {
            if (e instanceof Enemy) {
                ((Enemy)e).isAttacking = false; 
                e.speed = 0;
                ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.schedule(() -> {
                    ((Enemy)e).isAttacking = false; 
                    e.speed = 1;
                    executorService.shutdown();
                }, 5, TimeUnit.SECONDS);
            }
        }
    }

}