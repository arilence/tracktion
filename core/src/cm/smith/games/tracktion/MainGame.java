package cm.smith.games.tracktion;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import cm.smith.games.tracktion.screens.TitleScreen;

public class MainGame extends Game {

	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 720;

    public SpriteBatch batch;
    public BitmapFont font;

	@Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();

        setScreen(new TitleScreen(this));
	}

    @Override
    public void render() {
        super.render(); //important!
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

}

