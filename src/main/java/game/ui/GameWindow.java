package game.ui;

import game.backend.World;
import game.backend.WorldData;
import game.ui.components.panels.GamePanel;
import game.ui.components.panels.MainMenuPanel;
import game.ui.components.panels.SettingsPanel;
import game.ui.components.panels.GuidePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class GameWindow extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel container = new JPanel(layout);

    enum Menu {
        MAIN_MENU,
        GAME,
        SETTINGS,
        GUIDE
    }

    public GameWindow() {
        super(AppContext.WINDOW_NAME);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 1200);
        setLocationRelativeTo(null);
        setAppIcon();
        add(container);
        container.add(new MainMenuPanel(this), Menu.MAIN_MENU.name());
        container.add(new SettingsPanel(this), Menu.SETTINGS.name());
        container.add(new GuidePanel(this), Menu.GUIDE.name());

        container.setOpaque(false);
        container.setDoubleBuffered(true);

        goMenu();

        // hacky fix for alt-tabbing on macOS.
        // without this, the window doesn't regain focus when tabbing back in
        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                SwingUtilities.invokeLater(() -> {
                    var size = getSize();
                    setSize(size.width + 1, size.height + 1);
                    setSize(size);
                });
            }
        });
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
        SoundPlayer.playLoop("/assets/sounds/bg.wav", AppContext.persistentStorage().getVolume() / 100f / 2);

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

    public void goGuide() {
        showAndFocus(Menu.GUIDE.name());
    }

    private void setAppIcon() {
        try {
            var icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/assets/snake/food_cube.png"))).getImage();
            setIconImage(icon);
            Taskbar.getTaskbar().setIconImage(icon);
        } catch (Exception e) {
            System.err.println("Failed to set app icon: " + e.getMessage());
        }
    }
}
