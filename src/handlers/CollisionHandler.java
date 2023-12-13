package handlers;

import entity.Player;
import entity.Twins;
import entity.buffs.Buff;
import entity.Twin;
import entity.bullets.Bullet;
import entity.Enemy;
import entity.Entity;
import main.Game;

import java.awt.geom.Area;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionHandler implements Sound{
    Player player;
    Game game;
    public CollisionHandler(Player player, Game game){
        this.player = player;
        this.game = game;
    }

    public void checkCollisions(CopyOnWriteArrayList<Entity> entities, CopyOnWriteArrayList<Bullet> playerBullets) {
        player.bullets.removeIf(bullet -> !bullet.isActive);
        entities.removeIf(entity -> !entity.isActive);


        for(Entity e : entities){
            if(e instanceof Enemy){
                Enemy enemy = (Enemy)e;
                if(enemy instanceof Twins){
                    Twins twins = (Twins)enemy;
                    for(int i = 0; i < 2; i++){
                        Twin currentTwin = (i == 0) ? twins.twin1 : twins.twin2;
                        double distanceSquared = (currentTwin.x - player.x) * (currentTwin.x - player.x) + (currentTwin.y - player.y) * (currentTwin.y - player.y);
        
                        if (distanceSquared <= 200 * 200) {
                            Area intersection = new Area(player.mask);
                            intersection.intersect(currentTwin.mask);
                            if (!intersection.isEmpty()) {
                                player.takeDamage(enemy.getDamage(), false);
                            }
                        }
                        continue; 
                    }
                }else{
                    if(!(player.colRect.intersects(enemy.colRect)))continue;
                    Area intersection = new Area(player.mask);
                    intersection.intersect(enemy.mask);
                    if (!intersection.isEmpty()) {
                        player.takeDamage(enemy.getDamage(), false);
                        deactivate(enemy);
                    }
                }
            }
            if (e instanceof Buff) {
                double distanceSquared = (e.x - player.x) * (e.x - player.x) + (e.y - player.y) * (e.y - player.y);
                if (distanceSquared > 200 * 200) continue;

                Area intersection = new Area(player.mask);
                intersection.intersect(e.mask);
                if (!intersection.isEmpty()) {
                    ((Buff)e).applyBuff();
                }
            }
        }

        // check for playerBullet - enemy collision
        for (Bullet bullet : playerBullets) {
            if (!bullet.isActive) continue;
            for(Entity e : entities){
                if (!e.isActive) continue;
                if(e instanceof Enemy){
                    if(e instanceof Twins){
                        Twins twins = (Twins)e;
                        for(int i = 0; i < 2; i++){
                            Twin currentTwin = (i == 0) ? twins.twin1 : twins.twin2;
                            if (!(bullet.colRect.intersects(currentTwin.colRect)))continue;
                            Area intersection = new Area(bullet.mask);
                            intersection.intersect(currentTwin.mask);
                            if (!intersection.isEmpty()) {
                                handleCollision(twins, bullet, entities);
                                continue;
                            }
                        }
                        continue;
                    }

                    Enemy enemy = (Enemy)e;
                    if(bullet.bulletType == 1 && enemy.enemyType == 1)continue;
                    if(bullet.bulletType == 2 && enemy.enemyType == 2)continue;
                    if(bullet.bulletType == 3 && enemy.enemyType == 3)continue;

                    if(!bullet.colRect.intersects(enemy.colRect))continue;
                    Area intersection = new Area(bullet.mask);
                    intersection.intersect(enemy.mask);
                    if (!intersection.isEmpty()) {
                        handleCollision(enemy, bullet, entities);
                        break;
                    }
                }
            }
        }
    }

    private void handleCollision(Enemy enemy, Bullet bullet,  CopyOnWriteArrayList<Entity> entities) {
        int bulletType = bullet.bulletType;
        int enemyType = enemy.enemyType;
        try {
            switch (enemyType){
                case 1:
                    if(bulletType == 2){
                        playSE(1);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 3){
                        playSE(3);
                        deactivate(bullet);
                        enemy.speed+= 0.5;
                        return;
                    }
                    break;
                case 2:
                    if(bulletType == 3){
                        playSE(2);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 1){
                        playSE(1);
                        deactivate(bullet);
                        enemy.speed+= 0.5;
                        return;
                    }
                    break;
                case 3:
                    if(bulletType == 1){
                        playSE(3);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 2){
                        playSE(2);
                        deactivate(bullet);
                        enemy.speed+= 0.5;
                        return;
                    }
                    break;
                case 4:
                    deactivate(bullet);
                    deactivate(enemy);
                default:
                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void deactivate(Entity e) {
        if (e instanceof Enemy) {
            Enemy enemy = (Enemy) e;
            enemy.takeDamage(this.player.getDamage());
            if (enemy.getHealth() <= 0) {
                player.score++;
            }
        } else {
            e.isActive = false;
        }
    }
}