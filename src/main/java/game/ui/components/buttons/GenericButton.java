package game.ui.components.buttons;

import game.ui.SoundPlayer;
import game.utils.ColourUtils;
import game.rendering.textures.FontManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GenericButton extends JButton {
    private static final Color FOREGROUND_COLOR = new Color(38, 154, 204);
    private static final Color BACKGROUND_COLOR = new Color(10, 49, 46, 140);

    private Color currentBgColor = BACKGROUND_COLOR;

    public GenericButton(String string) {
        super(string);
        this.setFont(FontManager.get("oxygene", 40));
        this.setFocusPainted(false);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setOpaque(false);
        this.setForeground(FOREGROUND_COLOR);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBgColor = ColourUtils.brighten(BACKGROUND_COLOR, 1.3);
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBgColor = BACKGROUND_COLOR;
                repaint();
            }
        });

        addActionListener(e -> {
            SoundPlayer.play("/assets/sounds/menu_3.wav");
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(currentBgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

        super.paintComponent(g2);
        g2.dispose();
    }
}
