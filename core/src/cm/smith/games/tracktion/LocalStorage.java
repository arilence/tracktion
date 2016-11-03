package cm.smith.games.tracktion;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashMap;

/**
 * Created by anthony on 2016-11-02.
 */

public class LocalStorage {

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
        VALUES.put(LocalStorage.SETTINGS_EFFECTS_LEVEL, 1.0f);
        VALUES.put(LocalStorage.SETTINGS_MUSIC_LEVEL, 1.0f);
        VALUES.put(LocalStorage.SETTINGS_TURN_SENS, 1.0f);
    }

    public static void setEffectsLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0)
            VALUES.put(LocalStorage.SETTINGS_EFFECTS_LEVEL, newValue);
    }

    public static float getEffectsLevel() {
        return (Float)VALUES.get(LocalStorage.SETTINGS_EFFECTS_LEVEL);
    }

    public static void setMusicLevel(float newValue) {
        if (newValue >= 0 && newValue <= 1.0)
            VALUES.put(LocalStorage.SETTINGS_MUSIC_LEVEL, newValue);
    }

    public static float getMusicLevel(){
        return (Float)VALUES.get(LocalStorage.SETTINGS_MUSIC_LEVEL);
    }

    public static void setTurnSensitivity(float newValue) {
        if (newValue >= 0 && newValue <= 1.0)
            VALUES.put(LocalStorage.SETTINGS_TURN_SENS, newValue);
    }

    public static float getTurnSensitivity() {
        return (Float)VALUES.get(LocalStorage.SETTINGS_TURN_SENS);
    }

    public static void saveSettings() {
        // Get the preferences instance
        Preferences settingsPref = Gdx.app.getPreferences(LocalStorage.SETTINGS);

        // Save all the settings value
        settingsPref.putFloat(LocalStorage.SETTINGS_EFFECTS_LEVEL, getEffectsLevel());
        settingsPref.putFloat(LocalStorage.SETTINGS_MUSIC_LEVEL, getMusicLevel());
        settingsPref.putFloat(LocalStorage.SETTINGS_TURN_SENS, getTurnSensitivity());

        // Must run or else the values will not be finalized
        settingsPref.flush();
    }

    public static void loadSettings() {
        // Get the preferences instance
        Preferences settingsPref = Gdx.app.getPreferences(LocalStorage.SETTINGS);

        // Loads all the settings value
        setEffectsLevel(settingsPref.getFloat(LocalStorage.SETTINGS_EFFECTS_LEVEL, getEffectsLevel()));
        setMusicLevel(settingsPref.getFloat(LocalStorage.SETTINGS_MUSIC_LEVEL, getMusicLevel()));
        setTurnSensitivity(settingsPref.getFloat(LocalStorage.SETTINGS_TURN_SENS, getTurnSensitivity()));
    }

}
