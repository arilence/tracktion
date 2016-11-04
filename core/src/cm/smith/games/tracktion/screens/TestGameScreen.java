package cm.smith.games.tracktion.screens;

import cm.smith.games.tracktion.MainGame;
import cm.smith.games.tracktion.controllers.GameController;
import cm.smith.games.tracktion.ui.LabelButton;

/**
 * Created by anthony on 2016-11-03.
 */

public class TestGameScreen extends BaseScreen {

    private GameController gameController;

    public TestGameScreen(MainGame game, GameController.ROLE role) {
        super(game);
        gameController = new GameController();
        gameController.setRole(role);
    }

    @Override
    public void show() {
        super.show();

        gameController = new GameController();
        game.playServices.setGameManager(gameController);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    private void setupControlsUI() {

    }
}
