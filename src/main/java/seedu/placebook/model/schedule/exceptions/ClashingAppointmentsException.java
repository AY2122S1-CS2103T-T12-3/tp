package seedu.placebook.model.schedule.exceptions;

import java.util.List;

import seedu.placebook.commons.util.StringUtil;
import seedu.placebook.model.schedule.Appointment;

/**
 * Signals that the operation will result in an invalid {@code TimePeriod}.
 * The endTime of the time period is before the startTime of the time period.
 */
public class ClashingAppointmentsException extends RuntimeException {
    public static final String MESSAGE_CLASHING_APPOINTMENTS = "Clashing Appointments found.";
    private final List<Appointment> appointments;

    /**
     * Constructs ClashingAppointmentsException.
     * @param appointments The list of appointments in the schedule that have time conflict
     *                      with the appointment to add.
     */
    public ClashingAppointmentsException(List<Appointment> appointments) {
        super(MESSAGE_CLASHING_APPOINTMENTS);
        this.appointments = appointments;
    }

    /**
     * Returns the clashing Appointments as String.
     * @return the String representation of the clashing Appointments.
     */
    public String getClashingAppointmentAsString() {
        return StringUtil.listToString(appointments, app -> app.getTimePeriod().toString(), "\n");
    }
}
