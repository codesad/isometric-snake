package game.rendering.textures.internal;

import game.utils.Vec3;

public class BakedTile extends BakedAsset {

    public BakedTile(String fileName) {
        super("/assets/tiles", fileName);
    }

    public BakedTile(String folder, String fileName) {
        super(folder, fileName);
    }



    public double getTopOffset() {
        double expectedHeight = 2.0 / Math.sqrt(3) * image.getWidth();
        double topOffset = (expectedHeight - visibleHeight * realAspectRatio);
        return topOffset / expectedHeight;
    }

    public Vec3 moveTop(Vec3 from) {
        return new Vec3(from.x(), from.y() - getTopOffset(), from.z());
    }
}