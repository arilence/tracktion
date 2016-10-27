package cm.smith.games.tracktion.screens;

import com.badlogic.gdx.Gdx;

import cm.smith.games.tracktion.GameController;
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

        String roleName = "UNKNOWN";
        if (role == MainGame.ROLE_DRIVER)
            roleName = "Driver";
        if (role == MainGame.ROLE_BUILDER)
            roleName = "Builder";

        if (gameController.isPlaying) {
            game.batch.begin();
            game.font.draw(game.batch, "You are the " + roleName, 150, 200);
            game.batch.end();
        }
        if (gameController.shouldLeave) {
            game.setScreen(new TitleScreen(this.game));
            dispose();
        }
    }
}
