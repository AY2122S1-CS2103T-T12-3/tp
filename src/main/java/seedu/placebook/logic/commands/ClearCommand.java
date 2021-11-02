package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Placebook has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setContacts(new Contacts());
        model.updateState();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
