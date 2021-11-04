package seedu.placebook.model.historystates;

import java.util.LinkedList;
import java.util.NoSuchElementException;

import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;

public class HistoryStates {
    /** The data structure to store the {@Code State}. */
    private final LinkedList<State> historyStates;

    /**
     * Initialized a {@Code HistoryStates}.
     */
    public HistoryStates() {
        this.historyStates = new LinkedList<>();
    }

    /**
     * Add a given {@Code State} to this {@Code HistoryStates}.
     * @param state The given state to add.
     */
    public void addNewState(State state) {
        this.historyStates.addLast(state);
    }

    /**
     * Remove the last {@Code State} in the {@Code HistoryStates} and go back to the previous {@Code State}.
     * Exception will be thrown if this {@Code HistoryStates} does not contain any {@Code State}.
     * @throws NoHistoryStatesException Exception will be thrown if
     * this {@Code HistoryStates} does not contain any {@Code State}.
     */
    public void undo() throws NoHistoryStatesException {
        try {
            this.historyStates.removeLast();
        } catch (NoSuchElementException e) {
            throw new NoHistoryStatesException();
        }
    }

    /**
     * Get the last {@Code State} in the {@Code HistoryStates}. Exception will be thrown if
     * this {@Code HistoryStates} does not contain any {@Code State}.
     * @return The last {@Code State} in the {@Code HistoryStates}.
     * @throws NoHistoryStatesException Exception will be thrown if
     * this {@Code HistoryStates} does not contain any {@Code State}.
     */
    public State getCurrentState() throws NoHistoryStatesException {
        try {
            return this.historyStates.getLast();
        } catch (NoSuchElementException e) {
            throw new NoHistoryStatesException();
        }
    }

    /**
     * Check whether there are at least one previous {@Code State} in the {@Code HistoryStates}.
     * @return A boolean value, indicating whether there are {@Code State} in the {@Code HistoryStates}.
     */
    public Boolean hasHistoryStates() {
        return this.historyStates.size() > 1;
    }

    public LinkedList<State> getHistoryStates() {
        return new LinkedList<>(this.historyStates);
    }
}
