package seedu.placebook.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX =
            "The appointment index provided is invalid";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointments listed!";
    public static final String MESSAGE_APPOINTMENTS_DUPLICATE_PERSON_ADDED = "Duplicated Person not allowed!";
    public static final String MESSAGE_APPOINTMENTS_DUPLICATE_APPOINTMENT_ADDED = "Clashing appointment Timings";
    public static final String MESSAGE_END_TIME_BEFORE_START_TIME = "The end time should be after the start time";
}
