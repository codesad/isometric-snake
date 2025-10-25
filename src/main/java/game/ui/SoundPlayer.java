package game.ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private static Clip backgroundClip;

    private static float linearToDecibel(float linear) {
        return linear != 0.0f
                ? (float) (20.0 * Math.log10(linear))
                : -144.0f;
    }

    public static void play(String path, float volume) {
        playInternal(path, volume, false);
    }

    public static void play(String path) {
        play(path, AppContext.persistentStorage().getVolume() / 100f);
    }

    public static void playLoop(String path) {
        playLoop(path, AppContext.persistentStorage().getVolume() / 100f);
    }

    public static void playLoop(String path, float volume) {
        stopLoop();
        backgroundClip = playInternal(path, volume, true);
    }

    public static void stopLoop() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    private static Clip playInternal(String path, float volume, boolean loop) {
        try {
            URL url = SoundPlayer.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return null;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(
                    Math.max(linearToDecibel(volume), gainControl.getMinimum())
            );

            if (loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            return null;
        }
    }
}