package game.ui.components.panels;

import game.rendering.textures.FontManager;
import game.ui.components.buttons.GenericButton;

import javax.swing.*;
import java.awt.*;

public class GameOverOverlayPanel extends JPanel {
    private final JLabel titleLabel;
    private final JLabel scoreLabel;
    private final JButton restartButton;
    private final JButton menuButton;

    public Runnable onRestart = () -> {};
    public Runnable onMenu = () -> {};

    public GameOverOverlayPanel() {
        setOpaque(false);
        setLayout(new GridBagLayout());

        titleLabel = new JLabel("YOU LOST");
        titleLabel.setFont(FontManager.get("oxygene", 80));
        titleLabel.setForeground(new Color(255, 69, 69));

        scoreLabel = new JLabel("SCORE: 0");
        scoreLabel.setFont(FontManager.get("oxygene", 50));
        scoreLabel.setForeground(Color.ORANGE);

        restartButton = new GenericButton("RESTART");
        menuButton = new GenericButton("MAIN MENU");

        restartButton.addActionListener(e -> {
            onRestart.run();
        });

        menuButton.addActionListener(e -> {
            onMenu.run();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(restartButton);
        buttonPanel.add(menuButton);



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(titleLabel, gbc);

        gbc.gridy++;
        add(scoreLabel, gbc);

        gbc.gridy++;
        add(buttonPanel, gbc);
    }

    public void setScore(int score) {
        scoreLabel.setText("SCORE: " + score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        super.paintComponent(g);
    }
}
