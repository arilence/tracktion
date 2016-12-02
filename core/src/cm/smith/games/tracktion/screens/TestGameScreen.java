package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.entities.Vehicle;
import cm.smith.games.tracktion.ui.Hud;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.UIImageButton;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    private GameController gameController;
    private Vector3 touchPoint;
    private Vehicle vehicle;
    private Hud hud;

    public TestGameScreen(MainGame game, GameController.ROLE role) {
        super(game);
        gameController = new GameController();
        gameController.setRole(role);

        game.playServices.setGameManager(gameController);
        touchPoint = new Vector3();

        vehicle = new Vehicle(this.game, this.physicsWorld, 1, 2,
                new Vector2(10, 10), (float) Math.PI, 60, 15, 25, 100);

        hud = new Hud(this.game);
        hud.setupBaseHud();
        if (gameController.getRole() == GameController.ROLE.DRIVER) {
            hud.setupDriverHud();
        }
        else if (gameController.getRole() == GameController.ROLE.BUILDER) {
            hud.setupBuilderHud();
        }
        uiStage.addActor(hud);

        this.uiStage.setDebugAll(true);
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
                updatePreGame(delta);
                break;

            case PLAYING:
                updatePlaying(delta);
                break;

            case DEAD:
                updateDead(delta);
                break;

            case GAME_OVER:
                updateGameOver(delta);
                break;
        }

        this.game.batch.begin();
        this.vehicle.render(this.game.batch);
        this.game.batch.end();
    }

    private void updatePreGame(float delta) {
        gameController.updatePreGame(delta);
        hud.updateTimer(gameController.getTimer());
    }

    private void updatePlaying(float delta) {
        gameController.updatePlaying(delta);
        hud.updateTimer(gameController.getTimer());

        if (hud.isLeftDown) {
            vehicle.setSteer(Vehicle.STEER_LEFT);
            Gdx.app.log("VEHICLE", "LEFT");
        }
        else if (hud.isRightDown) {
            vehicle.setSteer(Vehicle.STEER_RIGHT);
            Gdx.app.log("VEHICLE", "RIGHT");
        }
        else {
            vehicle.setSteer(Vehicle.STEER_NONE);
        }

        if (hud.isAccelerateDown) {
            vehicle.setAccelerate(Vehicle.ACC_ACCELERATE);
            Gdx.app.log("VEHICLE", "ACCELERATE");
        }
        else {
            vehicle.setAccelerate(Vehicle.ACC_NONE);
        }

        vehicle.update(delta);
    }

    private void updateDead(float delta) {
        gameController.updateDead(delta);
        hud.updateTimer(gameController.getTimer());
    }

    private void updateGameOver(float delta) {
        gameController.updateGameOver(delta);
        hud.updateTimer(gameController.getTimer());
    }
}
