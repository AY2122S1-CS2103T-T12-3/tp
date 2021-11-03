package seedu.placebook.logic;

import javafx.stage.Stage;
import seedu.placebook.ui.Ui;

public class UiStubFactory {
    private static class PositiveConfirmationUi implements Ui {

        @Override
        public void start(Stage primaryStage) {

        }

        @Override
        public boolean showDeleteDialogAndWait(String contentText) {
            return true;
        }
    }

    private static class NegativeConfirmationUi implements Ui {

        @Override
        public void start(Stage primaryStage) {

        }

        @Override
        public boolean showDeleteDialogAndWait(String contentText) {
            return false;
        }
    }

    private static final Ui POSITIVE_CONFIRMATION_UI = new PositiveConfirmationUi();
    private static final Ui NEGATIVE_CONFIRMATION_UI = new NegativeConfirmationUi();

    public static Ui getUiStub(boolean expectedConfirmation) {
        if (expectedConfirmation) {
            return POSITIVE_CONFIRMATION_UI;
        } else {
            return NEGATIVE_CONFIRMATION_UI;
        }
    }
}
