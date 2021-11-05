package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.testutil.TypicalAppointment;
import seedu.placebook.testutil.TypicalPersons;
import seedu.placebook.ui.Ui;

class ListAppCommandTest {
    // default positive confirmation ui. This will not affect ListAppCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        Contacts contacts = TypicalPersons.getTypicalContacts();
        Schedule schedule = TypicalAppointment.getTypicalSchedule();
        model = new ModelManager(contacts, new UserPrefs(), schedule);
        expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListAppCommand(""), model, uiStub, ListAppCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        model.updateFilteredAppointmentList(x -> false);
        assertCommandSuccess(new ListAppCommand(""), model, uiStub, ListAppCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_time_sortsListByTime() {
        model.updateFilteredAppointmentList(x -> false);
        expectedModel.sortFilteredAppointmentList("Time");
        assertCommandSuccess(new ListAppCommand("Time"), model, uiStub, ListAppCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_description_sortsListByDescription() {
        model.updateFilteredAppointmentList(x -> false);
        expectedModel.sortFilteredAppointmentList("Description");
        assertCommandSuccess(new ListAppCommand("Description"),
                model, uiStub, ListAppCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void equals() {
        ListAppCommand emptyListAppCommand = new ListAppCommand("");
        ListAppCommand anotherEmptyListAppCommand = new ListAppCommand("");
        ListAppCommand timeListAppCommand = new ListAppCommand("Time");

        assertEquals(emptyListAppCommand, emptyListAppCommand);
        assertEquals(emptyListAppCommand, anotherEmptyListAppCommand);
        assertFalse(emptyListAppCommand.equals(timeListAppCommand));

    }
}
