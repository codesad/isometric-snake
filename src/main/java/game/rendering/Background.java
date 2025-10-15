package game.rendering;

import game.utils.RandomUtils;
import game.utils.Vec2;
import game.utils.Vec3;
import game.rendering.textures.internal.BakedTile;
import game.rendering.textures.BackgroundTiles;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Background {
    private final HashMap<Vec2, TileData> floorMap = new HashMap<>();
    private final HashMap<Vec2, TileData> flowerMap = new HashMap<>();

    private final int viewSize;
    private final int groundY;
    private final int gridSize;

    private final Random rng;

    private record TileData(BakedTile tile, Vec3 position) {
    }

    public Background(int gridSize, Random rng) {
        this.viewSize = 100;
        this.gridSize = gridSize;
        this.groundY = gridSize + 1;
        this.rng = rng;

        this.addBackground();
    }

    private void addToMap(Map<Vec2, TileData> map, Vec3 position, BakedTile bakedTile, boolean overwrite) {
        var key = new Vec2(position.x(), position.z());
        var value = new TileData(bakedTile, position);

        if (!overwrite) map.putIfAbsent(key, value);
        else map.put(key, value);
    }

    private BakedTile getRandomGrassTile() {
        double r = rng.nextDouble();
        if (r < 0.3) {
            return BackgroundTiles.GRASS_1_TILE;
        }
        if (r < 0.6) {
            return BackgroundTiles.GRASS_2_TILE;
        }
        return BackgroundTiles.GRASS_3_TILE;
    }

    private BakedTile getRandomFlower() {
        double r = rng.nextDouble();
        if (r < 0.2) return RandomUtils.getRandomFromList(rng, BackgroundTiles.LOGS);
        return RandomUtils.getRandomFromList(rng, BackgroundTiles.FLOWERS);
    }

    private void setGround(Vec3 position) {
        var grassTile = getRandomGrassTile();
        addToMap(floorMap, grassTile.moveTop(position), grassTile, false);
    }

    private void setWater(Vec3 position) {
        var waterTile = BackgroundTiles.WATER_TILE;
        addToMap(floorMap, waterTile.moveTop(position).add(new Vec3(0, 0.3, 0)), waterTile, true);
    }

    private void trySetPond(Vec3 currentVec) {
        if (rng.nextDouble() > 0.01) return;
        int radius = (int)Math.ceil(RandomUtils.randomFrom(rng, 1, 3));

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (Math.abs(x) == radius && Math.abs(z) == radius) continue;
                double distToCenter = Math.sqrt(x * x + z * z);

                double chance = 1 - (distToCenter / radius);
                chance *= RandomUtils.randomFrom(rng, 1.0, 4.0);

                if (distToCenter <= radius && chance > 1.0) {
                    setWater(currentVec.add(new Vec3(x, 0.0, z)));
                }
            }
        }
    }

    private void setFlower(Vec3 position) {
        addToMap(flowerMap, position, getRandomFlower(), true);
    }

    private void trySetFlower(Vec3 currentVec) {
        if (rng.nextDouble() > 0.1) return;
        var pos2d = new Vec2(currentVec.x(), currentVec.z());
        var tileDataBelow = floorMap.get(pos2d);
        if (!BackgroundTiles.GRASS.contains(tileDataBelow.tile())) return;
        setFlower(currentVec);
    }

    private boolean isBelowGame(Vec3 position) {
        return Math.abs(position.x()) <= gridSize
                && Math.abs(position.z()) <= gridSize;
    }

    private void addBackground() {
        for (int x = -viewSize; x <= viewSize; x++) {
            for (int z = -viewSize; z <= viewSize; z++) {
                Vec3 currentGroundVec = new Vec3(x, groundY, z);
                setGround(currentGroundVec);
                if (isBelowGame(currentGroundVec)) continue;
                trySetPond(currentGroundVec);
            }
        }

        for (int x = -viewSize; x <= viewSize; x++) {
            for (int z = -viewSize; z <= viewSize; z++) {
                Vec3 currentFlowerVec = new Vec3(x, groundY-1, z);
                if (isBelowGame(currentFlowerVec)) continue;
                trySetFlower(currentFlowerVec);
            }
        }

    }

    private void addMapToRenderer(Map<Vec2, TileData> map, Renderer renderer) {
        map.forEach((vec2, tileData) -> {
            renderer.addTile(tileData.tile, tileData.position, 0, 0);
        });
    }


    public void addToRenderer(Renderer renderer) {
        addMapToRenderer(flowerMap, renderer);
        addMapToRenderer(floorMap, renderer);
    }
}