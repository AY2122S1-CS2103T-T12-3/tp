package seedu.placebook.logic.commands;

import seedu.placebook.model.Model;
import seedu.placebook.ui.Ui;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting Placebook as requested ...";

    @Override
    public CommandResult execute(Model model, Ui ui) {
        model.updateState(MESSAGE_EXIT_ACKNOWLEDGEMENT);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false);
    }

}
