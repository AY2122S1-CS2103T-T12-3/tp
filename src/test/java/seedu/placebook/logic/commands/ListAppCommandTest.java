package seedu.placebook.logic.commands;

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
        Contacts addressBook = TypicalPersons.getTypicalAddressBook();
        Schedule schedule = TypicalAppointment.getTypicalSchedule();
        model = new ModelManager(addressBook, new UserPrefs(), schedule);
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
}
