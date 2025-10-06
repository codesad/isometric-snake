package game;

public enum Direction {
    POS_X( 1, 0, 0),
    NEG_X(-1, 0, 0),
    POS_Y( 0, 1, 0),
    NEG_Y( 0,-1, 0),
    POS_Z( 0, 0, 1),
    NEG_Z( 0, 0,-1);

    private final Vec3 delta;
    Direction(int x, int y, int z) { this.delta = new Vec3(x, y, z); }
    public Vec3 delta() { return delta; }

    public Vec3 times(int k) {
        return new Vec3(delta.x() * k, delta.y() * k, delta.z() * k);
    }

    public boolean isOppositeOf(Direction other) {
        return delta.x() == -other.delta.x()
                && delta.y() == -other.delta.y()
                && delta.z() == -other.delta.z();
    }

    public boolean isVertical() {
        return this == POS_Y || this == NEG_Y;
    }
}