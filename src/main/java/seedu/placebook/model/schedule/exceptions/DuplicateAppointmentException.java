package seedu.placebook.model.schedule.exceptions;

/**
 * Signals that the operation will result in duplicate Appointments (Appointments are considered duplicates
 * if they have the same identity).
 */
public class DuplicateAppointmentException extends RuntimeException {
    public DuplicateAppointmentException() {
        super("Operation would result in duplicate Appointments");
    }
}
