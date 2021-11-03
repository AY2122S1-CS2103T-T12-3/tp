package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.ui.Ui;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {
    // default positive confirmation ui. This will not affect ListCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
        expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, uiStub, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        assertCommandSuccess(new ListCommand(), model, uiStub, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
