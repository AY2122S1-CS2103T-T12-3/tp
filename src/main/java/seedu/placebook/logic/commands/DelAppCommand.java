package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.ui.Ui;

/**
 * Deletes an existing appointment in the schedule
 */
public class DelAppCommand extends Command {

    public static final String COMMAND_WORD = "delApp";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes an existing appointment from PlaceBook. "
            + "Parameters: "
            + "INDEX "
            + "Example: " + COMMAND_WORD + " "
            + "4";

    public static final String MESSAGE_SUCCESS = "Appointment deleted: %1$s";

    private final Index index;

    /**
     * Creates an DelAppCommand
     * @param index The index of the appointment to be deleted
     */
    public DelAppCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToDelete = lastShownList.get(index.getZeroBased());
        String deleteWarning = "You are about to delete:\n" + appointmentToDelete;

        if (ui.showDeleteDialogAndWait(deleteWarning)) {
            model.deleteAppointment(appointmentToDelete);
            model.updateState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, appointmentToDelete));
        } else {
            return new CommandResult("No appointment deleted.");
        }
    }


}
