package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlySchedule;
import seedu.address.model.schedule.Appointment;
import seedu.address.model.schedule.Schedule;

/**
 * An Immutable Schedule that is serializable to JSON format.
 */
@JsonRootName(value = "schedule")
public class JsonSerializableSchedule {

    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "Schedule contains duplicate appointment(s).";

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableSchedule} with the given appointments.
     */
    @JsonCreator
    public JsonSerializableSchedule(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments.addAll(appointments);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableSchedule}.
     */
    public JsonSerializableSchedule(ReadOnlySchedule source) {
        appointments.addAll(source.getSchedule().stream().map(JsonAdaptedAppointment::new)
                    .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Schedule} object.
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
            schedule.addAppointment(appointment);
        }
        return schedule;
    }
}
