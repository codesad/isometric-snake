package game.ui.components.panels;

import game.rendering.textures.FontManager;
import game.ui.AppContext;
import game.ui.GameWindow;
import game.ui.components.buttons.GenericButton;
import game.ui.components.panels.internal.BackgroundPanel;
import game.ui.components.sliders.GenericSlider;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class SettingsPanel extends BackgroundPanel {
    private final JLabel titleLabel;
    private final JLabel volumeLabel;
    private final JLabel gridLabel;

    private final JSlider volumeSlider;
    private final JSlider gridSlider;
    private final GenericButton backButton;

    public SettingsPanel(GameWindow gameWindow) {
        setOpaque(false);
        setLayout(new GridBagLayout());

        titleLabel = new JLabel("SETTINGS");
        titleLabel.setFont(FontManager.get("oxygene", 100));
        titleLabel.setForeground(new Color(69, 255, 100));

        volumeLabel = new JLabel("VOLUME");
        volumeLabel.setFont(FontManager.get("oxygene", 40));
        volumeLabel.setForeground(new Color(33, 229, 174));

        volumeSlider = new GenericSlider(0, 100, AppContext.persistentStorage().getVolume());
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPreferredSize(new Dimension(400, 60));
        volumeSlider.addChangeListener(e -> {
            int vol = volumeSlider.getValue();
            AppContext.persistentStorage().setVolume(vol);
        });

        Font labelFont = FontManager.get("oxygene", 24);
        Color labelColor = new Color(33, 229, 174);

        Hashtable<Integer, JLabel> volLabels = getVolLabels(labelFont, labelColor);

        volumeSlider.setLabelTable(volLabels);
        volumeSlider.setPaintLabels(true);

        gridLabel = new JLabel("GRID SIZE");
        gridLabel.setFont(FontManager.get("oxygene", 40));
        gridLabel.setForeground(new Color(33, 229, 174));

        gridSlider = new GenericSlider(5, 11, AppContext.persistentStorage().getGridLength());
        gridSlider.setMajorTickSpacing(2);
        gridSlider.setPaintTicks(true);
        gridSlider.setSnapToTicks(true);
        gridSlider.setPreferredSize(new Dimension(400, 60));

        Hashtable<Integer, JLabel> labels = getGridLabels(labelFont, labelColor);

        gridSlider.setLabelTable(labels);
        gridSlider.setPaintLabels(true);

        gridSlider.addChangeListener(e -> {
            int size = gridSlider.getValue();
            if (AppContext.persistentStorage().getGridLength() != size && size % 2 == 1) {
                System.out.println(AppContext.persistentStorage().getGridLength());
                System.out.println(size);
                AppContext.persistentStorage().setGridLength(size);
            }
        });

        backButton = new GenericButton("BACK");
        backButton.addActionListener(e -> gameWindow.goMenu());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridy = 0; add(titleLabel, gbc);
        gbc.gridy++; add(volumeLabel, gbc);
        gbc.gridy++; add(volumeSlider, gbc);
        gbc.gridy++; add(gridLabel, gbc);
        gbc.gridy++; add(gridSlider, gbc);
        gbc.gridy++; add(backButton, gbc);
    }

    private Hashtable<Integer, JLabel> getVolLabels(Font labelFont, Color labelColor) {
        Hashtable<Integer, JLabel> volLabels = new Hashtable<>();

        for (int i = 0; i <= 100; i += 20) {
            JLabel lbl = new JLabel(i + "%");
            lbl.setFont(labelFont);
            lbl.setForeground(labelColor);
            volLabels.put(i, lbl);
        }

        return volLabels;
    }

    private static Hashtable<Integer, JLabel> getGridLabels(Font labelFont, Color labelColor) {
        Hashtable<Integer, JLabel> labels = new Hashtable<>();

        JLabel l5 = new JLabel("5X5");
        l5.setFont(labelFont);
        l5.setForeground(labelColor);

        JLabel l7 = new JLabel("7X7");
        l7.setFont(labelFont);
        l7.setForeground(labelColor);

        JLabel l9 = new JLabel("9X9");
        l9.setFont(labelFont);
        l9.setForeground(labelColor);

        JLabel l11 = new JLabel("11X11");
        l11.setFont(labelFont);
        l11.setForeground(labelColor);

        labels.put(5, l5);
        labels.put(7, l7);
        labels.put(9, l9);
        labels.put(11, l11);
        return labels;
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