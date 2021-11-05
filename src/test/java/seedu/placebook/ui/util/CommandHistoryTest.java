package seedu.placebook.ui.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CommandHistoryTest {

    @Test
    void addCommand() {
        CommandHistory commandHistory = new CommandHistory();
        commandHistory.addCommand("command1");
        assertEquals("command1", commandHistory.getLastInput());
        // newly added command
        assertEquals("command1", commandHistory.getLastInput());
        // reach the end of the state
        assertEquals("", commandHistory.getNextInput());
        // no next input

        assertEquals("command1", commandHistory.getLastInput());
        // go to the previous command
        commandHistory.addCommand("command2");
        // adding new command will reset pointer
        assertEquals("command2", commandHistory.getLastInput());
        // newly added command
        assertEquals("command1", commandHistory.getLastInput());
        // last command
        assertEquals("command1", commandHistory.getLastInput());
        // reach the end of the state
        assertEquals("command2", commandHistory.getNextInput());
        // next input
        assertEquals("", commandHistory.getNextInput());
        // no next input
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
