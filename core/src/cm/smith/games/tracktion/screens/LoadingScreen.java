package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;

import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-10-28.
 */

public class LoadingScreen extends BaseScreen {

    public LoadingScreen(final MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        loadTextures();
        loadFonts();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if(game.assetManager != null && game.assetManager.update()) {
            // we are done loading, let's move to another screen!
            this.game.setScreen(new TitleScreen(this.game));
        }

        // display loading information
        float progress = game.assetManager.getProgress();
        this.game.uiBatch.begin();
        this.game.font.draw(this.game.uiBatch, "Loading: " + (int)(progress*100) + "%", ((Gdx.graphics.getWidth() / SCALE_X) / 2) - 50, ((Gdx.graphics.getHeight() / SCALE_Y) / 2) + 5);
        this.game.uiBatch.end();
    }

    private void loadTextures() {
        game.assetManager.load("uislider.png", Texture.class);
        game.assetManager.load("gamecontrols.png", Texture.class);
        game.assetManager.load("vehicle.png", Texture.class);
        game.assetManager.load("vehicle-explosion.png", Texture.class);
        game.assetManager.load("waterTile.png", Texture.class);
        game.assetManager.load("trackTile.png", Texture.class);
    }

    private void loadFonts() {
        // Setup the font loader
        FileHandleResolver resolver = new InternalFileHandleResolver();
        FreeTypeFontGenerator.setMaxTextureSize(2048);
        game.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter30 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter30.fontFileName = "Roboto-Light.ttf";
        parameter30.fontParameters.size = Math.round(30 * (BaseScreen.SCALE_X));
        parameter30.fontParameters.color = Color.WHITE;
        game.assetManager.load("font30.ttf", BitmapFont.class, parameter30);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter40 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter40.fontFileName = "Roboto-Light.ttf";
        parameter40.fontParameters.size = Math.round(40 * (BaseScreen.SCALE_X));
        parameter40.fontParameters.color = Color.WHITE;
        game.assetManager.load("font40.ttf", BitmapFont.class, parameter40);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter75 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter75.fontFileName = "Roboto-Light.ttf";
        parameter75.fontParameters.size = Math.round(75 * (BaseScreen.SCALE_X));
        parameter75.fontParameters.color = Color.WHITE;
        game.assetManager.load("font75.ttf", BitmapFont.class, parameter75);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter120 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter120.fontFileName = "Roboto-Light.ttf";
        parameter120.fontParameters.size = Math.round(120 * (BaseScreen.SCALE_X));
        parameter120.fontParameters.color = Color.WHITE;
        game.assetManager.load("font120.ttf", BitmapFont.class, parameter120);
    }
}
