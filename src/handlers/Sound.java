package handlers;

public interface Sound {
    SoundHandler sound = new SoundHandler();
    public default void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
        sound.setVolume(-10.0f);
    }
    public default void stopMusic(){
        sound.stop();
    }
    public default void playSE(int i){
        sound.setFile(i);
        sound.play();
        sound.setVolume(-10.0f);
    }
}
