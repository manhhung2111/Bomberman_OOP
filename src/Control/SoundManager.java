package Control;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {
    public static boolean isPlayBackgroundMusic = true;
    public static boolean isPlayEffectSound = true;
    private static Clip backgroundMusicClip = null;

    public static void playBackGroundMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        if(backgroundMusicClip == null){
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/BackgroundMusic.wav"));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
        }
        if(isPlayBackgroundMusic){
            backgroundMusicClip.setMicrosecondPosition(0);
            backgroundMusicClip.start();
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }else{
            backgroundMusicClip.stop();
        }
    }

    public static void playClickSound(){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/MouseClick.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }

    public static void playCollectItemsSound(){
        if(isPlayEffectSound){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/collectIem.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void playBombExplosionSound(){
        if(isPlayEffectSound){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/bombExplosion.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void playPlayerDeathSound(){
        if(isPlayEffectSound){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/playerDeath.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void playBombPlantedSound(){
        if(isPlayEffectSound){
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("res/menu/Sound/bombPlanted.wav"));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
