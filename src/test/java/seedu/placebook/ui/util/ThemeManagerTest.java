package seedu.placebook.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ThemeManagerTest {

    @Test
    void getCurrentStylesheet_default_lightThemeReturned() {
        ThemeManager themeManager = new ThemeManager();
        assertEquals("view/LightTheme.css", themeManager.getCurrentStylesheet());
    }
}
