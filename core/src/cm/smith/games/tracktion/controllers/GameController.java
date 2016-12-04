package cm.smith.games.tracktion.controllers;

import java.util.HashMap;
import java.util.Map;

import cm.smith.games.tracktion.entities.Vehicle;
import cm.smith.games.tracktion.ui.Hud;

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
        private static final Map<Long, ROLE> lookup = new HashMap<Long, ROLE>();

        static {
            for (ROLE d : ROLE.values()) {
                lookup.put(d.getValue(), d);
            }
        }

        public static ROLE get(long abbreviation) {
            return lookup.get(abbreviation);
        }
    }

    public enum STATE {
        PRE_GAME((byte)'P'),
        PLAYING((byte)'L'),
        DEAD((byte)'D'),
        GAME_OVER((byte)'G');

        private final byte state;
        STATE(byte state) { this.state = state; }
        public byte getValue() { return state; }
        private static final Map<Byte, STATE> lookup = new HashMap<Byte, STATE>();

        static {
            for (STATE d : STATE.values()) {
                lookup.put(d.getValue(), d);
            }
        }

        public static STATE get(byte abbreviation) {
            return lookup.get(abbreviation);
        }
    }

    public boolean isGameRunning;
    private STATE currentState;
    private ROLE currentRole;
    private boolean firstTimeState;     // tracks the first time update of state

    private static float TIME_PREGAME = 5f;
    private static float TIME_PLAYING = 0f;
    private static float TIME_DEAD = 5f;
    private static float TIME_GAMEOVER = 30f;

    // Store all of the network shared entities in the controller
    public float time = 0f;    // time left for the current state
    public Vehicle vehicle;
    public Hud hud;

    public GameController(ROLE role, Vehicle vehicle, Hud hud) {
        isGameRunning = false;
        currentState = STATE.PRE_GAME;
        firstTimeState = true;

        this.currentRole = role;
        this.vehicle = vehicle;
        this.hud = hud;
    }

    public ROLE getRole() {
        return this.currentRole;
    }

    public STATE getState() {
        return this.currentState;
    }

    public void updatePreGame(float delta) {
        time -= delta;
        if (firstTimeState) {
            time = TIME_PREGAME;
            firstTimeState = false;
        }
        if (time <= 0) {
            currentState = STATE.PLAYING;
            firstTimeState = true;
        }
    }

    public void updatePlaying(float delta) {
        time += delta;
        if (firstTimeState) {
            time = TIME_PLAYING;
            firstTimeState = false;
        }
        if (vehicle.isDead()) {
            currentState = STATE.DEAD;
        }
    }

    public void updateDead(float delta) {
        time -= delta;
        if (firstTimeState) {
            time = TIME_DEAD;
            firstTimeState = false;
        }
        if (time <= 0) {
            currentState = STATE.GAME_OVER;
        }
    }

    public void updateGameOver(float delta) {
        time -= delta;
        if (firstTimeState) {
            time = TIME_GAMEOVER;
            firstTimeState = false;
        }
    }

}
