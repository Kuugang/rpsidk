package handlers;

import entity.Player;
import entity.Twins;
import entity.buffs.Orb;
import entity.Twin;
import entity.bullets.Bullet;
import entity.Enemy;
import entity.Entity;

import java.awt.geom.Area;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollisionChecker implements Sound{
    Player player;
    public CollisionChecker(Player player){
        this.player = player;
    }

    public void checkCollisions(CopyOnWriteArrayList<Entity> entities, CopyOnWriteArrayList<Bullet> playerBullets) {
        player.bullets.removeIf(bullet -> !bullet.isActive);
        entities.removeIf(entity -> !entity.isActive);



        //player - enemy collision
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
                                player.takeDamage();
                            }
                        }
                        continue; 
                    }
                }else{
                    double distanceSquared = (enemy.x - player.x) * (enemy.x - player.x) + (enemy.y - player.y) * (enemy.y - player.y);
                    if (distanceSquared > 200 * 200) continue;
                    Area intersection = new Area(player.mask);
                    intersection.intersect(enemy.mask);
                    if (!intersection.isEmpty()) {
                        player.takeDamage();
                        deactivate(enemy);
                    }
                }
            }
            if(e instanceof Orb){
                double distanceSquared = (e.x - player.x) * (e.x - player.x) + (e.y - player.y) * (e.y - player.y);
                if (distanceSquared > 200 * 200) continue;
                    Area intersection = new Area(player.mask);
                    intersection.intersect(e.mask);
                    if (!intersection.isEmpty()) {
                        player.speedBuff(5);

                        deactivate(e);
                    }
                // player.speed += 2;
                // player.reloadTime -= 15;
                // deactivate(e);

                //make mask for orb
            }
        }

        // check for playerBullet - enemy collision
        for (Bullet bullet : playerBullets) {
            if (!bullet.isActive) continue;
            for(Entity e : entities){
                if(e instanceof Enemy){
                    if(e instanceof Twins){
                        Twins twins = (Twins)e;
                        for(int i = 0; i < 2; i++){
                            Twin currentTwin = (i == 0) ? twins.twin1 : twins.twin2;
                            double distanceSquared = (bullet.x - currentTwin.x) * (bullet.x - currentTwin.x) + (bullet.y - currentTwin.y) * (bullet.y - currentTwin.y);
                            if (distanceSquared <= 200 * 200) {
                                Area intersection = new Area(bullet.mask);
                                intersection.intersect(currentTwin.mask);
                                if (!intersection.isEmpty()) {
                                    handleCollision(twins, bullet);
                                    continue;
                                }
                            }
                            continue; 
                        }
                        continue;
                    }

                    Enemy enemy = (Enemy)e;
                    if (!enemy.isActive) continue;
                    double distanceSquared = (bullet.x - enemy.x) * (bullet.x - enemy.x) + (bullet.y - enemy.y) * (bullet.y - enemy.y);
                    if (distanceSquared > 200 * 200) continue;
                    Area intersection = new Area(bullet.mask);
                    intersection.intersect(enemy.mask);
                    if (!intersection.isEmpty()) {
                        handleCollision(enemy, bullet);
                        break;
                    }
                }
            }
        }
    }

    private void handleCollision(Enemy enemy, Bullet bullet) {
        int bulletType = bullet.bulletType;
        int enemyType = enemy.enemyType;
        try {
            switch (enemyType){
                case 1:
                    if(bulletType == 2){
                        playSE(3);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 3){
                        playSE(2);
                        deactivate(bullet);
                        enemy.speed+= 0.5;
                        return;
                    }
                    break;
                case 2:
                    if(bulletType == 3){
                        playSE(1);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 1){
                        playSE(3);
                        deactivate(bullet);
                        enemy.speed+= 0.5;
                        return;
                    }
                    break;
                case 3:
                    if(bulletType == 1){
                        playSE(2);
                        deactivate(enemy);
                        deactivate(bullet);
                        return;
                    }
                    if(bulletType == 2){
                        playSE(1);
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
            if(enemy.getHealth() <= 0){
                player.score++;
            }
            return;
        }
        e.isActive = false;
    }
}