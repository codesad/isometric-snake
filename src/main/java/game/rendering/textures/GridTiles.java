package game.rendering.textures;

import game.rendering.textures.internal.BakedTile;

public class GridTiles {
    public static final BakedTile CURRENT_LAYER_TILE = new BakedTile("/assets/snake", "current_cube.png");
    public static final BakedTile FOOD_CUBE = new BakedTile("/assets/snake", "food_cube.png");
    public static final BakedTile FOOD_CUBE_SAME_LAYER = new BakedTile("/assets/snake", "food_cube_same_layer.png");
    public static final BakedTile SNAKE_SEGMENT = new BakedTile("/assets/snake", "snake_segment.png");
}
