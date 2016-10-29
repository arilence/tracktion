package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.ui.TextLabel;

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
        this.game.batch.begin();
        this.game.font.draw(this.game.batch, "Loading: " + (int)(progress*100) + "%", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
        this.game.batch.end();
    }

    private void loadTextures() {
        game.assetManager.load("badlogic.jpg", Texture.class);
    }

    private void loadFonts() {
        // Setup the font loader
        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter60 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter60.fontFileName = "Roboto-Thin.ttf";
        parameter60.fontParameters.size = 60;
        parameter60.fontParameters.color = Color.WHITE;
        game.assetManager.load("font60.ttf", BitmapFont.class, parameter60);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter80 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter80.fontFileName = "Roboto-Thin.ttf";
        parameter80.fontParameters.size = 80;
        parameter80.fontParameters.color = Color.WHITE;
        game.assetManager.load("font80.ttf", BitmapFont.class, parameter80);

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter150 = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter150.fontFileName = "Roboto-Thin.ttf";
        parameter150.fontParameters.size = 150;
        parameter150.fontParameters.color = Color.WHITE;
        game.assetManager.load("font150.ttf", BitmapFont.class, parameter150);
    }
}
