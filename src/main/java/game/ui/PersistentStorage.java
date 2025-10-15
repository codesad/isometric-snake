package game.ui;

import game.rendering.Background;

import java.util.prefs.Preferences;

public class PersistentStorage {
    private static final Preferences prefs = Preferences.userRoot().node("isometric-snake");

    private int gridLength;
    private int highScore;

    public PersistentStorage() {
        this.gridLength = prefs.getInt("gridLength", 9);
        this.highScore = prefs.getInt("highScore", 0);
    }

    public void save() {
        prefs.putInt("gridLength", gridLength);
        prefs.putInt("highScore", highScore);
    }

    public int getGridLength() {
        return gridLength;
    }

    public void setGridLength(int gridLength) {
        this.gridLength = gridLength;
        AppContext.setBackground(new Background(gridOffset(), AppContext.RNG));
    }

    public int gridOffset() {
        return getGridLength() / 2;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        save();
    }
}
