package seedu.address.model.schedule;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Appointment;
import seedu.address.model.schedule.exceptions.AppointmentNotFoundException;

import java.util.Iterator;

import static java.util.Objects.requireNonNull;

/**
 * A list of Appointments
 *
 * Supports a minimal set of list operations.
 */
public class Schedule implements Iterable<Appointment> {
    // Data Fields
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentListUnModifiable =
            FXCollections.unmodifiableObservableList(appointmentList);

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return appointmentListUnModifiable;
    }

    /**
     * Adds an Appointment to the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        appointmentList.add(toAdd);
    }

    /**
     * Removes the equivalent Appointment from the list.
     * The Appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!appointmentList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    @Override
    public Iterator<Appointment> iterator() {
        return appointmentList.iterator();
    }

    @Override
    public int hashCode() {
        return appointmentList.hashCode();
    }
}
