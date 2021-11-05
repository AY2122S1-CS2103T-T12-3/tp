package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import org.junit.jupiter.api.Test;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.ui.Ui;

public class DelAppCommandTest {

    private final Model model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
    private final Ui positive = UiStubFactory.getUiStub(true);
    private final Ui negative = UiStubFactory.getUiStub(false);

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        DelAppCommand delAppCommand = new DelAppCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DelAppCommand.MESSAGE_SUCCESS, appointmentToDelete);

        ModelManager expectedModel = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
        expectedModel.deleteAppointment(appointmentToDelete);

        assertCommandSuccess(delAppCommand, model, positive, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredList_cancel() {

        DelAppCommand delAppCommand = new DelAppCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = DelAppCommand.MESSAGE_NO_APPOINTMENT_DELETED;

        assertCommandSuccess(delAppCommand, model, negative, expectedMessage, model);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DelAppCommand deleteCommand = new DelAppCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, positive, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentToDelete = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());

        DelAppCommand delAppCommand = new DelAppCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DelAppCommand.MESSAGE_SUCCESS, appointmentToDelete);

        Model expectedModel = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
        expectedModel.deleteAppointment(appointmentToDelete);
        showNoAppointment(expectedModel);

        assertCommandSuccess(delAppCommand, model, positive, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of contacts list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSchedule().getSchedule().size());

        DelAppCommand delAppCommand = new DelAppCommand(outOfBoundIndex);

        assertCommandFailure(delAppCommand, model, positive, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DelAppCommand delAppFirstCommand = new DelAppCommand(INDEX_FIRST_APPOINTMENT);
        DelAppCommand delAppSecondCommand = new DelAppCommand(INDEX_SECOND_APPOINTMENT);

        // same object -> returns true
        assertTrue(delAppFirstCommand.equals(delAppFirstCommand));

        // same values -> returns true
        DelAppCommand delAppFirstCommandCopy = new DelAppCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(delAppFirstCommand.equals(delAppFirstCommandCopy));

        // different types -> returns false
        assertFalse(delAppFirstCommand.equals(1));

        // null -> returns false
        assertFalse(delAppFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(delAppFirstCommand.equals(delAppSecondCommand));
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoAppointment(Model model) {
        model.updateFilteredAppointmentList(p -> false);

        assertTrue(model.getFilteredAppointmentList().isEmpty());
    }
}
