package seedu.placebook.logic.commands;

import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;
import seedu.placebook.ui.Ui;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo command: %1$s";
    public static final String MESSAGE_FAILURE = "Cannot undo: No history states";

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        try {
            String commandName = model.getCommandName();
            model.undo();
            return new CommandResult(
                    String.format(MESSAGE_SUCCESS, commandName), false, false, true);
        } catch (NoHistoryStatesException e) {
            throw new CommandException(MESSAGE_FAILURE);
        }
    }
}
