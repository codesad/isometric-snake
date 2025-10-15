package game.rendering;

import game.rendering.drawables.Drawable;
import game.rendering.drawables.Polygon2D;
import game.ui.AppContext;
import game.utils.Vec3;
import game.rendering.textures.internal.BakedTile;
import game.rendering.drawables.Tile2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Renderer {
    private final Projector projector;
    public final double scaleFactor;

    private final ArrayList<Drawable> drawables = new ArrayList<>();

    public Renderer(Projector projector, double scaleFactor) {
        this.projector = projector;
        this.scaleFactor = scaleFactor;
    }

    private int getTileDiagonalLength() {
        return (int) Math.round(Math.round(AppContext.getSubCubeLength() * this.scaleFactor * Math.sqrt(2)));
    }

    private void renderPolygon(Graphics2D g2, Polygon2D polygon) {
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

    private void renderTile(Graphics2D g2, Tile2D tile2D) {
        double filled = 1.0 - tile2D.inset();

        double width = getTileDiagonalLength() * filled;
        double height = width * tile2D.bakedTile().getAspectRatio();

        Composite oldComposite = g2.getComposite();

        float alpha = (float) Math.max(0.0, Math.min(1.0, 1.0 - tile2D.transparency()));
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        g2.drawImage(
                tile2D.bakedTile().getImage(),
                (int) Math.round(tile2D.projectedPosition().x() - width / 2.0),
                (int) Math.round(tile2D.projectedPosition().y() - height / 2.0),
                (int) Math.round(width),
                (int) Math.round(height),
                null
        );
        // Restore composite
        g2.setComposite(oldComposite);
    }

    public void render(Graphics2D g2) {
        drawables.sort((a, b) -> {
            // first depth, always
            int depthCompare = Double.compare(b.depth(), a.depth());
            if (depthCompare != 0) return depthCompare;

            // then if both are tiles, compare Y
            boolean aTile = a instanceof Tile2D;
            boolean bTile = b instanceof Tile2D;

            if (aTile && bTile) {
                var ta = (Tile2D) a;
                var tb = (Tile2D) b;
                int yCompare = Double.compare(tb.relativePosition().y(), ta.relativePosition().y());
                if (yCompare != 0) return yCompare;
            }

            // fallback
            return Integer.compare(System.identityHashCode(a), System.identityHashCode(b));
        });

        for (var drawable : drawables) {
            if (drawable instanceof Polygon2D) {
                renderPolygon(g2, (Polygon2D) drawable);
            } else if (drawable instanceof Tile2D) {
                renderTile(g2, (Tile2D) drawable);
            }
        }

        drawables.clear();
    }

    public Vec3 scaled(Vec3 from) {
        return from.multiply(scaleFactor);
    }

    public List<Vec3> getScreenCoordinates(List<Vec3> points) {
        return points
                .stream()
                .map(projector::project)
                .map(this::scaled)
                .map(projector::centered2D)
                .toList();
    }

    public void add3DFace(List<Vec3> points, Color fill, Color outline) {
        var newPoints = getScreenCoordinates(points);

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
        this.drawables.add(polygon);
    }

    public void addVisibleOnlyCube(Vec3 center, double size, Color fill, Color outline) {
        var cubeGeometry = new CubeGeometry(center, size);
        for (var face : cubeGeometry.getVisibleFaces()) {
            add3DFace(face, fill, outline);
        }
    }

    public void addCube(Vec3 center, double size, Color fill, Color outline) {
        var cubeGeometry = new CubeGeometry(center, size);
        for (var face : cubeGeometry.getFaces()) {
            add3DFace(face, fill, outline);
        }
    }

    public void addTile(BakedTile bakedTile, Vec3 position, double inset, double transparency) {
        var world = position.multiply(AppContext.getSubCubeLength());
        var projectedPosition = projector.centered2D(this.scaled(projector.project(world)));
        drawables.add(new Tile2D(bakedTile, position, projectedPosition, inset, transparency));
    }
}