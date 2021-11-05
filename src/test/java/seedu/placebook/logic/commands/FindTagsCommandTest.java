package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.commons.core.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.ALICE;
import static seedu.placebook.testutil.TypicalPersons.BENSON;
import static seedu.placebook.testutil.TypicalPersons.DANIEL;
import static seedu.placebook.testutil.TypicalPersons.GEORGE;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.PersonHasTagsPredicate;
import seedu.placebook.ui.Ui;

public class FindTagsCommandTest {
    // default positive confirmation ui. This will not affect FindTagsCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
    private Model expectedModel = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());

    @Test
    public void equals() {
        PersonHasTagsPredicate firstPredicate =
                new PersonHasTagsPredicate(Collections.singletonList("first"));
        PersonHasTagsPredicate secondPredicate =
                new PersonHasTagsPredicate(Collections.singletonList("second"));

        FindTagsCommand findFirstCommand = new FindTagsCommand(firstPredicate);
        FindTagsCommand findSecondCommand = new FindTagsCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindTagsCommand findFirstCommandCopy = new FindTagsCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonHasTagsPredicate predicate = preparePredicate(" ");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        PersonHasTagsPredicate predicate = preparePredicate("enemies");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonHasTagsPredicate predicate = preparePredicate("owesmoney");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON), model.getFilteredPersonList());
    }

    @Test
    public void execute_oneKeyword_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonHasTagsPredicate predicate = preparePredicate("friends");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_onePersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1);
        PersonHasTagsPredicate predicate = preparePredicate("family single");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(GEORGE), model.getFilteredPersonList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        PersonHasTagsPredicate predicate = preparePredicate("friends owesmoney");
        FindTagsCommand command = new FindTagsCommand(predicate);
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, BENSON, DANIEL), model.getFilteredPersonList());
    }

    /**
     * Parses {@code userInput} into a {@code PersonHasTagsPredicate}.
     */
    private PersonHasTagsPredicate preparePredicate(String userInput) {
        return new PersonHasTagsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
