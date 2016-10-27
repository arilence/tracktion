package cm.smith.games.tracktion.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-09-18.
 */
public abstract class BaseScreen implements Screen {

    /** Horizontalå scaling factor. */
    public static final float SCALE_X =
            (float)Gdx.graphics.getWidth()/MainGame.VIEW_WIDTH;

    /** Vertical scaling factor. */
    public static final float SCALE_Y =
            (float)Gdx.graphics.getHeight()/MainGame.VIEW_HEIGHT;

    final MainGame game;
    OrthographicCamera camera;

    Stage uiStage;
    Engine engine;

    public BaseScreen(MainGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT);

        uiStage = new Stage(new ScreenViewport());

        engine = new Engine();
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        engine.update(delta);

        uiStage.act();
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        uiStage.dispose();
    }

    public void playServicesLogin(){
        if (!game.playServices.isSignedIn()) {
            game.playServices.signIn();
        }
    }

    public void playServicesLogout() {
        if (game.playServices.isSignedIn()) {
            game.playServices.signOut();
        }
    }
}
