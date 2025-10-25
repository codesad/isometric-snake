package game.ui;

import game.backend.Direction;
import game.backend.World;
import game.ui.components.panels.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
    GamePanel panel;
    World world;

    public GameKeyListener(GamePanel panel, World world) {
        this.panel = panel;
        this.world = world;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void keyPressedOngoing(int k) {
        Direction toDirection = null;

        switch (k) {
            case KeyEvent.VK_LEFT -> toDirection = Direction.NEG_X;
            case KeyEvent.VK_RIGHT -> toDirection = Direction.POS_X;
            case KeyEvent.VK_UP -> toDirection = Direction.POS_Z;
            case KeyEvent.VK_DOWN -> toDirection = Direction.NEG_Z;
            case KeyEvent.VK_W -> toDirection = Direction.NEG_Y;
            case KeyEvent.VK_S -> toDirection = Direction.POS_Y;
            case KeyEvent.VK_ESCAPE -> panel.gameWindow.goMenu();
        }


        if (toDirection != null && !toDirection.isOppositeOf(world.getDirection())) {
            world.setDirection(toDirection);
            world.step();
            panel.stepTimer.restart();

            SoundPlayer.play("/assets/sounds/pop.wav", 0.3f);
        }
    }

    public void keyPressedGameOver(int k) {
        switch (k) {
            case KeyEvent.VK_ESCAPE -> panel.gameWindow.goMenu();
            case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE, KeyEvent.VK_R -> panel.gameWindow.playGame();
        }
    }

    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();
        if (!world.isGameOver()) keyPressedOngoing(k);
        else keyPressedGameOver(k);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
