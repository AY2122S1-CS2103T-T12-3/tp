package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.schedule.Schedule;

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
        model.setSchedule(new Schedule());
        model.updateState();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
