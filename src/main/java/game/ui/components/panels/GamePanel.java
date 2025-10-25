package game.ui.components.panels;

import game.backend.WorldListener;
import game.rendering.*;
import game.rendering.textures.FontManager;
import game.ui.AppContext;
import game.ui.GameKeyListener;
import game.ui.GameWindow;
import game.ui.SoundPlayer;
import game.ui.components.panels.internal.BackgroundPanel;
import game.utils.Vec3;
import game.backend.World;
import game.rendering.textures.GridTiles;

import javax.swing.*;

import java.awt.*;

public class GamePanel extends BackgroundPanel {
    public final GameWindow gameWindow;
    private World world;

    private final GameOverOverlayPanel gameOverOverlay;
    private boolean handledGameOver = false;
    public final Timer stepTimer = new Timer(320, e -> world.step());

    public GamePanel(GameWindow gameWindow, World world) {
        super();

        world.addListener(new WorldListener() {
            @Override
            public void onGameOver() {
                SoundPlayer.play("/assets/sounds/game_over.wav");
            }

            @Override
            public void onFoodEaten() {
                SoundPlayer.play("/assets/sounds/food_collect.wav");
            }
        });

        this.gameWindow = gameWindow;
        this.world = world;

        setOpaque(false);
        setFocusable(true);
        requestFocus();
        addKeyListener(new GameKeyListener(this, world));

        setLayout(new OverlayLayout(this));
        this.gameOverOverlay = new GameOverOverlayPanel();

        this.gameOverOverlay.onRestart = gameWindow::playGame;
        this.gameOverOverlay.onMenu = gameWindow::goMenu;

        add(this.gameOverOverlay);
        this.gameOverOverlay.setVisible(false);

        stepTimer.start();
    }


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
                    var head = world.getSnake().head();

                    if (world.getSnake().contains(currentVec)) {
                        renderer.addTile(GridTiles.SNAKE_SEGMENT,  currentVec, 0.2, 0.15);
                        continue;
                    }

                    if (world.getFoodPos().equals(currentVec)) {
                        if (y == world.getSnake().head().y()) {
                            renderer.addTile(GridTiles.FOOD_CUBE_SAME_LAYER,  currentVec, 0.2, 0.0);
                        } else {
                            renderer.addTile(GridTiles.FOOD_CUBE,  currentVec, 0.2, 0.3);
                        }
                        continue;
                    }

                    boolean isCurrentLevel =
                            (world.getDirection().isVertical() && x == head.x() && z == head.z()) ||
                                    (!world.getDirection().isVertical() && y == head.y());

                    if (isCurrentLevel) {
                        renderer.addTile(GridTiles.CURRENT_LAYER_TILE,  currentVec, 0.5, 0.5);
                        continue;
                    }


                    for (var segment : snakeSegments) {
                        if (isOccludedIso(currentVec, segment)) {
                            continue zLoop;
                        };
                    }

                    if (y < world.getSnake().head().y()) continue;

                    addSubCube(
                            currentVec,
                            new Color(255,255,255, 40),
                            null,
                            0.4
                    );
                }
            }
        }
        renderer.render(g2);
    }

    public void renderScores(Graphics2D g2) {
        FontHelper.drawFontWithShadow(
                g2,
                FontManager.get("oxygene", 50),
                Color.ORANGE,
                "HIGH SCORE: " + AppContext.persistentStorage().getHighScore(),
                20,
                20
        );

        FontHelper.drawFontWithShadow(
                g2,
                FontManager.get("oxygene", 50),
                Color.ORANGE,
                "CURRENT SCORE: " + world.getScore(),
                20,
                80
        );
    }

    public void handleGameOver() {
        gameOverOverlay.setScore(world.getScore());
        gameOverOverlay.setVisible(true);

        if (world.getScore() > AppContext.persistentStorage().getHighScore()) {
            AppContext.persistentStorage().setHighScore(world.getScore());
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        renderGrid(g2);

        // Outer cube
        renderer.addVisibleOnlyCube(Vec3.ZERO, 1, new Color(1, 1, 1, 20), Color.DARK_GRAY);
        renderer.render(g2);

        renderScores(g2);

        if (world.isGameOver() && !handledGameOver) {
            handleGameOver();
            handledGameOver = true;
        }
    }
}
