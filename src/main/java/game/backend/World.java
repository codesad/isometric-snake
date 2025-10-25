package game.backend;

import game.utils.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    private final WorldData worldData;
    private final Random rng;
    private final FoodSpawner foodSpawner;

    private Snake snake;
    private Direction direction;
    private Vec3 food;
    private int score;
    private boolean gameOver;

    private final List<WorldListener> listeners = new ArrayList<>();

    public World(WorldData worldData) {
        this(worldData, new Random());
    }

    public World(WorldData worldData, Random rng) {
        this.worldData = worldData;
        this.rng = rng;
        this.foodSpawner = new FoodSpawner(worldData.getGrid(), rng);
        this.reset();
    }

    public void addListener(WorldListener listener) {
        listeners.add(listener);
    }

    public void removeListener(WorldListener listener) {
        listeners.remove(listener);
    }

    public void reset() {
        this.snake = new Snake(worldData.getStartLength());
        this.food = foodSpawner.spawn(snake.segments());
        this.direction = Direction.POS_Z;
        this.score = 0;
        this.gameOver = false;
    }

    public void step() {
        if (gameOver) return;
        Vec3 next = snake.head().add(direction);

        if (!worldData.getGrid().inBounds(next)) {
            next = worldData.getGrid().teleport(next);
        }

        if (!worldData.getGrid().inBounds(next) || snake.contains(next)) {
            gameOver = true;
            for (var l : listeners) l.onGameOver();
            return;
        }

        boolean grow = next.equals(food);
        snake.moveTo(next, grow);

        if (grow) {
            score++;
            for (var l : listeners) l.onFoodEaten();
            food = foodSpawner.spawn(snake.segments());
        }
    }

    public void setDirection(Direction newDirection) {
        if (!newDirection.isOppositeOf(this.direction)) {
            this.direction = newDirection;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return this.score;
    }

    public WorldData getWorldData() {
        return this.worldData;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public Vec3 getFoodPos() {
        return this.food;
    }

    public Random getRng() {
        return this.rng;
    }

    public List<Vec3> getLineOfSight() {
        List<Vec3> out = new ArrayList<>();
        Vec3 point = this.snake.head().add(direction);
        while (this.worldData.getGrid().inBounds(point)) {
            if (snake.contains(point)) break;
            out.add(point);
            if (point.equals(food)) break;
            point = point.add(direction);
        }
        return out;
    }

    public WorldSnapshot snapshot() {
        return new WorldSnapshot(
                this.worldData.getGrid().getOffset(),
                List.copyOf(this.snake.segments()),
                food,
                direction,
                score,
                gameOver,
                getLineOfSight()
        );
    }
}
