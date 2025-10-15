package game.utils;

public record Vec2(double x, double z) {
    public static final Vec2 ZERO = new Vec2(0, 0);

    public Vec2 add(Vec2 o) {
        return new Vec2(x + o.x, z + o.z);
    }

    public Vec2 add(double n) {
        return new Vec2(x + n, z + n);
    }

    public Vec2 multiply(double n) {
        return new Vec2(x * n, z * n);
    }
}