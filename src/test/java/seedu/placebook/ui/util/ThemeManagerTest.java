package seedu.placebook.ui.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThemeManagerTest {

    @Test
    void getCurrentStylesheet_default_lightThemeReturned() {
        ThemeManager themeManager = new ThemeManager();
        assertEquals("view/LightTheme.css", themeManager.getCurrentStylesheet());
    }
}