package game.ui;

import javax.swing.*;

public class GameLauncher {
    public static void runApp() {
        GameWindow window = new GameWindow();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        // Run on Swing's event dispatch thread
        SwingUtilities.invokeLater(GameLauncher::runApp);
    }
}