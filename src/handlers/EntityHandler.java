package handlers;

import main.Game;

import java.awt.Graphics2D;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import entity.*;


public class EntityHandler{
    Game game;
    double spawnChance =  0.005;
    CollisionChecker collisionChecker;
    Player player;
    public CopyOnWriteArrayList<Enemy> enemies;
    public CopyOnWriteArrayList<Boss> boss;

    public CopyOnWriteArrayList<Entity> entities;
    Twins twins;
    public EntityHandler(Game game){
        this.game = game;
        this.player = game.player;
        this.collisionChecker = new CollisionChecker(game.player);
        this.twins  = new Twins(game);
        this.entities = new CopyOnWriteArrayList<>();
        this.entities.add(this.game.player);
    }

    public void reset(){
        this.enemies = new CopyOnWriteArrayList<>();

        // if (!boss.isEmpty()) {
        //     for (Boss b : boss) {
        //         b.dispose();
        //     }
        // }

        this.boss = new CopyOnWriteArrayList<>();
    }

    public void summonEnemy(){
        // if (boss.isEmpty()) {
            // boss.add(new Twins(game));
            // boss.add(new Snake(game));
        // }

        if(Math.random() < spawnChance){
            Random rand = new Random();
            int n = rand.nextInt(3);
            if(n == 0)entities.add(new RockEnemy(game));
            if(n == 1)entities.add(new PaperEnemy(game));
            if(n == 2)entities.add(new ScissorEnemy(game));
        }
    }

    public void update(){
        summonEnemy();
        this.twins.update();
        for (Entity e : entities) {
            e.update();
        }

        // for (Boss b : boss) {
        //     if(b instanceof Twins){
        //         if (!((Twins) b).isActive) {
        //             boss.remove(b);
        //             //todo fix lag upon removing 
        //             continue;
        //         }
        //     }
        //     b.update();
        // }

        collisionChecker.checkCollisions(this.entities, this.boss, player.bullets);
    }

    public void draw(Graphics2D g2){
        if(g2 != null){
            this.twins.draw(g2);

            for (Bullet bullet : player.bullets) {
                bullet.draw(g2);
            }

            for(Entity e : entities){
                e.draw(g2);
            }
        }
    }
}