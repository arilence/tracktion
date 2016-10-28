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
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.ui.TextLabel;

/**
 * Created by anthony on 2016-10-28.
 */

public class LoadingScreen extends BaseScreen {

    TextLabel loadingProgress;

    public LoadingScreen(final MainGame game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        Table uiTable = new Table();
        uiTable.setFillParent(true);

        loadingProgress = TextLabel.makeLabel("Loading: ", 80);

        uiTable.add(loadingProgress);

        uiStage.addActor(uiTable);

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
        loadingProgress.setText("Loading: " + (int)(progress*100) + "%");
    }

    private void loadTextures() {
        game.assetManager.load("badlogic.jpg", Texture.class);
    }

    private void loadFonts() {
        // Setup the font loader
        FileHandleResolver resolver = new InternalFileHandleResolver();
        game.assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        game.assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter parameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        parameter.fontFileName = "Roboto-Thin.ttf";
        parameter.fontParameters.size = 50;
        parameter.fontParameters.color = Color.WHITE;
        game.assetManager.load("font50.ttf", BitmapFont.class, parameter);
    }
}
