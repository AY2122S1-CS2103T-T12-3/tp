package seedu.placebook.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;

/**
 * An UI component that displays information of a {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    private static final BackgroundFill RED =
            new BackgroundFill(Color.RED.deriveColor(0, 1, 1, 0.1), null, null);
    private static final BackgroundFill YELLOW =
            new BackgroundFill(Color.ORANGE.deriveColor(0, 1, 1, 0.12), null, null);
    private static final BackgroundFill GREEN =
            new BackgroundFill(Color.GREEN.deriveColor(0, 1, 1, 0.12), null, null);

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox appointmentCardPane;
    @FXML
    private GridPane appointmentColorPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private TitledPane clientsPane;
    @FXML
    private Text clientNames;
    @FXML
    private Label address;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Appointment} and index to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        description.setText(appointment.getDescription());
        address.setText(appointment.getLocation().value);
        startDate.setText(appointment.getStartDateTimeString()); // Changed from date.setText
        endDate.setText(appointment.getEndDateTimeString());

        UniquePersonList clients = appointment.getClients();
        clientNames.textProperty().bindBidirectional(clients.getPersonNames());
        appointmentCardPane.widthProperty().addListener((observable, oldValue, newValue) ->
                clientNames.setWrappingWidth((Double) newValue * 3 / 4)
        );

        PersonListPanel clientsDetail = new PersonListPanel(clients.asUnmodifiableObservableList());
        clientsPane.setContent(clientsDetail.getRoot());

        this.setUrgencyDisplay();
    }

    private void setUrgencyDisplay() {
        switch (appointment.getUrgency()) {
        case HIGH:
            appointmentColorPane.setBackground(new Background(RED));
            break;
        case MEDIUM:
            appointmentColorPane.setBackground(new Background(YELLOW));
            break;
        case LOW:
            appointmentColorPane.setBackground(new Background(GREEN));
            break;
        default:
        }
    }

    public void expandClients(boolean expanded) {
        clientsPane.setExpanded(expanded);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return id.getText().equals(card.id.getText())
                && clientsPane.equals(card.clientsPane);
    }
}
