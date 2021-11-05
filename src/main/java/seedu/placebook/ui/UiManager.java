package seedu.placebook.ui;

import java.util.Optional;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import seedu.placebook.MainApp;
import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.commons.util.StringUtil;
import seedu.placebook.logic.Logic;
import seedu.placebook.ui.util.ThemeManager;

/**
 * The manager of the UI component.
 */
public class UiManager implements Ui {

    public static final String ALERT_DIALOG_PANE_FIELD_ID = "alertDialogPane";

    private static final Logger logger = LogsCenter.getLogger(UiManager.class);
    private static final String ICON_APPLICATION = "/images/placebook.png";

    private Logic logic;
    private MainWindow mainWindow;
    private ThemeManager themeManager;

    /**
     * Creates a {@code UiManager} with the given {@code Logic}.
     */
    public UiManager(Logic logic) {
        super();
        this.logic = logic;
    }

    @Override
    public void start(Stage primaryStage) {
        logger.info("Starting UI...");

        this.themeManager = new ThemeManager();

        //Set the application icon.
        primaryStage.getIcons().add(getImage(ICON_APPLICATION));

        try {
            mainWindow = new MainWindow(primaryStage, logic, themeManager);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();

        } catch (Throwable e) {
            logger.severe(StringUtil.getDetails(e));
            showFatalErrorDialogAndShutdown("Fatal error during initializing", e);
        }
    }

    private Image getImage(String imagePath) {
        return new Image(MainApp.class.getResourceAsStream(imagePath));
    }

    void showAlertDialogAndWait(Alert.AlertType type, String title, String headerText, String contentText) {
        showAlertDialogAndWait(mainWindow.getPrimaryStage(), type, title, headerText, contentText);
    }

    /**
     * Shows an alert dialog on {@code owner} with the given parameters.
     * This method only returns after the user has closed the alert dialog.
     */
    private void showAlertDialogAndWait(Stage owner, AlertType type, String title, String headerText,
                                               String contentText) {
        final Alert alert = new Alert(type);
        alert.getDialogPane().getStylesheets().add(themeManager.getCurrentStylesheet());
        alert.initOwner(owner);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.getDialogPane().setId(ALERT_DIALOG_PANE_FIELD_ID);
        alert.showAndWait();
    }

    /**
     * Shows an error alert dialog with {@code title} and error message, {@code e},
     * and exits the application after the user has closed the alert dialog.
     */
    private void showFatalErrorDialogAndShutdown(String title, Throwable e) {
        logger.severe(title + " " + e.getMessage() + StringUtil.getDetails(e));
        showAlertDialogAndWait(Alert.AlertType.ERROR, title, e.getMessage(), e.toString());
        Platform.exit();
        System.exit(1);
    }

    /**
     * Shows a confirmation dialog to confirm the deletion of an object.
     */
    @Override
    public boolean showDeleteDialogAndWait(String contentText) {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(themeManager.getCurrentStylesheet());

        alert.setHeight(500);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to delete?");
        alert.setContentText(contentText);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElseGet(() -> ButtonType.CANCEL) == ButtonType.OK;
    }

}
