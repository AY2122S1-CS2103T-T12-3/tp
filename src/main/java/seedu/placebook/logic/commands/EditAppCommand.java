package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_STARTDATETIME;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.commons.util.CollectionUtil;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.TimePeriod;
import seedu.placebook.model.schedule.exceptions.EndTimeBeforeStartTimeException;
import seedu.placebook.ui.Ui;

public class EditAppCommand extends Command {

    public static final String COMMAND_WORD = "editApp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the appointment identified "
            + "by the index number used in the displayed appointment list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_STARTDATETIME + "DATE (dd-MM-yyyy) TIME (HHmm) ] "
            + "[" + PREFIX_ENDDATETIME + "DATE (dd-MM-yyyy) TIME (HHmm) ] "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_ADDRESS + "Starbucks @ Raffles City "
            + PREFIX_STARTDATETIME + "14-12-2021 1600"
            + PREFIX_ENDDATETIME + "14-12-2021 1800"
            + PREFIX_DESCRIPTION + "discuss marketing strategies";

    public static final String MESSAGE_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_CLASHING_TIMINGS = "This appointment clashes with another appointment!";
    public static final String MESSAGE_END_BEFORE_START = "End time must be after Start time!";

    private final Index index;
    private final EditAppCommand.EditAppDescriptor editAppDescriptor;

    /**
     * @param index of the appointment in the filtered appointment list to edit
     * @param editAppDescriptor details to edit the appointment with
     */
    public EditAppCommand(Index index, EditAppCommand.EditAppDescriptor editAppDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppDescriptor);

        this.index = index;
        this.editAppDescriptor = new EditAppCommand.EditAppDescriptor(editAppDescriptor);
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException, EndTimeBeforeStartTimeException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppDescriptor);

        try {
            TimePeriod testTimePeriod = new TimePeriod(editedAppointment.getStart(), editedAppointment.getEnd());
        } catch (EndTimeBeforeStartTimeException e) {
            throw new CommandException(MESSAGE_END_BEFORE_START);
        }

        List<Appointment> clashingAppointmentsList = model.getClashingAppointments(editedAppointment);
        clashingAppointmentsList.remove(appointmentToEdit);

        // If clashing appointment list is not empty
        if (!clashingAppointmentsList.isEmpty()) {

            StringBuilder clashingAppointmentsString = new StringBuilder();
            for (Appointment app : clashingAppointmentsList) {
                clashingAppointmentsString.append(app + "\n");
            }

            throw new CommandException(MESSAGE_CLASHING_TIMINGS
                    + '\n' + clashingAppointmentsString);

        }

        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.updateState(String.format(MESSAGE_SUCCESS, editedAppointment));
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns an {@code Appointment} with the details of {@code appointmentToEdit}
     * edited with {@code editAppDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppDescriptor editAppDescriptor)
            throws CommandException {
        assert appointmentToEdit != null;

        UniquePersonList clients = appointmentToEdit.getClients();
        Address updatedLocation = editAppDescriptor.getLocation().orElse(appointmentToEdit.getLocation());
        LocalDateTime updatedStart = editAppDescriptor.getStart().orElse(appointmentToEdit.getStart());
        LocalDateTime updatedEnd = editAppDescriptor.getEnd().orElse(appointmentToEdit.getEnd());
        String updatedDescription = editAppDescriptor.getDescription().orElse(appointmentToEdit.getDescription());

        try {
            new TimePeriod(updatedStart, updatedEnd);
        } catch (EndTimeBeforeStartTimeException e) {
            throw new CommandException(MESSAGE_END_BEFORE_START);
        }

        TimePeriod updatedTimePeriod = new TimePeriod(updatedStart, updatedEnd);

        return new Appointment(clients, updatedLocation, updatedTimePeriod, updatedDescription);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppCommand)) {
            return false;
        }

        // state check
        EditAppCommand e = (EditAppCommand) other;
        return index.equals(e.index)
                && editAppDescriptor.equals(e.editAppDescriptor);
    }

    /**
     * Stores the details to edit the appointment with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAppDescriptor {
        private UniquePersonList clients;
        private Address location;
        private LocalDateTime start;
        private LocalDateTime end;
        private String description;

        public EditAppDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppDescriptor(EditAppCommand.EditAppDescriptor toCopy) {

            setClients(toCopy.clients);
            setLocation(toCopy.location);
            setStart(toCopy.start);
            setEnd(toCopy.end);
            setDescription(toCopy.description);

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil
                    .isAnyNonNull(location, start, end, description);
        }

        public void setClients(UniquePersonList clients) {
            this.clients = clients;
        }

        public UniquePersonList getClients() {
            return clients;
        }

        public void setLocation(Address location) {
            this.location = location;
        }

        public Optional<Address> getLocation() {
            return Optional.ofNullable(location);
        }

        public void setStart(LocalDateTime start) {
            this.start = start;
        }

        public Optional<LocalDateTime> getStart() {
            return Optional.ofNullable(start);
        }

        public void setEnd(LocalDateTime end) {
            this.end = end;
        }

        public Optional<LocalDateTime> getEnd() {
            return Optional.ofNullable(end);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }


        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppCommand.EditAppDescriptor)) {
                return false;
            }

            // state check
            EditAppCommand.EditAppDescriptor e = (EditAppCommand.EditAppDescriptor) other;

            return getClients().equals(e.getClients())
                    && getLocation().equals(e.getLocation())
                    && getStart().equals(e.getStart())
                    && getEnd().equals(e.getEnd())
                    && getDescription().equals(e.getDescription());
        }
    }

}
