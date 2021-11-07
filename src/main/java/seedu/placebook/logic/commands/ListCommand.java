package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.placebook.model.Model;
import seedu.placebook.ui.Ui;

/**
 * Lists all persons in the contacts to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateState(MESSAGE_SUCCESS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
