package game.backend;

import game.utils.Vec3;

public class Grid {
    private final int offset;
    private final int length;

    public Grid(int offset) {
        this.offset = offset;
        this.length = 2 * offset + 1;
    }

    public boolean inBounds(Vec3 point) {
        return Math.abs(point.x()) <= this.offset
                && Math.abs(point.y()) <= this.offset
                && Math.abs(point.z()) <= this.offset;
    }

    public Vec3 teleport(Vec3 point) {
        double x = wrap(point.x(), offset);
        double y = wrap(point.y(), offset);
        double z = wrap(point.z(), offset);
        return new Vec3(x, y, z);
    }

    private double wrap(double value, int offset) {
        double size = 2 * offset + 1;
        double shifted = value + offset; // shift range to [0, size)
        double wrapped = ((shifted % size) + size) % size; // safe modulo
        return wrapped - offset; // shift back to [-offset, offset]
    }

    public int getOffset() {
        return this.offset;
    }

    public int getLength() {
        return this.length;
    }

    public static Vec3 fromCenter(Direction direction, int scale) {
        Vec3 center = new Vec3(0, 0, 0);
        return center.add(direction.times(scale));
    }
}
