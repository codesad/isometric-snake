package game.rendering.textures;

import game.rendering.textures.internal.BakedTile;

import java.util.List;

public class BackgroundTiles {
    public static final BakedTile GRASS_1_TILE = new BakedTile("tile_grass_1.png");
    public static final BakedTile WATER_TILE = new BakedTile("tile_water.png");
    public static final BakedTile GRASS_2_TILE = new BakedTile("tile_grass_2.png");
    public static final BakedTile GRASS_3_TILE = new BakedTile("tile_grass_3.png");

    public static final BakedTile FLOWER_1_TILE = new BakedTile("tile_flower_1.png");
    public static final BakedTile FLOWER_2_TILE = new BakedTile("tile_flower_2.png");
    public static final BakedTile FLOWER_3_TILE = new BakedTile("tile_flower_3.png");
    public static final BakedTile LOG_1_TILE = new BakedTile("tile_log_1.png");
    public static final BakedTile LOG_2_TILE = new BakedTile("tile_log_2.png");
    public static final BakedTile BUSH_2_TILE = new BakedTile("tile_bush_2.png");

    public static final List<BakedTile> FLOWERS = List.of(
            FLOWER_1_TILE,
            FLOWER_2_TILE,
            FLOWER_3_TILE
    );


    public static final List<BakedTile> LOGS = List.of(
            LOG_1_TILE,
            LOG_2_TILE,
            BUSH_2_TILE
    );

    public static final List<BakedTile> GRASS = List.of(
            GRASS_1_TILE,
            GRASS_2_TILE,
            GRASS_3_TILE
    );
}
