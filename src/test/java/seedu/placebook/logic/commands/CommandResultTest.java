package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandResultTest {
    @Test
    public void equals() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns true
        assertTrue(commandResult.equals(new CommandResult("feedback")));
        assertTrue(commandResult.equals(new CommandResult("feedback", false, false, false)));

        // same object -> returns true
        assertTrue(commandResult.equals(commandResult));

        // null -> returns false
        assertFalse(commandResult.equals(null));

        // different types -> returns false
        assertFalse(commandResult.equals(0.5f));

        // different feedbackToUser value -> returns false
        assertFalse(commandResult.equals(new CommandResult("different")));

        // different showHelp value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", true, false, false)));

        // different exit value -> returns false
        assertFalse(commandResult.equals(new CommandResult("feedback", false, true, false)));
    }

    @Test
    public void hashcode() {
        CommandResult commandResult = new CommandResult("feedback");

        // same values -> returns same hashcode
        assertEquals(commandResult.hashCode(), new CommandResult("feedback").hashCode());

        // different feedbackToUser value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("different").hashCode());

        // different showHelp value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", true, false, false).hashCode());

        // different exit value -> returns different hashcode
        assertNotEquals(commandResult.hashCode(), new CommandResult("feedback", false, true, false).hashCode());
    }

    @Test
    public void isShowHelp() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isShowHelp());

        CommandResult anotherCommandResult = new CommandResult("feedback", true, false, false);
        assertTrue(anotherCommandResult.isShowHelp());
    }

    @Test
    public void isExit() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isExit());

        CommandResult anotherCommandResult = new CommandResult("feedback", false, true, false);
        assertTrue(anotherCommandResult.isExit());
    }

    @Test
    public void isUndo() {
        CommandResult commandResult = new CommandResult("feedback");
        assertFalse(commandResult.isUndo());

        CommandResult anotherCommandResult = new CommandResult("feedback", false, false, true);
        assertTrue(anotherCommandResult.isUndo());
    }
}
