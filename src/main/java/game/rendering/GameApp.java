package game.rendering;

import game.World;
import game.WorldData;

import javax.swing.*;
import java.awt.*;

public class GameApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            World world = new World(new WorldData(3, 3));
            GamePanel panel = new GamePanel(world);

            JFrame frame = new JFrame("Snake");
            frame.setBackground(Color.BLACK);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(panel);
            frame.setSize(1200, 1200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}