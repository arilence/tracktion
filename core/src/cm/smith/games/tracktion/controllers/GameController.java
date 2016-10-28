package cm.smith.games.tracktion.controllers;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameController {

    public boolean isPlaying;
    public boolean shouldLeave;

    public GameController() {
        isPlaying = false;
        shouldLeave = false;
    }

    public void startGame() {
        if (!isGameRunning()) {
            isPlaying = true;
        }
    }

    public void stopGame() {
        if (isGameRunning()) {
            isPlaying = false;
            shouldLeave = true;
        }
    }

    public boolean isGameRunning() {
        return isPlaying;
    }

}
