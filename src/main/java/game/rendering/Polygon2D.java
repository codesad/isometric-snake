package game.rendering;

import java.awt.*;

public record Polygon2D(int[] xPoints, int[] yPoints, double depth, Color fill, Color outline) {
}
