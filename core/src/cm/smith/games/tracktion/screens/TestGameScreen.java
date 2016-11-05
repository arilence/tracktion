package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.ui.LabelButton;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    private GameController gameController;
    private Vector3 touchPoint;

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
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (Gdx.input.isTouched()) {
            this.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            float midpoint = MainGame.VIEW_WIDTH / 2;
            if (touchPoint.x < midpoint) {
                // TODO: tell vehicle to turn left
                Gdx.app.log("VEHICLE", "LEFT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
            }
            if (touchPoint.x >= midpoint){
                // TODO: tell vehicle to turn right
                Gdx.app.log("VEHICLE", "RIGHT, " + Float.toString(midpoint) + ", " + Float.toString(touchPoint.x));
            }
        }

    }

    private void setupControlsUI() {

    }
}
