package game.ui;

import game.rendering.Background;

import java.util.prefs.Preferences;

public class PersistentStorage {
    private static final Preferences prefs = Preferences.userRoot().node("isometric-snake");

    private int gridLength;
    private int volume;

    private int[] highScores;

    public PersistentStorage() {
        this.gridLength = prefs.getInt("gridLength", 9);
        this.highScores = new int[4];
        for (int i = 0; i < 4; i++) {
            this.highScores[i] = prefs.getInt("highScore" + i, 0);
        }
        this.volume = prefs.getInt("volume", 100);
    }

    public void save() {
        prefs.putInt("gridLength", gridLength);
        switch (gridLength) {
            case 5 -> prefs.putInt("highScore0", highScores[0]);
            case 7 -> prefs.putInt("highScore1", highScores[1]);
            case 9 -> prefs.putInt("highScore2", highScores[2]);
            case 11 -> prefs.putInt("highScore3", highScores[3]);
        }
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
        switch (this.gridLength) {
            case 5 -> {return this.highScores[0];}
            case 7 -> {return this.highScores[1];}
            case 9 -> {return this.highScores[2];}
            case 11 -> {return this.highScores[3];}
        }
        return 0;
    }

    public void setHighScore(int highScore) {
        switch (gridLength) {
            case 5 -> highScores[0] = highScore;
            case 7 -> highScores[1] = highScore;
            case 9 -> highScores[2] = highScore;
            case 11 -> highScores[3] = highScore;
        }
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
