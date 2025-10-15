package game.rendering.drawables;

import java.awt.*;

public record Polygon2D(int[] xPoints, int[] yPoints, double depth, Color fill, Color outline) implements Drawable {
}
