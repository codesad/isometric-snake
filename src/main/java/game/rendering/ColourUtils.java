package game.rendering;

import java.awt.*;

public class ColourUtils {
    public static Color darken(Color c, double factor) {
        return new Color(
                (int) Math.max(0, c.getRed() * factor),
                (int) Math.max(0, c.getGreen() * factor),
                (int) Math.max(0, c.getBlue()  * factor),
                c.getAlpha()
        );
    }

    public static Color brighten(Color c, double factor) {
        return new Color(
                (int) Math.min(255, c.getRed() * factor),
                (int) Math.min(255, c.getGreen() * factor),
                (int) Math.min(255, c.getBlue()  * factor),
                c.getAlpha()
        );
    }
}
