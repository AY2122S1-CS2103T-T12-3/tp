package seedu.placebook.model.historystates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.placebook.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.testutil.TypicalAppointment;
import seedu.placebook.testutil.TypicalPersons;

class StateTest {
    private static Contacts testContacts = TypicalPersons.getTypicalContacts();
    private static Schedule testSchedule = TypicalAppointment.getTypicalSchedule();
    private static String testCommandName = "test command";
    @BeforeEach
    public void setUp() {
        testContacts = TypicalPersons.getTypicalContacts();
        testSchedule = TypicalAppointment.getTypicalSchedule();
    }

    @Test
    public void getContacts_returnsCorrectContacts() {
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        assertEquals(state.getContacts(), testContacts);
    }

    @Test
    public void getContacts_modifyResult_contactsOfStateNotAffected() {
        Contacts contacts = TypicalPersons.getTypicalContacts();
        State state = new State(
                contacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        Contacts result = state.getContacts();
        result.removePerson(result.getPersonList().get(0));
        assertEquals(state.getContacts(), contacts);
        assertNotEquals(state.getContacts(), result);
    }

    @Test
    public void getSchedule_returnsCorrectSchedule() {
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        assertEquals(state.getSchedule(), testSchedule);
    }

    @Test
    public void getSchedule_modifyResult_scheduleOfStateNotAffected() {
        Schedule schedule = TypicalAppointment.getTypicalSchedule();
        State state = new State(
                testContacts,
                schedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        Schedule result = state.getSchedule();
        result.deleteAppointment(result.getSchedule().get(0));
        assertEquals(state.getSchedule(), schedule);
        assertNotEquals(state.getContacts(), result);
    }

    @Test
    public void getPersonListPredicate_returnsCorrectPredicate() {
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        assertEquals(state.getPersonListPredicate(), PREDICATE_SHOW_ALL_PERSONS);
    }

    @Test
    public void getAppointmentListPredicate_returnsCorrectPredicate() {
        State state = new State(
                testContacts,
                testSchedule,
                PREDICATE_SHOW_ALL_PERSONS,
                PREDICATE_SHOW_ALL_APPOINTMENTS,
                testCommandName
        );
        assertEquals(state.getAppointmentListPredicate(), PREDICATE_SHOW_ALL_APPOINTMENTS);
    }
}
