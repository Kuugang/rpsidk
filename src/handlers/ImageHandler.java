package handlers;

import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageHandler implements UtilityTool{
    public static BufferedImage[] playerFrames = new BufferedImage[5];
    public static BufferedImage enemyScissorImage;
    public static BufferedImage enemyRockImage;
    public static BufferedImage enemyPaperImage;
    public static BufferedImage rockBulletImage;
    public static BufferedImage scissorBulletImage;
    public static BufferedImage paperBulletImage;
    public static BufferedImage enemyRockAura;
    public static BufferedImage enemyPaperAura;
    public static BufferedImage enemyScissorsAura;
    public static BufferedImage mainMenuBackgroundImage;

    public static BufferedImage background;
    public static BufferedImage boss1;

    public static BufferedImage[] orbImages = new BufferedImage[96];
    public static BufferedImage[] gems = new BufferedImage[35];

    public static BufferedImage apple;

    public static BufferedImage spaceRock;

    public ImageHandler(){
        try{
            for (int i = 0; i < playerFrames.length; i++) {
                playerFrames[i]= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png")));
            }

            paperBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
            scissorBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/scissors.png"));
            rockBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/rock.png"));

            enemyRockImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/rock.png"));
            enemyPaperImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paper.png"));
            enemyScissorImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissors.png"));

            enemyRockAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/rockaura.png")));
            enemyPaperAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/paperaura.png")));
            enemyScissorsAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/scissorsaura.png")));

            mainMenuBackgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/background/bac1.png")));

            background =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/background/Background.png")));
            boss1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/boss1.png")));
            
            for (int i = 0; i < orbImages.length; i++) {
                orbImages[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/background/orbBuff/" + (i + 1) + ".png")));
                orbImages[i] = scaleImage(orbImages[i], 100, 100);

            }

            for (int i = 0; i < gems.length; i++) {
                gems[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/PurimoJemu/" + (i + 1) + ".png")));
            }

            apple =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/snake/apple.png")));
            spaceRock =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/bato.png")));
            
        }catch(IOException e){

            e.printStackTrace();
        }
    }
}