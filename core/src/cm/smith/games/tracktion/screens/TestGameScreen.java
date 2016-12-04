package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.BooleanArray;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.Packet;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.entities.Vehicle;
import cm.smith.games.tracktion.systems.RenderingSystem;
import cm.smith.games.tracktion.ui.Hud;
import cm.smith.games.tracktion.ui.LabelButton;
import cm.smith.games.tracktion.ui.UIImageButton;
import javafx.geometry.Bounds;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    public static final float LERP = 10f;
    private GameController gameController;
    private Vehicle vehicle;
    private Hud hud;

    public TestGameScreen(MainGame game, GameController.ROLE role) {
        super(game);

        // Add systems to ashley ECS engine
        this.engine.addSystem(new RenderingSystem(this.game.gameBatch));

        // Setup HUD
        hud = new Hud(this.game, role);
        uiStage.addActor(hud);

        // Setup game objects
        vehicle = new Vehicle(this.game, this.physicsWorld, 1.2f, 2.4f,
                new Vector2(10, 10), (float) Math.PI * 0.5f, 60, 15, 25, 80);
        this.engine.addEntity(vehicle);

        // Setup middleman that deals with google play services
        gameController = new GameController(role, vehicle, hud);
        game.multiplayerServices.setGameManager(gameController);

        game.multiplayerServices.findGame(role.getValue());
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        if (gameController.isGameRunning) {
            this.game.multiplayerServices.broadcastMessage();

            // Update the game camera
            float newX = (vehicle.transformComponent.pos.x - this.gameCamera.position.x) * LERP * delta;
            float newY = (vehicle.transformComponent.pos.y - this.gameCamera.position.y) * LERP * delta;
            this.gameCamera.translate(newX, newY);

            switch (gameController.getState()) {
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
        }
    }

    private void updatePreGame(float delta) {
        gameController.updatePreGame(delta);
        hud.updateTimer(gameController.time);
    }

    private void updatePlaying(float delta) {
        gameController.updatePlaying(delta);
        hud.updateTimer(gameController.time);

        if (gameController.getRole() == GameController.ROLE.DRIVER) {
            if (hud.isLeftDown) {
                vehicle.setSteer(Vehicle.STEER_LEFT);
            } else if (hud.isRightDown) {
                vehicle.setSteer(Vehicle.STEER_RIGHT);
            } else {
                vehicle.setSteer(Vehicle.STEER_NONE);
            }

            if (hud.isAccelerateDown) {
                vehicle.setAccelerate(Vehicle.ACC_ACCELERATE);
            } else {
                vehicle.setAccelerate(Vehicle.ACC_NONE);
            }
        }

        vehicle.update(delta);
    }

    private void updateDead(float delta) {
        gameController.updateDead(delta);
        hud.updateTimer(gameController.time);
    }

    private void updateGameOver(float delta) {
        gameController.updateGameOver(delta);
        hud.updateTimer(gameController.time);
    }
}
