package cm.smith.games.tracktion.controllers;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameController {

    public enum STATE {
        START_COUNTDOWN,
        PLAYING,
        DEAD_COUNTDOWN,
        GAME_OVER_COUNTDOWN
    }

    private STATE currentState;

    public GameController() {
        currentState = STATE.START_COUNTDOWN;
    }

    public STATE getState() {
        return this.currentState;
    }

}
