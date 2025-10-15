package game.ui;

import game.rendering.Background;

import java.util.Random;

public class AppContext {
    static final String WINDOW_NAME = "Isometric Snake";
    static final Random RNG = new Random();
    private static final PersistentStorage PERSISTENT_STORAGE = new PersistentStorage();
    private static Background background = new Background(PERSISTENT_STORAGE.gridOffset(), RNG);

    public static PersistentStorage persistentStorage() {
        return PERSISTENT_STORAGE;
    }

    public static Background getBackground() {
        return background;
    }

    public static void setBackground(Background background) {
        AppContext.background = background;
    }

    public static double getSubCubeLength() {
        return 1.0 / PERSISTENT_STORAGE.getGridLength();
    }

    public static void resetBackground() {
        background = new Background(PERSISTENT_STORAGE.gridOffset(), RNG);
    }
}
