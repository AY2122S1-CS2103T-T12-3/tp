package seedu.placebook.model.historystates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.LinkedList;

import org.junit.jupiter.api.Test;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.testutil.PersonBuilder;
import seedu.placebook.testutil.TypicalAppointment;
import seedu.placebook.testutil.TypicalPersons;

class HistoryStatesTest {
    private static Contacts testContacts = TypicalPersons.getTypicalContacts();
    private static Schedule testSchedule = TypicalAppointment.getTypicalSchedule();
    private static String testCommandName = "test command";

    private static State testState = new State(
            testContacts,
            testSchedule,
            PREDICATE_SHOW_ALL_PERSONS,
            PREDICATE_SHOW_ALL_APPOINTMENTS,
            testCommandName
    );

    @Test
    public void addNewState_success() {
        HistoryStates historyStates = new HistoryStates();
        LinkedList<State> listOfState = new LinkedList<>();
        listOfState.add(testState);
        historyStates.addNewState(testState);
        assertEquals(historyStates.getHistoryStates(), listOfState);
    }

    @Test
    public void undo_historyStatesNotEmpty_success() {
        HistoryStates historyStates = new HistoryStates();
        LinkedList<State> listOfState = new LinkedList<>();
        listOfState.add(testState);
        historyStates.addNewState(testState);
        historyStates.addNewState(new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        ));
        historyStates.undo();
        assertEquals(historyStates.getHistoryStates(), listOfState);
    }

    @Test
    public void undo_historyStatesIsEmptyCase1_exceptionThrown() {
        HistoryStates historyStates = new HistoryStates();
        assertThrows(NoHistoryStatesException.class, () -> historyStates.undo());
    }

    @Test
    public void undo_historyStatesIsEmptyCase2_exceptionThrown() {
        HistoryStates historyStates = new HistoryStates();
        historyStates.addNewState(testState);
        historyStates.undo();
        assertThrows(NoHistoryStatesException.class, () -> historyStates.undo());
    }

    @Test
    public void getCurrentState_returnsCorrectState() {
        HistoryStates historyStates = new HistoryStates();
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        historyStates.addNewState(state);
        assertEquals(historyStates.getCurrentState(), state);
    }

    @Test
    public void getCurrentState_modifyResult_stateInHistoryStatesNotAffected() {
        HistoryStates historyStates = new HistoryStates();
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        historyStates.addNewState(state);
        State result = historyStates.getCurrentState();
        result.getContacts().addPerson(new PersonBuilder().build());
        assertEquals(historyStates.getCurrentState(), state);
    }

    @Test
    public void getCurrentState_emptyHistoryStates_exceptionThrown() {
        HistoryStates historyStates = new HistoryStates();
        assertThrows(NoHistoryStatesException.class, () -> historyStates.getCurrentState());
    }

    @Test
    public void hasHistoryStates_emptyHistoryStates_returnsFalse() {
        HistoryStates historyStates = new HistoryStates();
        assertFalse(historyStates.hasHistoryStates());
    }

    @Test
    public void hasHistoryStates_historyStatesOnlyHasOneState_returnsFalse() {
        HistoryStates historyStates = new HistoryStates();
        historyStates.addNewState(testState);
        assertFalse(historyStates.hasHistoryStates());
    }

    @Test
    public void hasHistoryStates_historyStatesHasTwoStates_returnsTrue() {
        HistoryStates historyStates = new HistoryStates();
        historyStates.addNewState(testState);
        historyStates.addNewState(new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        ));
        assertTrue(historyStates.hasHistoryStates());
    }
}
