package game.rendering;

import game.Vec3;
import game.World;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GamePanel extends JPanel implements ActionListener {
    private final Projector projector;
    private Renderer renderer;
    private World world;

    private final Timer renderTimer = new Timer(16, this);  // ~60 FPS
    public final Timer stepTimer = new Timer(320, e -> world.step());

    public GamePanel(World world) {
        this.world = world;

        setBackground(Color.BLACK);
        setFocusable(true);
        requestFocus();
        addKeyListener(new GameKeyListener(this, world));

        this.projector = new Projector(
                this,
                Math.PI / 4,
                Math.toRadians(35.264)
        );

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                double scale = Math.min(
                        getWidth() / Math.sqrt(2.0),
                        getHeight() / (2 * Math.sqrt(2.0 / 3.0))
                );
                renderer = new Renderer(
                        GamePanel.this,
                        projector,
                        scale * 0.8
                );
            }
        });

        renderTimer.start();
        stepTimer.start();
    }

    private static Color randomColor() {
        var rand = new java.util.Random();
        return new Color(
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256)
        );
    }

    public void addSubCube(Graphics2D g2, Vec3 position, CubeProperty cubeProperty) {
        int gridLength = world.getWorldData().getGrid().getLength();
        double cubeSize = 1.0 / gridLength;

        var pos = position.multiply(cubeSize);

        var cubeGeometry = new CubeGeometry(pos, cubeSize * cubeProperty.surface());

        var topColour = ColourUtils.brighten(cubeProperty.fillColor(), 1.2);
        var rightColour = ColourUtils.darken(cubeProperty.fillColor(), 0.8);


        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.TOP),
                topColour,
                cubeProperty.outlineColor()
        );

        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.RIGHT),
                rightColour,
                cubeProperty.outlineColor()
        );

        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.FRONT),
                cubeProperty.fillColor(),
                cubeProperty.outlineColor()
        );
    }

    public static CubeProperty EMPTY_CUBE = new CubeProperty(
            new Color(255,255,255, 40),
            null,
            0.8
    );

    public static CubeProperty CURRENT_LEVEL_CUBE = new CubeProperty(
            new Color(128, 186, 255, 100),
            null,
            0.8
    );

    public static CubeProperty FOOD_CUBE = new CubeProperty(
            new Color(252, 54, 11, 200),
            null,
            0.5
    );

    public static CubeProperty CURRENT_LAYER_FOOD_CUBE = new CubeProperty(
        new Color(222, 19, 36),
            new Color(190, 169, 20),
            0.65
    );

    public static CubeProperty SNAKE_CUBE = new CubeProperty(
            new Color(26, 213, 20),
            new Color(32, 147, 54),
            0.9
    );

    boolean isOccludedIso(Vec3 p, Vec3 s) {
        double dx = p.x() - s.x();
        double dy = p.y() - s.y();
        double dz = p.z() - s.z();

        if (dx == 0 && dy == 0 && dz == 0) return false;
        if (Math.abs(dx) != Math.abs(dy) || Math.abs(dy) != Math.abs(dz)) return false;
        if (!(Math.signum(-dx) == Math.signum(dy) && Math.signum(dy) == Math.signum(dz))) return false;

        return true;
    }

    public void renderGrid(Graphics2D g2) {
        int gridOffset = world.getWorldData().getGrid().getOffset();
        var snakeSegments = world.getSnake().segments();

        for (int x=-gridOffset; x<=gridOffset; x++) {
            for (int y=-gridOffset; y<=gridOffset; y++) {
                zLoop: for (int z=-gridOffset; z<=gridOffset; z++) {
                    var currentVec = new Vec3(x, y, z);

                    var cubeProperty = EMPTY_CUBE;

                    if (world.getDirection().isVertical() && x == world.getSnake().head().x()) {
                        cubeProperty = CURRENT_LEVEL_CUBE;
                    }

                    if (!world.getDirection().isVertical() && y == world.getSnake().head().y()) {
                        cubeProperty = CURRENT_LEVEL_CUBE;
                    }



                    if (world.getSnake().contains(currentVec)) {
                        cubeProperty = SNAKE_CUBE;
                    }

                    if (world.getFoodPos().equals(currentVec)) {
                        if (y == world.getSnake().head().y()) {
                            cubeProperty = CURRENT_LAYER_FOOD_CUBE;
                        } else {
                            cubeProperty = FOOD_CUBE;
                        }
                    }

                    if (cubeProperty == EMPTY_CUBE) {
                        for (var segment : snakeSegments) {
                            if (isOccludedIso(currentVec, segment)) {
                                continue zLoop;
                            };
                        }

                        if (y < world.getSnake().head().y()) continue;
                    }


                    addSubCube(
                            g2,
                            currentVec,
                            cubeProperty
                    );
                }
            }
        }
        renderer.render(g2);
    }


    @Override
    protected void paintComponent(Graphics g) {
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderGrid(g2);

        // Outer cube
        renderer.addCube(Vec3.ZERO, 1, null, Color.WHITE);
        renderer.render(g2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
