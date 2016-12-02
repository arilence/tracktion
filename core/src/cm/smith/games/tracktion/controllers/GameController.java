package cm.smith.games.tracktion.controllers;

import cm.smith.games.tracktion.entities.Vehicle;

/**
 * Created by anthony on 2016-09-18.
 */
public class GameController {

    public enum ROLE {
        DRIVER(0x1),
        BUILDER(0x2);

        private final long role;
        ROLE(long role) { this.role = role; }
        public long getValue() { return role; }
    }

    public enum STATE {
        PRE_GAME,
        PLAYING,
        DEAD,
        GAME_OVER
    }

    private STATE currentState;
    private ROLE currentRole;
    private boolean firstTimeState;     // tracks the first time update of state

    private static float TIME_PREGAME = 5f;
    private static float TIME_PLAYING = 0f;
    private static float TIME_DEAD = 5f;
    private static float TIME_GAMEOVER = 30f;

    // Store all of the network shared entities in the controller
    private float time = 0f;    // time left for the current state
    private Vehicle vehicle;

    public GameController() {
        currentState = STATE.PRE_GAME;
        firstTimeState = true;
    }

    public void setRole(ROLE role) {
        this.currentRole = role;
    }

    public ROLE getRole() { return this.currentRole; }

    public STATE getState() {
        return this.currentState;
    }

    public float getTimer() {
        return this.time;
    }

    public void updatePreGame(float delta) {
        if (firstTimeState) {
            time = TIME_PREGAME;
            firstTimeState = false;
        }
        if (time <= 0) {
            currentState = STATE.PLAYING;
        }
        time -= delta;
    }

    public void updatePlaying(float delta) {
        if (firstTimeState) {
            time = TIME_PLAYING;
            firstTimeState = false;
        }
        time += delta;
    }

    public void updateDead(float delta) {
        if (firstTimeState) {
            time = TIME_DEAD;
            firstTimeState = false;
        }
        if (time <= 0) {
            currentState = STATE.GAME_OVER;
        }
        time -= delta;
    }

    public void updateGameOver(float delta) {
        if (firstTimeState) {
            time = TIME_GAMEOVER;
            firstTimeState = false;
        }
        time -= delta;
    }

}
