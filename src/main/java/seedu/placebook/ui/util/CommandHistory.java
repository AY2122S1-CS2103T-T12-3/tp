package seedu.placebook.ui.util;

import java.util.ArrayList;
import java.util.List;

public class CommandHistory {

    private final List<String> history;
    private int historyIndex;

    /**
     * Constructs the CommandHistory Class
     */
    public CommandHistory() {
        this.history = new ArrayList<>();
        this.historyIndex = 0;
    }

    /**
     * Adds the command to the history
     */
    public void addCommand(String commandString) {
        history.add(commandString);
        historyIndex = history.size();
    }

    /**
     * Gets the last user input in the history of this instance.
     * return the first input if there is no more last history.
     *
     * @return a string of user input history
     */
    public String getLastInput() {
        try {
            historyIndex = Math.max(historyIndex - 1, 0);
            return history.get(historyIndex);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }

    /**
     * Gets the next user input in the history of this instance.
     * return an empty string if there is no more next history.
     *
     * @return a string of user input history
     */
    public String getNextInput() {
        try {
            historyIndex = Math.min(historyIndex + 1, history.size());
            return history.get(historyIndex);
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
