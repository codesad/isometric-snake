package game.rendering;

import game.utils.ColourUtils;

import java.awt.*;

public class FontHelper {
    public static void drawString(Graphics2D g2, String string, int x, int y) {
        g2.drawString(string, x, y + g2.getFontMetrics().getAscent());
    }
    public static void drawFontWithShadow(Graphics2D g2, Font font, Color colour, String string, int x, int y) {
        var oldComposite = g2.getComposite();

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        g2.setFont(font);
        g2.setColor(ColourUtils.darken(colour, 0.5));
        drawString(g2, string, x + 3, y + 3);

        g2.setComposite(oldComposite);

        g2.setColor(colour);
        drawString(g2, string, x, y);
    }
}
