package game;

public class WorldData {
    private final Grid grid;   // coordinates are [-gridSize, gridSize]
    private final int startLength;

    public WorldData(int gridOffset, int startLength) {
        if (gridOffset < 1) throw new IllegalArgumentException("gridSize >= 1");
        if (startLength < 2) throw new IllegalArgumentException("startLen >= 2");
        this.grid = new Grid(gridOffset);
        int capacity = (int) Math.pow(this.grid.getLength(), 3);
        if (startLength >= capacity) throw new IllegalArgumentException("startLen must be < total cells");
        this.startLength = startLength;
    }

    public Grid getGrid() {
        return grid;
    }

    public int getStartLength() {
        return this.startLength;
    }
}