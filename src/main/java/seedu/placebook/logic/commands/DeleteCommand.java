package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.person.Person;
import seedu.placebook.ui.UiManager;

/**
 * Deletes a person identified using it's displayed index from Placebook.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person: %1$s";

    public static final String MESSAGE_DELETE_PERSON_WARNING =
            "The following appointments related to the client will be deleted as well:\n";

    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        String relatedAppointment = MESSAGE_DELETE_PERSON_WARNING
                + model.getAppointmentsThatOnlyHaveThisClientAsString(personToDelete);

        if (UiManager.showDeleteDialogAndWait(relatedAppointment)) {
            model.deletePerson(personToDelete);
            model.removePersonFromAppointments(personToDelete);
            model.updateState();
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        } else {
            return new CommandResult("No person deleted.");
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteCommand) other).targetIndex)); // state check
    }
}
