package game.rendering;

import game.Direction;
import game.World;

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

    @Override public void keyPressed(KeyEvent e) {
        int k = e.getKeyCode();

        Direction toDirection = null;


        switch (k) {
            case KeyEvent.VK_LEFT -> toDirection = Direction.NEG_X;
            case KeyEvent.VK_RIGHT -> toDirection = Direction.POS_X;
            case KeyEvent.VK_UP -> toDirection = Direction.POS_Z;
            case KeyEvent.VK_DOWN -> toDirection = Direction.NEG_Z;
            case KeyEvent.VK_W -> toDirection = Direction.NEG_Y;
            case KeyEvent.VK_S -> toDirection = Direction.POS_Y;
        }

//        if (k == KeyEvent.VK_P) paused = !paused;

        if (toDirection != null && !toDirection.isOppositeOf(world.getDirection())) {
            world.setDirection(toDirection);
            world.step();
            panel.stepTimer.restart();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
