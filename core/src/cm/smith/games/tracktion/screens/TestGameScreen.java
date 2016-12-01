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
import cm.smith.games.tracktion.ui.Hud;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.UIImageButton;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    private GameController gameController;
    private Vector3 touchPoint;
    private Hud hud;

    public TestGameScreen(MainGame game, GameController.ROLE role) {
        super(game);
        gameController = new GameController();
        gameController.setRole(role);

        game.playServices.setGameManager(gameController);
        touchPoint = new Vector3();

        // TODO: setup vehicle
        // TODO: setup map world

        hud = new Hud(this.game);
        hud.setupBaseHud();
        if (gameController.getRole() == GameController.ROLE.DRIVER) {
            hud.setupDriverHud();
        }
        else if (gameController.getRole() == GameController.ROLE.BUILDER) {
            hud.setupBuilderHud();
        }
        uiStage.addActor(hud);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        switch(gameController.getState()) {
            case PRE_GAME:
                break;

            case PLAYING:
                updatePlaying();
                break;

            case DEAD:
                break;

            case GAME_OVER:
                break;
        }

        if (Gdx.input.isTouched()) {
            this.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

//            float midpoint = MainGame.VIEW_WIDTH / 2;
//            if (touchPoint.x < midpoint) {
//                // TODO: tell vehicle to turn left
//                Gdx.app.log("VEHICLE", "LEFT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
//            }
//            if (touchPoint.x >= midpoint){
//                // TODO: tell vehicle to turn right
//                Gdx.app.log("VEHICLE", "RIGHT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
//            }
        }

    }

    public void updatePlaying() {
        if (hud.isLeftDown) {
            Gdx.app.log("VEHICLE", "LEFT");
        }
        if (hud.isRightDown) {
            Gdx.app.log("VEHICLE", "RIGHT");
        }
        if (hud.isAccelerateDown) {
            Gdx.app.log("VEHICLE", "ACCELERATE");
        }
    }
}
