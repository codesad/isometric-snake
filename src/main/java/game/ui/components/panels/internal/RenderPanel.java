package game.ui.components.panels.internal;

import game.rendering.*;
import game.rendering.Renderer;
import game.ui.AppContext;
import game.utils.ColourUtils;
import game.utils.Vec3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class RenderPanel extends JPanel implements ActionListener {
    final Projector projector;
    public Renderer renderer;

    private final Timer renderTimer = new Timer(16, this);  // ~60 FPS

    public RenderPanel() {
        this.projector = new Projector(
                this,
                Math.PI / 4,
                Math.toRadians(35.264)
        );
        updateRenderer();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateRenderer();
            }
        });

        renderTimer.start();
    }


    public void addSubCube(Vec3 position, Color fillColor, Color outlineColor, double surface) {
        var pos = position.multiply(AppContext.getSubCubeLength());

        var cubeGeometry = new CubeGeometry(pos, AppContext.getSubCubeLength() * surface);

        var topColour = ColourUtils.brighten(fillColor, 1.2);
        var rightColour = ColourUtils.darken(fillColor, 0.8);


        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.TOP),
                topColour,
                outlineColor
        );

        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.RIGHT),
                rightColour,
                outlineColor
        );

        renderer.add3DFace(
                cubeGeometry.getFace(CubeGeometry.Face.FRONT),
                fillColor,
                outlineColor
        );
    }


    private void updateRenderer() {
        double scale = Math.min(
                getWidth() / Math.sqrt(2.0),
                getHeight() / (2 * Math.sqrt(2.0 / 3.0))
        ) * 0.8;

        renderer = new Renderer(
                projector,
                scale
        );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
}
