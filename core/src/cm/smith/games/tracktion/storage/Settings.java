package cm.smith.games.tracktion.storage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

/**
 * Created by anthony on 2016-11-02.
 */

public class Settings {

    /**
     * Keys to track save data
     */
    public static final String SETTINGS = "settings";
    public static final String SETTINGS_EFFECTS_LEVEL = "settings_effects_level";
    public static final String SETTINGS_MUSIC_LEVEL = "settings_music_level";
    public static final String SETTINGS_TURN_SENS = "settings_turn_sens";

    /**
     * Temporary object to hold the currently loaded settings in memory.
     */
    public static HashMap<String, Object> VALUES = new HashMap<String, Object>();
    static {
        VALUES.put(Settings.SETTINGS_EFFECTS_LEVEL, 1.0f);
        VALUES.put(Settings.SETTINGS_MUSIC_LEVEL, 1.0f);
        VALUES.put(Settings.SETTINGS_TURN_SENS, 0.5f);
    }

    public void setEffectsLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            VALUES.put(Settings.SETTINGS_EFFECTS_LEVEL, newValue);
    }

    public float getEffectsLevel() {
        return (Float)VALUES.get(Settings.SETTINGS_EFFECTS_LEVEL);
    }

    public void setMusicLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            VALUES.put(Settings.SETTINGS_MUSIC_LEVEL, newValue);
    }

    public float getMusicLevel(){
        return (Float)VALUES.get(Settings.SETTINGS_MUSIC_LEVEL);
    }

    public void setTurnSensitivity(float newValue) {
        if (newValue >= 0 && newValue <= 1.0f)
            VALUES.put(Settings.SETTINGS_TURN_SENS, (Float)newValue);
    }

    public float getTurnSensitivity() {
        return (Float)VALUES.get(Settings.SETTINGS_TURN_SENS);
    }

    public void saveSettings() {
        // Get the preferences instance
        Preferences settingsPref = Gdx.app.getPreferences(Settings.SETTINGS);

        // Save all the settings value
        Gdx.app.log("TEST", Float.toString(getEffectsLevel()));
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
