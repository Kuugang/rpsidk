package handlers;

import main.UtilityTool;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
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

    public static BufferedImage[] orbImages = new BufferedImage[91];
    public static BufferedImage[] gems = new BufferedImage[35];

    public static BufferedImage smiley;
    public static BufferedImage thumbsUp;

    static HashMap<Integer, BufferedImage[]> images = new HashMap<>();;

    public ImageHandler(){
        try{
            BufferedImage[] playerImages = new BufferedImage[5];
            for(int i = 0; i < playerImages.length; i++){
                playerImages[i] =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png"))); 
            }
            images.put(1, playerImages);

            // for (int i = 0; i < playerFrames.length; i++) {
            //     playerFrames[i]= ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png")));
            // }

            // paperBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
            // scissorBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/scissors.png"));
            // rockBulletImage =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/rock.png"));

            // enemyRockImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/rock.png"));
            // enemyPaperImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paper.png"));
            // enemyScissorImage =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissors.png"));

            // enemyRockAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/rockaura.png")));
            // enemyPaperAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/paperaura.png")));
            // enemyScissorsAura = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/scissorsaura.png")));

            // boss1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/twin.png")));
            
            // for (int i = 0; i < orbImages.length; i++) {
            //     orbImages[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/buff/orb/" + (i + 1) + ".png")));
            //     orbImages[i] = scaleImage(orbImages[i], 89, 129);

            // }

            // for (int i = 0; i < gems.length; i++) {
            //     gems[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/PurimoJemu/" + (i + 1) + ".png")));
            // }
            
            // smiley = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/smiley.png")));
            // thumbsUp = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/thumbs-up.png")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private BufferedImage[] addImage(int entityId){
        BufferedImage image[] = null;
        try{
            switch(entityId){
                case 1:
                    image = new BufferedImage[5];
                    for(int i = 0; i < image.length; i++){
                        image[i] =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/player/idle" + (i + 1) + ".png"))); 
                    }
                    break;
                case 2:
                    image = new BufferedImage[1];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/rock.png"));
                    break;
                case 3:
                    image = new BufferedImage[1];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/paper.png"));
                    break;
                case 4:
                    image = new BufferedImage[1];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/bullets/scissors.png"));
                    break;
                case 5:
                    image = new BufferedImage[2];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/rock.png"));
                    image[1] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/rockaura.png"));
                    break;
                case 6:
                    image = new BufferedImage[2];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paper.png"));
                    image[1] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/paperaura.png"));
                    break;
                case 7:
                    image = new BufferedImage[2];
                    image[0] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissors.png"));
                    image[1] =  ImageIO.read(getClass().getResourceAsStream("../resource/enemies/scissorsaura.png"));
                    break;
                case 8:
                    image = new BufferedImage[1];
                    image[0] =  ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/twin.png")));
                    break;
                case 9:
                    image = new BufferedImage[91];
                    for (int i = 0; i < orbImages.length; i++) {
                        image[i] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/buff/orb/" + (i + 1) + ".png")));
                        image[i] = scaleImage(image[i], 45, 64);
                    }
                    break;
                case 10:
                    image = new BufferedImage[6];
                    image[0] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/smiley.png")));
                    image[1] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/super-angry.png")));
                    image[2] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/omg.png")));

                    image[3] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/thumbs-up.png")));
                    image[3] = flipHorizontal(image[3]);
                    image[4] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/thumbs-up.png")));
                    image[5] = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("../resource/enemies/smiley/middle-finger.png")));

                    break;
            }
            images.put(entityId, image);
        }catch(IOException e){
            e.printStackTrace();
        }
        return image;
    }

    public BufferedImage[] getImage(int entityId){
        if(!images.containsKey(entityId)){
            BufferedImage[] image = addImage(entityId);
            images.put(entityId, image);
            return image;
        }
        return images.get(entityId);
    }
}