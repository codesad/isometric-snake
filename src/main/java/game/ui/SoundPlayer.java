package game.ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {
    private static float linearToDecibel(float linear) {
        return linear != 0.0f
                ? (float) (20.0 * Math.log10(linear))
                : -144.0f;
    }

    public static void play(String path, float volume) {
        try {
            URL url = SoundPlayer.class.getResource(path);
            if (url == null) {
                System.err.println("Sound not found: " + path);
                return;
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(
                    Math.max(
                            linearToDecibel(volume),
                            gainControl.getMinimum()
                    )
            );

            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }

    }
}
