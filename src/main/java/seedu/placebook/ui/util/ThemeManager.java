package seedu.placebook.ui.util;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ThemeManager {
    private static final double size = 30;
    private static final String DARK_THEME_CSS = "view/DarkTheme.css";
    private static final String LIGHT_THEME_CSS = "view/LightTheme.css";
    private static final String SUN = "images/sun.png";
    private static final String MOON = "images/moon.png";
    private enum Theme { LIGHT, DARK }

    private Theme currentTheme;
    private String currentStylesheet;
    private String currentButtonImage;

    /**
     * Constructs ThemeManager
     */
    public ThemeManager() {
        currentTheme = Theme.LIGHT;
        currentStylesheet = LIGHT_THEME_CSS;
        currentButtonImage = SUN;
    }

    /**
     * Changes the current theme.
     *
     * @param primaryStage the primary stage
     * @param themeButton the theme button
     */
    public void changeTheme(Stage primaryStage, Button themeButton) {
        primaryStage.getScene().getStylesheets().remove(currentStylesheet);

        toggle();

        primaryStage.getScene().getStylesheets().add(currentStylesheet);
        ImageView buttonGraphic = new ImageView(currentButtonImage);
        buttonGraphic.setFitHeight(size);
        buttonGraphic.setFitWidth(size);
        themeButton.setGraphic(buttonGraphic);
    }

    private void toggle() {
        switch (currentTheme) {
        case DARK:
            currentTheme = Theme.LIGHT;
            currentStylesheet = LIGHT_THEME_CSS;
            currentButtonImage = SUN;
            break;
        case LIGHT:
            currentTheme = Theme.DARK;
            currentStylesheet = DARK_THEME_CSS;
            currentButtonImage = MOON;
            break;
        default:
        }
    }

    public String getCurrentStylesheet() {
        return this.currentStylesheet;
    }
}
