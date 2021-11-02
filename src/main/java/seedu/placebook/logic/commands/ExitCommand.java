package seedu.placebook.logic.commands;

import seedu.placebook.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Placebook as requested ...";

    @Override
    public CommandResult execute(Model model) {
        model.updateState();
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
    }

}
