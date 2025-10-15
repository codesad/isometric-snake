package game.rendering.drawables;

import game.utils.Vec3;
import game.rendering.textures.internal.BakedTile;

public record Tile2D(BakedTile bakedTile, Vec3 relativePosition, Vec3 projectedPosition, double inset, double transparency) implements Drawable {
    @Override
    public double depth() {
        return projectedPosition.z();
    }
}
