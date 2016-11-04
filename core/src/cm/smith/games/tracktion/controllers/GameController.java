package cm.smith.games.tracktion.controllers;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameController {

    public static final long ROLE_DRIVER = 0x1; // 001 in binary
    public static final long ROLE_BUILDER = 0x2; // 010 in binary

    public enum ROLE {
        DRIVER(0x1),
        BUILDER(0x2);

        private final long role;
        ROLE(long role) { this.role = role; }
        public long getValue() { return role; }
    }

    public enum STATE {
        START_COUNTDOWN,
        PLAYING,
        DEAD_COUNTDOWN,
        GAME_OVER_COUNTDOWN
    }

    private STATE currentState;
    private ROLE currentRole;

    public GameController() {
        currentState = STATE.START_COUNTDOWN;
    }

    public void setRole(ROLE role) {
        this.currentRole = role;
    }

    public STATE getState() {
        return this.currentState;
    }

}
