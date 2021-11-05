package seedu.placebook.model.schedule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_CARL_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.CARL_APPOINTMENT;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.placebook.model.schedule.exceptions.AppointmentNotFoundException;
import seedu.placebook.model.schedule.exceptions.DuplicateAppointmentException;

public class ScheduleTest {

    private final Schedule schedule = new Schedule();

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> schedule.addAppointment(null));
    }

    @Test
    public void add_unique_existingAppointment() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        assertTrue(schedule.contains(ALICE_APPOINTMENT));
    }

    @Test
    public void add_duplicate_existingAppointment() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        assertThrows(DuplicateAppointmentException.class, () -> schedule.addAppointment(ALICE_APPOINTMENT));
    }
    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> schedule.deleteAppointment(null));
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> schedule.deleteAppointment(ALICE_APPOINTMENT));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.deleteAppointment(ALICE_APPOINTMENT);
        Schedule expectedSchedule = new Schedule();
        assertEquals(expectedSchedule, schedule);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> schedule.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void setAppointment_validInput_success() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.addAppointment(CARL_APPOINTMENT);
        schedule.setAppointment(ALICE_APPOINTMENT, ALICE_CARL_APPOINTMENT);
        assertFalse(schedule.contains(ALICE_APPOINTMENT));
        assertTrue(schedule.contains(ALICE_CARL_APPOINTMENT));
        assertTrue(schedule.contains(CARL_APPOINTMENT));
    }

    @Test
    public void setAppointment_targetAppointmentNotInClientList_exceptionThrown() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        assertThrows(AppointmentNotFoundException.class, ()
            -> schedule.setAppointment(CARL_APPOINTMENT, CARL_APPOINTMENT));
    }

    @Test
    public void setAppointment_invalidAppointmentToEdit_exceptionThrown() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.addAppointment(CARL_APPOINTMENT);
        assertThrows(NullPointerException.class, ()
            -> schedule.setAppointment(null, ALICE_CARL_APPOINTMENT));
    }

    @Test
    public void setAppointment_invalidEditedAppointment_exceptionThrown() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.addAppointment(CARL_APPOINTMENT);
        assertThrows(NullPointerException.class, ()
            -> schedule.setAppointment(ALICE_CARL_APPOINTMENT, null));
    }

    @Test
    public void sortAppointmentByDescription() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.addAppointment(CARL_APPOINTMENT);

        // Order in expected is by description
        ObservableList<Appointment> expected = FXCollections.observableArrayList();
        expected.add(CARL_APPOINTMENT);
        expected.add(ALICE_APPOINTMENT);

        schedule.sortAppointmentByDescription();
        assertEquals(expected, schedule.getSchedule());
    }

    @Test
    public void sortAppointmentByTime() {
        schedule.addAppointment(ALICE_APPOINTMENT);
        schedule.addAppointment(CARL_APPOINTMENT);

        // Order in expected is by time
        ObservableList<Appointment> expected = FXCollections.observableArrayList();
        expected.add(ALICE_APPOINTMENT);
        expected.add(CARL_APPOINTMENT);

        // List is by default sorted by time, sort by description first
        schedule.sortAppointmentByDescription();
        schedule.sortAppointmentByTimePeriod();
        assertEquals(expected, schedule.getSchedule());
    }
}
