package entity.smiley;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import entity.Boss;
import entity.Enemy;
import entity.HealthBarEntity;
import main.Game;

public class Smiley extends Enemy implements Boss, HealthBarEntity {
    private static Smiley instance = null;
    private int health;
    protected Point2D.Double destination;
    long lastExecutionTime = System.currentTimeMillis();
    private RightHand rightHand;
    private LeftHand leftHand;
    private int attack1Duration = 300;
    protected Boolean attack1 = false;
    private int attackTimer =  0;
    private AffineTransform at;

    private Smiley(Game game) {
        super(game);
        this.id = 10;
        getImage();
        this.damage = 2;
        this.health = 50;
        this.maxHealth = 50;
        this.destination = validSpawnPoint(game);
        this.x = destination.x;
        this.y = destination.y;
        rightHand = new RightHand(this, game);
        leftHand = new LeftHand(this, game);
        this.colRect = this.mask.getBounds();
        this.HEALTH_BAR_WIDTH = (int) (this.image.getWidth() * 0.75);
    }

    public Point2D.Double validSpawnPoint(Game game) {
        double x = (int) (Math.random() * (game.getWidth() - 100 + 1)) + 100;
        double y = (int) (Math.random() * (game.getHeight() - 100 + 1)) + 100;
        Point2D.Double spawn = new Point2D.Double(x, y);
        return spawn;
    }

    public static Smiley getInstance(Game game) {
        if (instance == null) {
            instance = new Smiley(game);
        }
        return instance;
    }

    public void getImage(){
        this.image = this.game.imageHandler.getImage(this.id)[0];
        this.mask = new Area(this.game.maskHandler.getMask(this.id)[0]);
    }

    public void takeDamage(int damage){
        this.health -= damage;
        if (this.health <= 0) {
            this.isActive = false;
            instance = null;
        }
    }

    @Override
    public void destroyInstance() {
        instance = null;
    }

    public void updateMask(){
        Area newMask = this.game.maskHandler.getMask(this.id)[0];
        AffineTransform at = AffineTransform.getTranslateInstance(this.x , this.y);
        this.mask.reset();
        this.mask.add(newMask);
        this.mask.transform(at);
    }

    private void attack1(){
        if(attackTimer < attack1Duration){
            this.image = this.game.imageHandler.getImage(10)[1];
            this.leftHand.image = this.game.imageHandler.getImage(id)[5];
            this.rightHand.image = this.game.imageHandler.getImage(id)[5];
            attackTimer++;
        }else{
            isAttacking = false;
            attack1 = false;
            attackTimer = 0;
        }
    }


    public void update() {
        long currentTime = System.currentTimeMillis();

        if (currentTime - lastExecutionTime >= 1000) {
            if(!isAttacking){
                if(Math.random() < .2){
                    isAttacking = true;
                    attack1 = true;
                    for(int i = 0; i < 5; i++){
                        this.game.entityHandler.addEntity(new SmileyEnemy(game));
                    }
                }
                this.image = this.game.imageHandler.getImage(10)[0];
                this.leftHand.image = this.game.imageHandler.getImage(id)[4];
                this.rightHand.image = this.game.imageHandler.getImage(id)[3];
            }

            if(Math.random() < 0.7){
                this.destination = validSpawnPoint(game);
                leftHand.getNewDestination();
                rightHand.getNewDestination();
            }

            lastExecutionTime = currentTime;
        }

        if(isAttacking){
            attack1();
        }

        if(this.health < 25){
            this.image = this.game.imageHandler.getImage(10)[2];
        }
        if(this.health < 10){
            this.image = this.game.imageHandler.getImage(10)[6];
        }

        Point2D.Double destinationdirection = new Point2D.Double(this.destination.x - this.x, this.destination.y - this.y);
        double angle = Math.atan2(destinationdirection.y, destinationdirection.x);
        double dx = Math.cos(angle) * this.speed;
        double dy = Math.sin(angle) * this.speed;
        this.x += dx * this.speed;
        this.y += dy * this.speed;

        leftHand.update();
        rightHand.update();
        updateMask();
        this.colRect.setLocation((int) (this.x - this.image.getWidth() / 2.0), (int) (this.y - this.image.getHeight() / 2.0));
    }

    public void draw(Graphics2D g2) {
        at = AffineTransform.getTranslateInstance(this.x - image.getWidth() / 2.0, this.y - image.getHeight() / 2.0);
        g2.drawImage(this.image, at, null);
    
        leftHand.draw(g2);
        rightHand.draw(g2);

        drawHealthBar(g2, x, y, this.health, this.maxHealth, HEALTH_BAR_WIDTH, HEALTH_BAR_HEIGHT);
    }
}