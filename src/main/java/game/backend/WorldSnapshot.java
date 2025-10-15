package game.backend;

import game.utils.Vec3;

import java.util.List;

public record WorldSnapshot(
        int gridSize,
        List<Vec3> snake,
        Vec3 food,
        Direction direction,
        int score,
        boolean gameOver,
        List<Vec3> lineOfSight
) {

}