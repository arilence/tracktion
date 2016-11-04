package cm.smith.games.tracktion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cm.smith.games.tracktion.screens.LoadingScreen;
import cm.smith.games.tracktion.storage.Settings;

public class MainGame extends Game {

    public static final String GAME_TITLE = "Tracktion";
	public static final int VIEW_WIDTH = 1280;
	public static final int VIEW_HEIGHT = 720;

    public SpriteBatch batch;
    public BitmapFont font;
    public PlayServices playServices;
    public AssetManager assetManager;

    public MainGame(PlayServices playServices) {
        this.playServices = playServices;
    }

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        assetManager = new AssetManager();

        setScreen(new LoadingScreen(this));
	}

    @Override
    public void render() {
        super.render(); //important!

        batch.begin();
        font.draw(batch, "FPS: " + (1 / Gdx.graphics.getDeltaTime()), 20, 30);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

