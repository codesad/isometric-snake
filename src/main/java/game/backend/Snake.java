package game.backend;

import game.utils.Vec3;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class Snake {
    private final Deque<Vec3> body = new ArrayDeque<>();

    public Snake(int initialSize) {
        if (initialSize < 2) {
            throw new IllegalArgumentException("Snake initialSize should be >= 2");
        }
        addInitialSegments(initialSize);
    }

    public void addInitialSegments(int initialSize) {
        for (int scale = 0; scale < initialSize; scale++) {
            body.addFirst(Grid.fromCenter(Direction.POS_Z, scale));
        }
    }

    public Vec3 head() {
        return body.getFirst();
    }

    public boolean contains(Vec3 point) {
        return body.contains(point);
    }

    public int length() {
        return body.size();
    }

    public List<Vec3> segments() {
        return body.parallelStream().toList();
    }

    public void moveTo(Vec3 next, boolean grow) {
        body.addFirst(next);
        if (!grow) body.removeLast();
    }
}
