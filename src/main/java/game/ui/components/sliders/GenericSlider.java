package game.ui.components.sliders;

import game.utils.ColourUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class GenericSlider extends JSlider {

    private static final Color TRACK_COLOR = new Color(10, 49, 46, 140);
    private static final Color FILL_COLOR = new Color(38, 154, 204);
    private static final Color THUMB_COLOR = new Color(69, 255, 100);

    public GenericSlider(int min, int max, int value) {
        super(min, max, value);
        setOpaque(false);
        setUI(new CustomUI(this));
        setForeground(FILL_COLOR);
    }


    private static class CustomUI extends BasicSliderUI {
        public CustomUI(JSlider b) { super(b); }

        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int cy = trackRect.y + (trackRect.height / 2);
            int trackHeight = 10;
            int arc = 12;

            g2.setColor(TRACK_COLOR);
            g2.fillRoundRect(trackRect.x, cy - trackHeight / 2, trackRect.width, trackHeight, arc, arc);

            int fillWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
            g2.setColor(FILL_COLOR);
            g2.fillRoundRect(trackRect.x, cy - trackHeight / 2, fillWidth, trackHeight, arc, arc);
        }

        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int radius = 20;
            int x = thumbRect.x + thumbRect.width / 2 - radius / 2;
            int y = thumbRect.y + thumbRect.height / 2 - radius / 2;

            Color thumb = THUMB_COLOR;
            if (slider.getMousePosition() != null && thumbRect.contains(slider.getMousePosition()))
                thumb = ColourUtils.brighten(THUMB_COLOR, 1.3);

            g2.setColor(thumb);
            g2.fillOval(x, y, radius, radius);
            g2.setColor(ColourUtils.brighten(thumb, 1.5));
            g2.drawOval(x, y, radius, radius);
        }

        @Override
        public void paintFocus(Graphics g) {
            // no outline
        }
    }
}
