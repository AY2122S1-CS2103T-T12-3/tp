package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_INDEXES;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import java.time.LocalDateTime;
import java.util.List;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.person.exceptions.DuplicatePersonException;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.TimePeriod;
import seedu.placebook.model.schedule.exceptions.ClashingAppointmentsException;
import seedu.placebook.model.schedule.exceptions.DuplicateAppointmentException;
import seedu.placebook.model.schedule.exceptions.EndTimeBeforeStartTimeException;
import seedu.placebook.ui.Ui;

/**
 * Creates an appointment with an existing person in PlaceBook
 */
public class AddAppCommand extends Command {

    public static final String COMMAND_WORD = "addApp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an appointment to PlaceBook. "
            + "Parameters: "
            + PREFIX_INDEXES + "INDEX,INDEX "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_STARTDATETIME + "DATE (dd-MM-yyyy) TIME (HHmm) "
            + PREFIX_ENDDATETIME + "DATE (dd-MM-yyyy) TIME (HHmm) "
            + PREFIX_DESCRIPTION + "DESCRIPTION "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_INDEXES + "1,2,3 "
            + PREFIX_ADDRESS + "Starbucks @ Raffles City "
            + PREFIX_STARTDATETIME + "01-11-2021 1400 "
            + PREFIX_ENDDATETIME + "01-11-2021 1500 "
            + PREFIX_DESCRIPTION + "discuss marketing strategies";

    public static final String MESSAGE_SUCCESS = "New appointment added: %1$s";

    private final List<Index> indexes;
    private final Address location;
    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    private final String description;

    /**
     * Creates an AddAppCommand.
     * @param indexes The indexes of the person to be met during the appointment.
     * @param location The location of the appointment.
     * @param startDateTime The start date and time of the appointment.
     * @param endDateTime The end date and time of the appointment.
     * @param description The description of the appointment.
     */
    public AddAppCommand(List<Index> indexes, Address location, LocalDateTime startDateTime,
                         LocalDateTime endDateTime, String description) {
        requireNonNull(indexes);
        requireNonNull(location);
        requireNonNull(startDateTime);
        requireNonNull(endDateTime);
        requireNonNull(description);

        this.indexes = indexes;
        this.location = location;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Appointment> lastShownAppList = model.getFilteredAppointmentList();
        UniquePersonList clients = new UniquePersonList();

        for (Index index : indexes) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            Person client = lastShownList.get(index.getZeroBased());
            try {
                clients.add(client);
            } catch (DuplicatePersonException e) {
                throw new CommandException(Messages.MESSAGE_APPOINTMENTS_DUPLICATE_PERSON_ADDED);
            }
        }
        Appointment appointmentToAdd;

        try {
            TimePeriod timePeriod = new TimePeriod(this.startDateTime, endDateTime);
            appointmentToAdd = new Appointment(clients, location, timePeriod, description);
            model.addAppointment(appointmentToAdd);
        } catch (ClashingAppointmentsException e) {
            String clashingAppointments = e.getClashingAppointmentAsString();
            throw new CommandException(Messages.MESSAGE_APPOINTMENTS_CLASHING_APPOINTMENT_ADDED + clashingAppointments);
        } catch (DuplicateAppointmentException e) {
            throw new CommandException(Messages.MESSAGE_APPOINTMENTS_DUPLICATE_APPOINTMENT_ADDED);
        } catch (EndTimeBeforeStartTimeException e) {
            throw new CommandException(Messages.MESSAGE_APPOINTMENTS_END_TIME_BEFORE_START_TIME);
        }

        model.updateState(String.format(MESSAGE_SUCCESS, appointmentToAdd));
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentToAdd));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAppCommand)) {
            return false;
        }

        AddAppCommand aa = (AddAppCommand) other;
        return this.indexes.equals(aa.indexes)
                && this.location.equals(aa.location)
                && this.startDateTime.equals(aa.startDateTime)
                && this.endDateTime.equals(aa.endDateTime)
                && this.description.equals(aa.description);
    }
}
