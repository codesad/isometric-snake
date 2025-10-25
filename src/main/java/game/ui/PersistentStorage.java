package game.ui;

import game.rendering.Background;

import java.util.prefs.Preferences;

public class PersistentStorage {
    private static final Preferences prefs = Preferences.userRoot().node("isometric-snake");

    private int gridLength;
    private int highScore;
    private int volume;

    public PersistentStorage() {
        this.gridLength = prefs.getInt("gridLength", 9);
        this.highScore = prefs.getInt("highScore", 0);
        this.volume = prefs.getInt("volume", 100);
    }

    public void save() {
        prefs.putInt("gridLength", gridLength);
        prefs.putInt("highScore", highScore);
        prefs.putInt("volume", volume);
    }

    public int getGridLength() {
        return gridLength;
    }

    public void setGridLength(int gridLength) {
        this.gridLength = gridLength;
        AppContext.setBackground(new Background(gridOffset(), AppContext.RNG));
        save();
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

    public int getVolume() {
        return this.volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
        save();
    }
}
