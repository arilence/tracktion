package cm.smith.games.tracktion.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.components.TextureComponent;
import cm.smith.games.tracktion.components.TransformComponent;

/**
 * Created by anthony on 2016-12-08.
 */

public class TrackSegment extends Entity {

    public Texture texture;
    public TextureRegion textureRegion;

    public TextureComponent textureComponent;
    public TransformComponent transformComponent;

    public TrackSegment(MainGame game, float x, float y) {
        texture = game.assetManager.get("trackTile.png", Texture.class);
        textureRegion = new TextureRegion(texture);

        textureComponent = new TextureComponent();
        textureComponent.region = textureRegion;

        transformComponent = new TransformComponent();
        transformComponent.pos.x = x;
        transformComponent.pos.y = y;
        transformComponent.pos.z = -1;

        add(textureComponent);
        add(transformComponent);
    }
}
