package cm.smith.games.tracktion.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.components.BoundsComponent;
import cm.smith.games.tracktion.components.TextureComponent;
import cm.smith.games.tracktion.components.TrackComponent;
import cm.smith.games.tracktion.components.TransformComponent;
import cm.smith.games.tracktion.screens.BaseScreen;

/**
 * Created by anthony on 2016-12-08.
 */

public class TrackSegment extends Entity {

    public Texture texture;
    public TextureRegion textureRegion;

    public TextureComponent textureComponent;
    public TransformComponent transformComponent;
    public TrackComponent trackComponent;
    public BoundsComponent boundsComponent;

    public TrackSegment(MainGame game, float x, float y) {
        texture = game.assetManager.get("trackTile.png", Texture.class);
        textureRegion = new TextureRegion(texture);

        textureComponent = new TextureComponent();
        textureComponent.region = textureRegion;

        transformComponent = new TransformComponent();
        transformComponent.pos.x = x;
        transformComponent.pos.y = y;
        transformComponent.pos.z = -1;

        trackComponent = new TrackComponent();

        boundsComponent = new BoundsComponent();
        float width = textureRegion.getRegionWidth() / BaseScreen.PIXELS_PER_METER;
        float height = textureRegion.getRegionHeight() / BaseScreen.PIXELS_PER_METER;
        boundsComponent.bounds = new Polygon(new float[]{0,0,width,0,width,height,0,height});
        boundsComponent.bounds.setOrigin(width/2, height/2);
        boundsComponent.bounds.setPosition(x, y);

        add(textureComponent);
        add(transformComponent);
        add(trackComponent);
        add(boundsComponent);
    }
}
