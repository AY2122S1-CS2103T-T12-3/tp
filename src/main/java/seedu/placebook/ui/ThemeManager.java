package seedu.placebook.ui;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ThemeManager {
    private static final double size = 30;
    private static final String DARK_THEME_CSS = "view/DarkTheme.css";
    private static final String LIGHT_THEME_CSS = "view/LightTheme.css";
    private enum Theme { LIGHT, DARK }

    private Theme currentTheme;
    private String currentStylesheet;

    /**
     * Constructs ThemeManager
     */
    public ThemeManager() {
        currentTheme = Theme.LIGHT;
        currentStylesheet = LIGHT_THEME_CSS;
    }

    /**
     * Changes the current theme.
     *
     * @param primaryStage the primary stage
     * @param themeButton the theme button
     */
    public void changeTheme(Stage primaryStage, Button themeButton) {
        switch (currentTheme) {
        case DARK:
            primaryStage.getScene().getStylesheets().remove(DARK_THEME_CSS);
            primaryStage.getScene().getStylesheets().add(LIGHT_THEME_CSS);

            ImageView sun = new ImageView("images/sun.png");
            sun.setFitHeight(size);
            sun.setFitWidth(size);
            themeButton.setGraphic(sun);

            currentTheme = Theme.LIGHT;
            currentStylesheet = LIGHT_THEME_CSS;
            break;
        case LIGHT:
            primaryStage.getScene().getStylesheets().remove(LIGHT_THEME_CSS);
            primaryStage.getScene().getStylesheets().add(DARK_THEME_CSS);

            ImageView moon = new ImageView("images/moon.png");
            moon.setFitHeight(size);
            moon.setFitWidth(size);
            themeButton.setGraphic(moon);

            currentTheme = Theme.DARK;
            currentStylesheet = DARK_THEME_CSS;
            break;
        default:
        }
    }

    public String getCurrentStylesheet() {
        return this.currentStylesheet;
    }
}
