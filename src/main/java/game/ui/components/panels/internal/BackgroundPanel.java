package game.ui.components.panels.internal;

import game.rendering.textures.ScreenEffects;
import game.ui.AppContext;

import java.awt.*;

public class BackgroundPanel extends RenderPanel {
    public void renderVignette(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(
                ScreenEffects.VIGNETTE_TEXTURE.getImage(),
                0,
                0,
                getWidth(),
                getHeight(),
                null
        );
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;
        AppContext.getBackground().addToRenderer(renderer);
        this.renderer.render(g2);
        renderVignette(g2);
    }
}
