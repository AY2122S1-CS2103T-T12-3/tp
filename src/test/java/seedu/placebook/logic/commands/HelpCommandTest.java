package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.ui.Ui;

public class HelpCommandTest {
    // default positive confirmation ui. This will not affect HelpCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false, false);
        assertCommandSuccess(new HelpCommand(), model, uiStub, expectedCommandResult, expectedModel);
    }
}
