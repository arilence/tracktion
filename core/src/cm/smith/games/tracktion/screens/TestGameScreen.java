package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.UIImageButton;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    private GameController gameController;
    private Vector3 touchPoint;

    private boolean leftDown = false;
    private boolean rightDown = false;
    private boolean accelerateDown = false;

    public TestGameScreen(MainGame game, GameController.ROLE role) {
        super(game);
        gameController = new GameController();
        gameController.setRole(role);

        game.playServices.setGameManager(gameController);
        touchPoint = new Vector3();

        // TODO: setup vehicle
        // TODO: setup map world
        // TODO: setup on-screen hud
        //      if driver: (speedometer, left/right control buttons, pause/leave button)
        //      if builder: (next piece, map grid to place on)

        Texture texture = game.assetManager.get("gamecontrols.png", Texture.class);
        TextureRegion leftTexture = new TextureRegion(texture, 0, 0, 64, 128);
        TextureRegion rightTexture = new TextureRegion(texture, 0, 0, 64, 128);
        TextureRegion accelerateTexture = new TextureRegion(texture, 0, 0, 128, 64);
        rightTexture.flip(true, false);
        UIImageButton turnLeftButton = UIImageButton.makeButton(this.game, leftTexture);
        UIImageButton turnRightButton = UIImageButton.makeButton(this.game, rightTexture);
        UIImageButton accelerateButton = UIImageButton.makeButton(this.game, accelerateTexture);

        turnLeftButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        turnRightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        accelerateButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                accelerateDown = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                accelerateDown = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });

        Table turningTable = new Table();
        turningTable.setFillParent(true);
        turningTable.add(turnLeftButton).padLeft(80 * SCALE_X).padBottom(70 * SCALE_Y);
        turningTable.add(turnRightButton).padLeft(100 * SCALE_X).padBottom(70 * SCALE_Y);

        Table gearbox = new Table();
        gearbox.setFillParent(true);
        gearbox.add(accelerateButton.padRight(80 * SCALE_X).padBottom(70 * SCALE_Y));

        Stack stack = new Stack();
        stack.setFillParent(true);

        stack.add(turningTable.bottom().left());
        stack.add(gearbox.bottom().right());

        uiStage.addActor(stack);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

//        if (Gdx.input.isTouched()) {
//            this.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
//
//            float midpoint = MainGame.VIEW_WIDTH / 2;
//            if (touchPoint.x < midpoint) {
//                // TODO: tell vehicle to turn left
//                Gdx.app.log("VEHICLE", "LEFT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
//            }
//            if (touchPoint.x >= midpoint){
//                // TODO: tell vehicle to turn right
//                Gdx.app.log("VEHICLE", "RIGHT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
//            }
//        }

        if (leftDown) {
            Gdx.app.log("VEHICLE", "LEFT");
        }
        if (rightDown) {
            Gdx.app.log("VEHICLE", "RIGHT");
        }
        if (accelerateDown) {
            Gdx.app.log("VEHICLE", "ACCELERATE");
        }

    }

    private void setupControlsUI() {

    }
}
