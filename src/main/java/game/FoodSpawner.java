package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FoodSpawner {
    private final Grid grid;
    private final Random rng;

    public FoodSpawner(Grid grid, Random rng) {
        this.grid = grid;
        this.rng = rng;
    }

    public Vec3 spawn(List<Vec3> occupied) {
        int randomAttempts = 100;
        while (randomAttempts-- > 0) {
            int x = rng.nextInt(grid.getLength()) - grid.getOffset();
            int y = rng.nextInt(grid.getLength()) - grid.getOffset();
            int z = rng.nextInt(grid.getLength()) - grid.getOffset();

            Vec3 point = new Vec3(x, y, z);
            if (!occupied.contains(point)) return point;
        }

        List<Vec3> freePoints = new ArrayList<>();
        for (int x = -grid.getOffset(); x <= grid.getOffset(); x++) {
            for (int y = -grid.getOffset(); y <= grid.getOffset(); y++) {
                for (int z = -grid.getOffset(); z <= grid.getOffset(); z++) {
                    Vec3 point = new Vec3(x, y, z);
                    if(!occupied.contains(point)) freePoints.add(point);
                }
            }
        }

        if (freePoints.isEmpty()) return null;
        return freePoints.get(rng.nextInt(freePoints.size()));
    }
}
