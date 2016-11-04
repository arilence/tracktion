package cm.smith.games.tracktion.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

/**
 * Created by anthony on 2016-11-02.
 */

public class Settings {

    /*
     * Keys to track save data
     */
    public static final String SETTINGS = "settings";
    public static final String SETTINGS_EFFECTS_LEVEL = "settings_effects_level";
    public static final String SETTINGS_MUSIC_LEVEL = "settings_music_level";
    public static final String SETTINGS_TURN_SENS = "settings_turn_sens";

    /*
     * Current state of the settings values
     */
    private Float effectsLevel = 1.0f;
    private Float musicLevel = 1.0f;
    private Float turnSensitivity = 0.5f;

    /*
     * Getter and setters
     */
    public void setEffectsLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            effectsLevel = newValue;
    }

    public float getEffectsLevel() {
        return this.effectsLevel;
    }

    public void setMusicLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            musicLevel = newValue;
    }

    public float getMusicLevel(){
        return this.musicLevel;
    }

    public void setTurnSensitivity(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            turnSensitivity = newValue;
    }

    public float getTurnSensitivity() {
        return this.turnSensitivity;
    }

    /*
     * Manage data persistence
     */
    public void saveSettings() {
        // Get the preferences instance
        Preferences settingsPref = Gdx.app.getPreferences(Settings.SETTINGS);

        // Save all the settings value
        settingsPref.putFloat(Settings.SETTINGS_EFFECTS_LEVEL, getEffectsLevel());
        settingsPref.putFloat(Settings.SETTINGS_MUSIC_LEVEL, getMusicLevel());
        settingsPref.putFloat(Settings.SETTINGS_TURN_SENS, getTurnSensitivity());

        // Must run or else the values will not be finalized
        settingsPref.flush();
    }

    public void loadSettings() {
        // Get the preferences instance
        Preferences settingsPref = Gdx.app.getPreferences(Settings.SETTINGS);

        // Loads all the settings value
        setEffectsLevel(settingsPref.getFloat(Settings.SETTINGS_EFFECTS_LEVEL, getEffectsLevel()));
        setMusicLevel(settingsPref.getFloat(Settings.SETTINGS_MUSIC_LEVEL, getMusicLevel()));
        setTurnSensitivity(settingsPref.getFloat(Settings.SETTINGS_TURN_SENS, getTurnSensitivity()));
    }


}
