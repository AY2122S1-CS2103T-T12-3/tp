package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.ui.Ui;

public class ClearCommandTest {
    // default positive confirmation ui. This will not affect clearCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    @Test
    public void execute_emptyContacts_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, uiStub, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyContacts_success() {
        Model model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
        Model expectedModel = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
        expectedModel.setContact(new Contacts());
        expectedModel.setSchedule(new Schedule());

        assertCommandSuccess(new ClearCommand(), model, uiStub, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
