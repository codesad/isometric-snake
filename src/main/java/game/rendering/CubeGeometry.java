package game.rendering;

import game.utils.Vec3;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CubeGeometry {
    private final Vec3 center;
    private final double size;
    private final Map<Face, List<Vec3>> faceMap;

    public enum Face {
        BACK, FRONT, BOTTOM, TOP, RIGHT, LEFT
    }

    public CubeGeometry(Vec3 center, double size) {
        this.center = center;
        this.size = size;
        this.faceMap = generateFaces();
    }

    private Map<Face, List<Vec3>> generateFaces() {
        double s = size / 2.0;

        double cx = center.x();
        double cy = center.y();
        double cz = center.z();

        Vec3[] v = new Vec3[]{
                new Vec3(cx - s, cy - s, cz - s),
                new Vec3(cx + s, cy - s, cz - s),
                new Vec3(cx + s, cy + s, cz - s),
                new Vec3(cx - s, cy + s, cz - s),

                new Vec3(cx - s, cy - s, cz + s),
                new Vec3(cx + s, cy - s, cz + s),
                new Vec3(cx + s, cy + s, cz + s),
                new Vec3(cx - s, cy + s, cz + s)
        };

        return Map.of(
                Face.FRONT,   List.of(v[0], v[1], v[2], v[3]),
                Face.BACK,  List.of(v[4], v[5], v[6], v[7]),
                Face.TOP, List.of(v[0], v[1], v[5], v[4]),
                Face.BOTTOM,    List.of(v[2], v[3], v[7], v[6]),
                Face.RIGHT,  List.of(v[1], v[2], v[6], v[5]),
                Face.LEFT,   List.of(v[0], v[3], v[7], v[4])
        );
    }

    public List<List<Vec3>> getFaces() {
        return new ArrayList<>(faceMap.values());
    }

    public List<List<Vec3>> getVisibleFaces() {
        return List.of(
                faceMap.get(Face.TOP),
                faceMap.get(Face.RIGHT),
                faceMap.get(Face.FRONT)
        );
    }

    public List<Vec3> getFace(Face face) {
        return faceMap.get(face);
    }
}