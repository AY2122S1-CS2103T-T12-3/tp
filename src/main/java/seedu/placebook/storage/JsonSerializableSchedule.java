package seedu.placebook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.placebook.commons.exceptions.IllegalValueException;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;

/**
 * An Immutable Schedule that is serializable to JSON format.
 */
@JsonRootName(value = "schedule")
public class JsonSerializableSchedule {

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Schedule contains duplicate appointment(s).";
    public static final String MESSAGE_CONFLICTING_APPOINTMENT = "Schedule contains conflicting appointments.";

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSchedule} with the given appointments.
     */
    @JsonCreator
    public JsonSerializableSchedule(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments.addAll(appointments);
    }

    /**
     * Converts a given {@code ReadOnlySchedule} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSchedule}.
     */
    public JsonSerializableSchedule(ReadOnlySchedule source) {
        appointments.addAll(source.getSchedule().stream().map(JsonAdaptedAppointment::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this Schedule into the model's {@code Schedule} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Schedule toModelType() throws IllegalValueException {
        Schedule schedule = new Schedule();
        for (JsonAdaptedAppointment jsonAdaptedSchedule : appointments) {
            Appointment appointment = jsonAdaptedSchedule.toModelType();
            if (schedule.contains(appointment)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_APPOINTMENT);
            }

            if (schedule.hasConflictingAppointment(appointment)) {
                throw new IllegalValueException(MESSAGE_CONFLICTING_APPOINTMENT);
            }
            schedule.addAppointment(appointment);
        }
        return schedule;
    }
}
