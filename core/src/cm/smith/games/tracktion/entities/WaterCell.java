package cm.smith.games.tracktion.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

/**
 * Created by anthony on 2016-12-06.
 */

public class WaterCell extends TiledMapTileLayer.Cell {

    public WaterCell(AssetManager manager) {
        Texture tileTexture = manager.get("waterTile.png", Texture.class);
        TextureRegion region = new TextureRegion(tileTexture);
        setTile(new StaticTiledMapTile(region));
    }

}
