package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
        expectedModel.setContacts(new Contacts());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
