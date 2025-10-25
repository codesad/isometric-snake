package game.ui;

import game.backend.World;
import game.backend.WorldData;
import game.ui.components.panels.GamePanel;
import game.ui.components.panels.MainMenuPanel;
import game.ui.components.panels.SettingsPanel;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    enum Menu {
        MAIN_MENU,
        GAME,
        SETTINGS
    }

    public GameWindow() {
        super(AppContext.WINDOW_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLocationRelativeTo(null);
        add(container);
        container.add(new MainMenuPanel(this), Menu.MAIN_MENU.name());
        container.add(new SettingsPanel(this), Menu.SETTINGS.name());

        container.setOpaque(false);
        container.setDoubleBuffered(true);

        goMenu();
    }

    public void showAndFocus(String name) {
        layout.show(container, name);

        // Focus the panel
        Component comp = null;
        for (Component c : container.getComponents()) {
            if (c.isVisible()) {
                comp = c;
                break;
            }
        }

        if (comp != null) {
            comp.requestFocusInWindow();
        }
    }

    public void playGame() {
        var worldData = new WorldData(AppContext.persistentStorage().gridOffset(), 3);
        var world = new World(worldData, AppContext.RNG);

        container.add(new GamePanel(this, world), Menu.GAME.name());

        SwingUtilities.invokeLater(() -> {
            showAndFocus(Menu.GAME.name());
        });
    }

    public void goMenu() {
        showAndFocus(Menu.MAIN_MENU.name());
    }

    public void goSettings() {
        showAndFocus(Menu.SETTINGS.name());
    }
}
