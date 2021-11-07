package seedu.placebook.model.schedule;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.exceptions.AppointmentNotFoundException;
import seedu.placebook.model.schedule.exceptions.ClashingAppointmentsException;
import seedu.placebook.model.schedule.exceptions.DuplicateAppointmentException;

/**
 * A list of Appointments that enforces uniqueness among appointments to be added
 *
 * Supports a minimal set of list operations.
 */
public class Schedule implements Iterable<Appointment>, ReadOnlySchedule {
    // Data Fields
    private final ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> appointmentListUnModifiable =
            FXCollections.unmodifiableObservableList(appointmentList);

    public Schedule() {};

    /**
     * Creates an Schedule using the Appointments in the {@code toBeCopied}
     */
    public Schedule(ReadOnlySchedule toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * A factory method to create a deep copy of the given schedule to copy.
     * @param scheduleToCopy The given schedule to copy.
     * @return The deep copy.
     */
    public static Schedule deepCopy(Schedule scheduleToCopy) {
        Schedule result = new Schedule();
        for (Appointment appointment : scheduleToCopy.getSchedule()) {
            result.addAppointment(Appointment.deepCopy(appointment));
        }
        return result;
    }

    /**
     * Replaces the contents of the person list with {@code appointments}.
     * {@code appointments} must not contain duplicate persons.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointmentList.setAll(appointments);
    }

    /**
     * Resets the existing data of this {@code Schedule} with {@code newData}.
     */
    public void resetData(ReadOnlySchedule newData) {
        requireNonNull(newData);

        setAppointments(newData.getSchedule());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return appointmentListUnModifiable;
    }

    /**
     * Adds an Appointment to the list.
     * Appointment Must not already be in the list
     */
    public void addAppointment(Appointment toAdd) throws DuplicateAppointmentException, ClashingAppointmentsException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }

        List<Appointment> clashingAppointments = getClashingAppointments(toAdd);
        if (!clashingAppointments.isEmpty()) {
            throw new ClashingAppointmentsException(clashingAppointments);
        }

        appointmentList.add(toAdd);
        appointmentList.sort(Comparator.comparing(Appointment::getDescription));
        appointmentList.sort(Comparator.comparing(Appointment::getTimePeriod));
    }

    /**
     * Returns a list of appointments in the appointmentList that have time conflicts with
     * the given appointment.
     * @param appointmentToCheck The given appointment.
     * @return A list of appointments that have time conflicts with the given appointment.
     */
    private List<Appointment> getClashingAppointments(Appointment appointmentToCheck) {
        return this.appointmentList
                .stream()
                .parallel()
                .filter(appointment -> appointment.isConflictingWith(appointmentToCheck))
                .collect(Collectors.toList());
    }

    public void sortAppointmentByDescription() {
        appointmentList.sort(Comparator.comparing(Appointment::getDescription));
    }

    public void sortAppointmentByTimePeriod() {
        appointmentList.sort(Comparator.comparing(Appointment::getTimePeriod));
    }

    /**
     * Removes the equivalent Appointment from the list.
     * The Appointment must exist in the list.
     */
    public void deleteAppointment(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!appointmentList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    /**
     * Returns the list of {@Code Appointment} that is related to the {@Code Person}
     */
    public List<Appointment> getRelatedAppointments(Person client) {
        return appointmentListUnModifiable
                .stream()
                .parallel()
                .filter(appointment -> appointment.hasClient(client))
                .collect(Collectors.toList());
    }

    /**
     * Check if appointmentList contains {@code Appointment}
     * @return boolean result
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return appointmentList.contains(toCheck);
    }

    /**
     * Checks if the appointmentList contains a conflict {@code Appointment} with the {@code Appointment} to be checked.
     * @param toCheck The appointment to be checked for conflict.
     * @return true if there is a conflicting appointment, false if there is not.
     */
    public boolean hasConflictingAppointment(Appointment toCheck) {
        requireNonNull(toCheck);
        for (Appointment app : appointmentList) {
            if (app.isConflictingWith(toCheck)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Appointment> iterator() {
        return appointmentList.iterator();
    }

    @Override
    public int hashCode() {
        return appointmentList.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Schedule // instanceof handles nulls
                && appointmentList.equals(((Schedule) other).appointmentList));
    }

    @Override
    public ObservableList<Appointment> getSchedule() {
        return appointmentList;
    }

    public void setAppointment(Appointment appointmentToEdit, Appointment editedAppointment) {
        requireNonNull(appointmentToEdit);
        requireNonNull(editedAppointment);

        int index = appointmentList.indexOf(appointmentToEdit);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }

        appointmentList.set(index, editedAppointment);
    }
}
