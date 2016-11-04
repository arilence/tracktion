package cm.smith.games.tracktion.screens;

import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.MainGame;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameScreen extends BaseScreen {

    private long role;
    private GameController gameController;

    public GameScreen(MainGame game, long role) {
        super(game);
        this.role = role;
    }

    @Override
    public void show() {
        super.show();

        gameController = new GameController();
        game.playServices.setGameManager(gameController);

        // try to initiate finding a game
        if (!game.playServices.isSignedIn()) {
            game.playServices.signIn();
        }
        game.playServices.connectOnline();
        game.playServices.findGame(role);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}
