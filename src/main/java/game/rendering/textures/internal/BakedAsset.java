package game.rendering.textures.internal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BakedAsset {
    protected final String fileName;
    protected final BufferedImage image;
    protected final double visibleHeight;
    protected final double aspectRatio;
    protected final double realAspectRatio;

    public BakedAsset(String folder, String fileName) {
        this.fileName = fileName;
        var file = getClass().getResourceAsStream("%s/%s".formatted(folder, fileName));

        try {
            this.image = ImageIO.read(file);
            this.visibleHeight = computeVisibleHeight();
            this.aspectRatio = (double) image.getHeight() / image.getWidth();
            this.realAspectRatio = visibleHeight / image.getWidth();
        } catch (IOException exception) {
            throw new RuntimeException("Image failed to load");
        }
    }

    private int computeVisibleHeight() {
        int top = -1, bottom = -1;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int alpha = (image.getRGB(x, y) >> 24) & 0xff;
                if (alpha > 0) {
                    if (top == -1) top = y;
                    bottom = y;
                    break;
                }
            }
        }

        if (top == -1) return 0;
        return bottom - top + 1;
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }
}