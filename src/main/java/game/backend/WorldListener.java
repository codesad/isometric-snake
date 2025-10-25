package game.backend;

public interface WorldListener {
    default void onGameOver() {}
    default void onFoodEaten() {}
}
