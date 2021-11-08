package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.ui.Ui;

/**
 * Clears the contacts.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Contacts has been cleared!";


    @Override
    public CommandResult execute(Model model, Ui ui) {
        requireNonNull(model);
        model.setContact(new Contacts());
        model.setSchedule(new Schedule());
        model.updateState(COMMAND_WORD);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
