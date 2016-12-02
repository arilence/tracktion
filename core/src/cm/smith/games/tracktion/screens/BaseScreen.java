package cm.smith.games.tracktion.screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import cm.smith.games.tracktion.Colors;
import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-09-18.
 */
public abstract class BaseScreen implements Screen {

    /** Horizontal scaling factor. */
    public static final float SCALE_X =
            (float)Gdx.graphics.getWidth()/MainGame.VIEW_WIDTH;

    /** Vertical scaling factor. */
    public static final float SCALE_Y =
            (float)Gdx.graphics.getHeight()/MainGame.VIEW_HEIGHT;

    final MainGame game;
    OrthographicCamera camera;

    Stage uiStage;
    Engine engine;
    TweenManager tweenManager;

    // box-2d physics stuff
    World physicsWorld;
    Box2DDebugRenderer debugPhysicsRenderer;


    public BaseScreen(MainGame game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, MainGame.VIEW_WIDTH, MainGame.VIEW_HEIGHT);

        engine = new Engine();
        uiStage = new Stage(new ScreenViewport());
        tweenManager = new TweenManager();

        // setup physics engine
        physicsWorld = new World(new Vector2(0, 0), true);
        debugPhysicsRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(uiStage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.GL_CLEAR_COLOR[0], Colors.GL_CLEAR_COLOR[1], Colors.GL_CLEAR_COLOR[2], Colors.GL_CLEAR_COLOR[3]);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        engine.update(delta);
        uiStage.act();
        uiStage.draw();
        tweenManager.update(delta);

        physicsWorld.step(1/60f, 6, 2);
        debugPhysicsRenderer.render(physicsWorld, camera.combined);
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

    public void transitionOut(final Timeline timeline, final BaseScreen newScreen) {
        timeline.setCallback(new TweenCallback() {
                    @Override
                    public void onEvent(int type, BaseTween<?> source) {
                        game.setScreen(newScreen);
                    }
                })
                .start(tweenManager);
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
