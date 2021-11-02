package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.placebook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
        expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
