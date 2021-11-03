package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.placebook.model.AddressBook;
import seedu.placebook.model.Model;
import seedu.placebook.model.schedule.Schedule;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new AddressBook());
        model.setSchedule(new Schedule());
        model.updateState();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
