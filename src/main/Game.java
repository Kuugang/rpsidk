package main;

import entity.*;
import handlers.*;
import object.ObjectDrawerThread;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Game extends JPanel implements Runnable, Sound{
    public JFrame window;
    public int windowPosX;
    public int windowPosY;

    public int windowWidth = 576;
    public int windowHeight = 576;
    public int mouseX;
    public int mouseY;
    public int absoluteMouseX;
    public int absoluteMouseY;
    public MaskHandler maskCreationThread;
    public EnemyHandler enemyHandler;
    public Player player;
    public KeyHandler keyH;
    public MouseHandler mouseH;
    public MouseMotionHandler mouseMotionH;
//    public BackgroundHandler backgroundHandler; /** BACKGROUND TO DO **/

    // GameState variables
    public int mainMenuState = 0, mainGameState = 1, gameOverState = 2;
    public int gameState = mainGameState;
    public boolean paused;
    public MainMenu mainMenu;
    public PauseMenu pauseMenu;
    public GameOverMenu gameOverMenu;
    public FPS fps = new FPS();
    public ScoreBoard scoreBoard;
    Thread gameThread;
    ObjectDrawerThread objectDrawerThread = new ObjectDrawerThread(this);
    AssetSetter  assetSetter;
    public Graphics2D g2;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private final Object lock = new Object();
    public Game(){
        setWindowDefaults();
        this.setBackground(Color.black); /** BACKGROUND TO DO **/
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setSize(this.window.getWidth(), this.window.getHeight());
        this.setFocusTraversalKeysEnabled(false);

        this.maskCreationThread = new MaskHandler();

        assetSetter = new AssetSetter(this, objectDrawerThread);
        assetSetter.setObject();

        new ImageHandler();
        this.player = new Player(this);
        this.mouseMotionH = new MouseMotionHandler();
        this.addMouseMotionListener(mouseMotionH);
        this.keyH = new KeyHandler();

        this.mainMenu = new MainMenu(this);
        this.pauseMenu = new PauseMenu(this);
        this.gameOverMenu = new GameOverMenu(this);

        this.mouseH = new MouseHandler(this);

//        this.backgroundHandler = new BackgroundHandler(this); /** BACKGROUND TO DO **/

        this.enemyHandler = new EnemyHandler(this);

        this.addKeyListener(keyH);
        this.addMouseListener(mouseH);
        player.setHandlers(keyH, mouseH);
        this.scoreBoard = new ScoreBoard();
        window.add(this);
        this.requestFocusInWindow();
    }

    public void setWindowDefaults(){
        this.window =  new JFrame();
        this.window.setSize(windowWidth, windowHeight);

        window.setFocusTraversalKeysEnabled(false);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Game");

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.windowPosX = dim.width / 2 - this.window.getWidth() / 2;
        this.windowPosY = dim.height / 2 - this.window.getHeight() / 2;

        window.setLocation(windowPosX, windowPosY);

        window.setVisible(true);
        this.window.setAlwaysOnTop(true);
        objectDrawerThread.start();
//        executorService.submit(objectDrawerThread); // NECESSARY? DUNNO
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
        playMusic(0);
    }

    public void reset(){
        this.player.reset();
        this.enemyHandler.reset();
        this.gameState = mainGameState;
    }

    public void run(){
        while(gameThread != null){
                this.absoluteMouseX =  MouseInfo.getPointerInfo().getLocation().x;
                this.absoluteMouseY = MouseInfo.getPointerInfo().getLocation().y;
                if(mouseMotionH.hasMouseMoved()){
                    this.mouseX = mouseMotionH.getMouseX();
                    this.mouseY = mouseMotionH.getMouseY();
                }

                fps.update();

                if(fps.delta >= 1){
                    update();
                    fps.delta--;
                    fps.drawCount++;
                }


        }
    }

    public void update(){
        synchronized (lock) {
            if(this.gameState == this.mainMenuState){
                this.enemyHandler.reset();
            }

            if(this.gameState == this.mainGameState){
                if(KeyHandler.escToggled){
                    this.paused = true;
                }

                if(!KeyHandler.escToggled){
                    this.paused = false;
                }

                if(!this.paused){
    //                this.backgroundHandler.update(); /** BACKGROUND TO DO **/
                    if(this.player.getHealth() <= 0){
                        scoreBoard.addScore(String.valueOf(LocalDate.now()), player.score);
                        this.gameState = gameOverState;
                    }

                    player.update(this, this.window);
                    enemyHandler.update();
                }
            }
        }
    }

    protected void paintComponent(Graphics g){
        synchronized (lock) {
            if(this.gameThread != null){
                super.paintComponent(g);
                this.g2 = (Graphics2D)g;

                if(this.gameState == mainMenuState){
                    this.mainMenu.draw(g2);
                }

                if(this.gameState ==  mainGameState){
    //                this.backgroundHandler.draw(g2); /** BACKGROUND TO DO **/
                    objectDrawerThread.drawObjects(g2);


                    for (Bullet bullet : player.bullets) {
                        bullet.draw(g2);
                    }
                    if(!this.paused){
                        this.enemyHandler.draw(g2);
                    }

                    g2.setColor(Color.GREEN);
                    g2.drawString("Score: " + player.score, 5, 10);
                    g2.drawString("Health: " + player.getHealth() ,400, 10);

                    player.draw(g2);

                    if(this.paused){
                        this.pauseMenu.draw(g2);
                    }
                }

                if(this.gameState == gameOverState){
                    this.gameOverMenu.draw(g2);
                }

                g2.dispose();
            }
        }
    }
}
