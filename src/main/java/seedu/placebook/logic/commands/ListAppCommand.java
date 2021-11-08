package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.placebook.model.Model;
import seedu.placebook.ui.Ui;

/**
 * List all appointments in PlaceBook to the user.
 */
public class ListAppCommand extends Command {
    public static final String COMMAND_WORD = "listApp";

    public static final String MESSAGE_SUCCESS = "Listed all Appointments";

    private final String sortBy;

    public ListAppCommand(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.sortFilteredAppointmentList(sortBy);
        model.updateState(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListAppCommand // instanceof handles nulls
                && sortBy.equals(((ListAppCommand) other).sortBy)); // state check
    }
}
