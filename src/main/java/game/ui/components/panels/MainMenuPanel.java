package game.ui.components.panels;

import game.rendering.textures.FontManager;
import game.ui.AppContext;
import game.ui.GameWindow;
import game.ui.components.buttons.GenericButton;
import game.ui.components.panels.internal.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainMenuPanel extends BackgroundPanel {
    private final JLabel titleLabel;
    private final JLabel descriptionLabel;

    private final GenericButton playButton;
    private final GenericButton settingsButton;
    private final GenericButton guideButton;
    private final GenericButton exitButton;

    Timer t = new Timer(4000, e -> {
        AppContext.resetBackground();
        repaint();
    });

    public MainMenuPanel(GameWindow gameWindow) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        titleLabel = new JLabel("ISOMETRIC SNAKE");
        titleLabel.setFont(FontManager.get("oxygene", 100));
        titleLabel.setForeground(new Color(69, 255, 100));

        descriptionLabel = new JLabel("AN EXTRA DIMENSION.");
        descriptionLabel.setFont(FontManager.get("oxygene", 40));
        descriptionLabel.setForeground(new Color(33, 229, 174));

        playButton = new GenericButton("PLAY");
        settingsButton = new GenericButton("SETTINGS");
        guideButton = new GenericButton("GUIDE");
        exitButton = new GenericButton("EXIT");

        playButton.addActionListener(e -> gameWindow.playGame());

        settingsButton.addActionListener(e -> gameWindow.goSettings());

        exitButton.addActionListener(e -> {
            gameWindow.dispose();
            System.exit(0);
        });

        var buttons = new GenericButton[] { playButton, settingsButton, guideButton, exitButton };


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(titleLabel, gbc);

        gbc.gridy++;
        gbc.insets = new Insets(10, 0, 100, 0);
        add(descriptionLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);
        for (var button : buttons) {
            gbc.gridy++;
            add(button, gbc);
        }

        setupBackgroundShuffle();
    }

    private void setupBackgroundShuffle() {
        t.start();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                t.restart();
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                t.stop();
            }
        });
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
