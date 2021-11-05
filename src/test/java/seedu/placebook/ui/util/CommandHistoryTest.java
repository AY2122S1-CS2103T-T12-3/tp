package seedu.placebook.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommandHistoryTest {

    @Test
    void addCommand() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.addCommand("command1");
        // newly added command
        assertEquals("command1", commandHistory.getLastInput());
        // reach the end of the state
        assertEquals("command1", commandHistory.getLastInput());
        // no next input
        assertEquals("", commandHistory.getNextInput());
        // no next input when go beyond the limit
        assertEquals("", commandHistory.getNextInput());
        // newly added command when go back
        assertEquals("command1", commandHistory.getLastInput());

        // go to the previous command
        assertEquals("command1", commandHistory.getLastInput());
        // adding new command will reset pointer
        commandHistory.addCommand("command2");
        // newly added command
        assertEquals("command2", commandHistory.getLastInput());
        // last command
        assertEquals("command1", commandHistory.getLastInput());
        // reach the end of the state
        assertEquals("command1", commandHistory.getLastInput());
        // next input
        assertEquals("command2", commandHistory.getNextInput());
        // no next input
        assertEquals("", commandHistory.getNextInput());
        // no next input when go beyond the limit
        assertEquals("", commandHistory.getNextInput());
        // newly added command when go back
        assertEquals("command2", commandHistory.getLastInput());
    }

    @Test
    void getLastInput_emptyState_emptyStringReturned() {
        CommandHistory commandHistory = new CommandHistory();
        assertEquals("", commandHistory.getLastInput());
        assertEquals("", commandHistory.getLastInput());
    }

    @Test
    void getNextInput_emptyState_emptyStringReturned() {
        CommandHistory commandHistory = new CommandHistory();
        assertEquals("", commandHistory.getNextInput());
        assertEquals("", commandHistory.getNextInput());
    }
}
