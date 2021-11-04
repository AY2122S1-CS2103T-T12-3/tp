package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.CARL_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.DescriptionContainsKeywordsPredicate;
import seedu.placebook.ui.Ui;


/**
 * Contains integration tests (interaction with the Model) for {@code FindAppCommand}.
 * The testcases are similar to those in {@code FindCommand}.
 */
public class FindAppCommandTest {
    // default positive confirmation ui. This will not affect FindCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());
    private Model expectedModel = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());

    @Test
    public void equals() {
        DescriptionContainsKeywordsPredicate firstPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("first"));
        DescriptionContainsKeywordsPredicate secondPredicate =
                new DescriptionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindAppCommand findFirstCommand = new FindAppCommand(firstPredicate);
        FindAppCommand findSecondCommand = new FindAppCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindAppCommand findFirstCommandCopy = new FindAppCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different appointment -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 0);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindAppCommand command = new FindAppCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_oneKeyword_multipleAppointmentsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("sales");
        FindAppCommand command = new FindAppCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_APPOINTMENT, CARL_APPOINTMENT), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_oneKeyword_oneAppointmentFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 1);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("talk");
        FindAppCommand command = new FindAppCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_APPOINTMENT), model.getFilteredAppointmentList());
    }

    @Test
    public void execute_multipleKeywords_multipleAppointmentsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2);
        DescriptionContainsKeywordsPredicate predicate = preparePredicate("talk sales");
        FindAppCommand command = new FindAppCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, uiStub, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE_APPOINTMENT, CARL_APPOINTMENT), model.getFilteredAppointmentList());
    }

    /**
     * Parses {@code userInput} into a {@code NameContainsKeywordsPredicate}.
     */
    private DescriptionContainsKeywordsPredicate preparePredicate(String userInput) {
        return new DescriptionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
