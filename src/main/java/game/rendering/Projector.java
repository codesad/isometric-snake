package game.rendering;

import game.utils.Vec3;

import javax.swing.*;

public class Projector {
    private final JPanel panel;
    private final double currentPitch;
    private final double currentYaw;

    public Projector(JPanel panel, double currentYaw, double currentPitch) {
        this.panel = panel;
        this.currentYaw = currentYaw;
        this.currentPitch = currentPitch;
    }

    public static Vec3 project(Vec3 point, double yaw, double pitch) {
        // yaw = rotation around Y
        double cosY = Math.cos(yaw);
        double sinY = Math.sin(yaw);

        double x1 = cosY * point.x() + sinY * point.z();
        double z1 = -sinY * point.x() + cosY * point.z();
        double y1 = point.y();

        // pitch = rotation around X
        double cosX = Math.cos(pitch);
        double sinX = Math.sin(pitch);

        double y2 = cosX * y1 - sinX * z1;
        double z2 = sinX * y1 + cosX * z1;

        return new Vec3(x1, y2, z2);
    }

    public Vec3 centered2D(Vec3 from) {
        int cx = this.panel.getWidth() / 2;
        int cy = this.panel.getHeight() / 2;


        return new Vec3(
                from.x() + cx,
                from.y() + cy,
                from.z()
        );
    }

    public Vec3 project(Vec3 point) {
        return project(point, this.currentYaw, this.currentPitch);
    }
}
