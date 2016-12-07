package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.math.Vector2;

import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.entities.GameBoard;
import cm.smith.games.tracktion.entities.Vehicle;
import cm.smith.games.tracktion.ui.Hud;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameScreen extends BaseScreen {

    private GameController.ROLE role;
    private GameController gameController;

    public GameScreen(MainGame game, GameController.ROLE role) {
        super(game);
        this.role = role;
    }

    @Override
    public void show() {
        super.show();

        Vehicle vehicle = new Vehicle(this.game, this.physicsWorld, 1.2f, 2.4f,
                new Vector2(10, 10), (float) Math.PI * 0.5f, 60, 15, 25, 80);

        Hud hud = new Hud(this, GameController.ROLE.DRIVER);
        GameBoard gameBoard = new GameBoard(this);

        gameController = new GameController(GameController.ROLE.DRIVER, vehicle, hud, gameBoard);
        game.multiplayerServices.setGameManager(gameController);

        // try to initiate finding a game
        if (!game.playServices.isSignedIn()) {
            game.playServices.signIn();
        }
        game.playServices.connectOnline();
        game.multiplayerServices.findGame(role.getValue());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
