package seedu.placebook.logic.commands;

import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Model;
import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;
import seedu.placebook.ui.Ui;

public class UndoCommand extends Command {
    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_SUCCESS = "Undo success";

    @Override
    public CommandResult execute(Model model, Ui ui) throws CommandException {
        try {
            model.undo();
        } catch (NoHistoryStatesException e) {
            throw new CommandException("Cannot undo: No history states");
        }
        return new CommandResult(MESSAGE_SUCCESS, false, false, true);
    }
}
