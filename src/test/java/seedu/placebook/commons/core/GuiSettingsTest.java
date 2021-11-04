package seedu.placebook.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.awt.Point;
import java.util.Objects;

import org.junit.jupiter.api.Test;


class GuiSettingsTest {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;

    @Test
    void getWindowWidthMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertEquals(DEFAULT_WIDTH, defaultGuiSettings.getWindowWidth());

        GuiSettings guiSettings = new GuiSettings(10, 20, 1, 2);
        assertEquals(10, guiSettings.getWindowWidth());
    }

    @Test
    void getWindowHeightMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertEquals(DEFAULT_HEIGHT, defaultGuiSettings.getWindowHeight());

        GuiSettings guiSettings = new GuiSettings(10, 20, 1, 2);
        assertEquals(20, guiSettings.getWindowHeight());
    }

    @Test
    void getWindowCoordinatesMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();
        assertNull(defaultGuiSettings.getWindowCoordinates());

        GuiSettings guiSettings = new GuiSettings(10, 20, 1, 2);
        assertEquals(new Point(1, 2), guiSettings.getWindowCoordinates());
    }

    @Test
    void equalsMethod() {
        // assertEquals will call equals() method
        GuiSettings defaultGuiSettings = new GuiSettings();
        GuiSettings guiSettings = new GuiSettings();
        assertNotNull(defaultGuiSettings);
        assertEquals(defaultGuiSettings, defaultGuiSettings);
        assertEquals(defaultGuiSettings, guiSettings);

        GuiSettings expectedGuiSettings = new GuiSettings(10, 20, 1, 2);
        GuiSettings sameGuiSettings = new GuiSettings(10, 20, 1, 2);
        assertEquals(expectedGuiSettings, sameGuiSettings);

        // different width -> not equal
        GuiSettings widthDifferentSetting = new GuiSettings(9, 20, 1, 2);
        assertNotEquals(expectedGuiSettings, widthDifferentSetting);

        //different height -> not equal
        GuiSettings heightDifferentSetting = new GuiSettings(10, 19, 1, 2);
        assertNotEquals(expectedGuiSettings, heightDifferentSetting);

        //different x -> not equal
        GuiSettings xDifferentSetting = new GuiSettings(10, 20, 0, 2);
        assertNotEquals(expectedGuiSettings, xDifferentSetting);

        //different y -> not equal
        GuiSettings yDifferentSetting = new GuiSettings(10, 20, 1, 1);
        assertNotEquals(expectedGuiSettings, yDifferentSetting);

        // different type
        assertFalse(defaultGuiSettings.equals(new Point(1, 2)));
        assertFalse(defaultGuiSettings.equals(DEFAULT_HEIGHT));
    }

    @Test
    void hashCodeMethod() {
        GuiSettings defaultGuiSettings = new GuiSettings();

        assertEquals(Objects.hash(
                defaultGuiSettings.getWindowWidth(),
                defaultGuiSettings.getWindowHeight(),
                defaultGuiSettings.getWindowCoordinates()),
                defaultGuiSettings.hashCode());
    }

    @Test
    void testToString() {
        GuiSettings defaultGuiSettings = new GuiSettings();

        assertEquals("Width : 740.0\n"
                        + "Height : 600.0\n"
                        + "Position : null",
                defaultGuiSettings.toString());
    }
}
