package cm.smith.games.tracktion.entities;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-12-06.
 */

public class GameBoard extends TiledMap{

    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 100;
    public static final int WATER_TILE_WIDTH = 128;
    public static final int WATER_TILE_HEIGHT = 128;
    public static final int PLATFORM_TILE_WIDTH = 0;
    public static final int PLATFORM_TILE_HEIGHT = 0;

    private BaseScreen screen;
    public MapLayers boardLayers;
    public TiledMapTileLayer waterLayer;
    public TiledMapTileLayer platformLayer;

    public GameBoard(BaseScreen screen) {
        this.screen = screen;
        boardLayers = getLayers();

        waterLayer = new TiledMapTileLayer(WORLD_WIDTH, WORLD_HEIGHT, WATER_TILE_WIDTH, WATER_TILE_HEIGHT);
        boardLayers.add(waterLayer);

        platformLayer = new TiledMapTileLayer(WORLD_WIDTH, WORLD_HEIGHT, PLATFORM_TILE_WIDTH, PLATFORM_TILE_HEIGHT);
        boardLayers.add(platformLayer);

        initializeWater();
    }

    public void initializeWater() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                waterLayer.setCell(x, y, new WaterCell(screen.game.assetManager));
            }
        }
    }

}
