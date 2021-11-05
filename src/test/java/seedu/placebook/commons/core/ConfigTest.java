package seedu.placebook.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Paths;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void getLogLevelMethod() {
        Config defaultConfig = new Config();
        assertEquals(defaultConfig.getLogLevel(), Level.INFO);
    }

    @Test
    public void setLogLevel_differentLevels_getLogLevelUpdated() {
        // test for all different levels of log used
        Config defaultConfig = new Config();
        assertEquals(Level.INFO, defaultConfig.getLogLevel());
        defaultConfig.setLogLevel(Level.SEVERE);
        assertEquals(Level.SEVERE, defaultConfig.getLogLevel());
        defaultConfig.setLogLevel(Level.FINE);
        assertEquals(Level.FINE, defaultConfig.getLogLevel());
        defaultConfig.setLogLevel(Level.WARNING);
        assertEquals(Level.WARNING, defaultConfig.getLogLevel());
    }

    @Test
    public void getUserPrefsFilePathMethod() {
        Config defaultConfig = new Config();
        assertEquals(Paths.get("preferences.json"), defaultConfig.getUserPrefsFilePath());
    }

    @Test
    public void equalsMethod() {
        // assertEquals will call equals() method
        Config defaultConfig = new Config();
        Config currentConfig = new Config();
        assertNotNull(defaultConfig);
        assertEquals(defaultConfig, defaultConfig);
        assertEquals(defaultConfig, currentConfig);

        // different log level -> not equal
        currentConfig.setLogLevel(Level.FINE);
        assertNotEquals(defaultConfig, currentConfig);
        currentConfig.setLogLevel(defaultConfig.getLogLevel());
        assertEquals(defaultConfig, currentConfig);

        //different userPrefsFilePath -> not equal
        currentConfig.setUserPrefsFilePath(Paths.get("config/preferences.json"));
        assertNotEquals(defaultConfig, currentConfig);
        currentConfig.setUserPrefsFilePath(defaultConfig.getUserPrefsFilePath());
        assertEquals(defaultConfig, currentConfig);

        // both attribute different -> not equal
        currentConfig.setLogLevel(Level.FINE);
        currentConfig.setUserPrefsFilePath(Paths.get("config/preferences.json"));
        assertNotEquals(defaultConfig, currentConfig);

        // different type
        assertFalse(defaultConfig.equals(Level.INFO));
        assertFalse(defaultConfig.equals(Paths.get("config/preferences.json")));
    }


}
