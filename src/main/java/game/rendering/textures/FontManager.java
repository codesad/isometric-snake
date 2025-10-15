package game.rendering.textures;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class FontManager {
    private static final Map<String, Font> nameToFont = new HashMap<>();

    public static Font get(String fontName, float size) {
        if (!nameToFont.containsKey(fontName)) {
            loadBaseFont(fontName);
        }
        return nameToFont.get(fontName).deriveFont(size);
    }

    private static void loadBaseFont(String fontName) {
        try {
            var file = FontManager.class.getResourceAsStream("/assets/fonts/%s.ttf".formatted(fontName));

            nameToFont.put(
                    fontName,
                    Font.createFont(Font.TRUETYPE_FONT, file)
            );
        } catch (Exception e) {
            // Should never occur
            e.printStackTrace();
            nameToFont.put(
                    fontName,
                    new Font("SansSerif", Font.PLAIN, 12)
            );
        }
    }
}
