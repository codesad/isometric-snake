package game.ui.components.panels;

import game.rendering.textures.FontManager;
import game.ui.AppContext;
import game.ui.GameWindow;
import game.ui.components.buttons.GenericButton;
import game.ui.components.panels.internal.BackgroundPanel;

import javax.swing.*;
import java.awt.*;
import java.lang.StringBuilder;

public class GuidePanel extends BackgroundPanel {
    private final JLabel titleLabel;
    private final JLabel gameplayLabel;
    private final JLabel controlsLabel;

    private final JTextArea gameplayTextArea;
    private final JTextArea controlsTextArea;

    private final GenericButton backButton;

    public GuidePanel(GameWindow gameWindow) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        titleLabel = new JLabel("GUIDE");
        titleLabel.setFont(FontManager.get("oxygene", 100));
        titleLabel.setForeground(new Color(69, 255, 100));

        gameplayLabel = new JLabel("GAMEPLAY");
        gameplayLabel.setFont(FontManager.get("oxygene", 50));
        gameplayLabel.setForeground(new Color(49, 241, 137));

        StringBuilder gameplaySb = new StringBuilder();
        gameplaySb.append("YOU ARE A VERY HUNGRY SNAKE TRYING TO EAT AS MUCH\nFRUIT AS YOU CAN\n\n");
        gameplaySb.append("HOWEVER, CRUEL NATURE HAS DECIDED TO MAKE IT DIFFICULT\nFOR YOU, AND PUT YOU IN AN ISOMORPHIC 3D WORLD\n\n");
        gameplaySb.append("COLLECT AS MUCH FRUIT AS POSSIBLE, AND GROW CONTINOUSLYLONGER ALL THE WHILE\n\n");
        gameplaySb.append("ALTHOUGH WATCH OUT, AS YOU GROW LONGER, YOU MIGHT\nGET STUCK AND ACCIDENTALLY CRASH INTO YOURSELF\n\n");
        gameplaySb.append("THE MORE FRUIT YOU COLLECT, THE HIGHER YOUR SCORE");

        gameplayTextArea = new JTextArea(gameplaySb.toString());
        gameplayTextArea.setFont(FontManager.get("oxygene", 30));
        gameplayTextArea.setSize(1028, 1028);
        gameplayTextArea.setForeground(new Color(33, 229, 174));
        gameplayTextArea.setOpaque(false);
        gameplayTextArea.setLineWrap(true);

        controlsLabel = new JLabel("CONTROLS");
        controlsLabel.setFont(FontManager.get("oxygene", 50));
        controlsLabel.setForeground(new Color(49, 241, 137));

        StringBuilder controlsSb = new StringBuilder();
        controlsSb.append("MOVE UP-RIGHT: UP-ARROW\n");
        controlsSb.append("MOVE UP-LEFT: LEFT-ARROW\n");
        controlsSb.append("MOVE DOWN-LEFT: DOWN-ARROW\n");
        controlsSb.append("MOVE DOWN-RIGHT: RIGHT-ARROW\n");
        controlsSb.append("MOVE VERTICALLY UP: W\n");
        controlsSb.append("MOVE VERTICALLY DOWN: S\n");

        controlsTextArea = new JTextArea(controlsSb.toString());
        controlsTextArea.setFont(FontManager.get("oxygene", 30));
        controlsTextArea.setSize(1028, 1028);
        controlsTextArea.setForeground(new Color(33, 229, 174));
        controlsTextArea.setOpaque(false);
        controlsTextArea.setLineWrap(true);

        backButton = new GenericButton("BACK");
        backButton.addActionListener(e -> gameWindow.goMenu());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 0; add(titleLabel, gbc);
        gbc.gridy++; add(gameplayLabel, gbc);
        gbc.gridy++; add(gameplayTextArea, gbc);
        gbc.gridy++; add(controlsLabel, gbc);
        gbc.gridy++; add(controlsTextArea, gbc);
        gbc.gridy++; add(backButton, gbc);
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
