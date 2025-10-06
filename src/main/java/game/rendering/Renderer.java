package game.rendering;

import game.Vec3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final JPanel panel;
    private final Projector projector;
    private final double scaleFactor;

    private ArrayList<Polygon2D> polygons = new ArrayList<>();

    public Renderer(JPanel panel, Projector projector, double scaleFactor) {
        this.panel = panel;
        this.projector = projector;
        this.scaleFactor = scaleFactor;
    }

    public void render(Graphics2D g2) {
        polygons.sort((p1, p2) -> Double.compare(p2.depth(), p1.depth()));
        for (var polygon : polygons) {
            if (polygon.fill() != null) {
                g2.setColor(polygon.fill());
                g2.fillPolygon(
                        polygon.xPoints(),
                        polygon.yPoints(),
                        polygon.xPoints().length
                );
            }

            if (polygon.outline() != null) {
                g2.setColor(polygon.outline());
                g2.drawPolygon(
                        polygon.xPoints(),
                        polygon.yPoints(),
                        polygon.xPoints().length
                );
            }
        }
        polygons.clear();
    }

    public Vec3 scaled(Vec3 from) {
        return from.multiply(scaleFactor);
    }

    public void add3DFace(List<Vec3> points, Color fill, Color outline) {
        var newPoints = points
                .stream()
                .map(projector::project)
                .map(this::scaled)
                .map(projector::centered2D)
                .toList();

        int nPoints = newPoints.size();
        var xPoints = new int[nPoints];
        var yPoints = new int[nPoints];

        double depthSum = 0;
        for (int i = 0; i < nPoints; i++) {
            var point = newPoints.get(i);
            xPoints[i] = (int) point.x();
            yPoints[i] = (int) point.y();
            depthSum += point.z();
        }
        double depth = depthSum / nPoints;
        var polygon = new Polygon2D(xPoints, yPoints, depth, fill, outline);
        this.polygons.add(polygon);
    }

    public void addCube(Vec3 center, double size, Color fill, Color outline) {
        var cubeGeometry = new CubeGeometry(center, size);
        for (var face : cubeGeometry.getVisibleFaces()) {
            add3DFace(face, fill, outline);
        }
    }
}