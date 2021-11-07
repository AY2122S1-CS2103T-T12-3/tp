package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_STARTDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.placebook.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.person.NameContainsKeywordsPredicate;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.DescriptionContainsKeywordsPredicate;
import seedu.placebook.model.schedule.TimePeriod;
import seedu.placebook.testutil.EditAppDescriptorBuilder;
import seedu.placebook.testutil.EditPersonDescriptorBuilder;
import seedu.placebook.ui.Ui;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final LocalDate VALID_DATE = LocalDate.parse("12-12-2021",
            DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    public static final LocalTime VALID_TIME = LocalTime.parse("1400", DateTimeFormatter.ofPattern("HHmm"));
    public static final TimePeriod VALID_TIME_PERIOD =
            new TimePeriod(LocalDateTime.of(2021, 12, 14, 10, 00),
                    LocalDateTime.of(2021, 12, 14, 11, 00));

    public static final String VALID_LOCATION_A = "Starbucks @ Vivocity";
    public static final String VALID_START_A = "14-12-2021 1000";
    public static final String VALID_END_A = "14-12-2021 1100";
    public static final String VALID_DESCRIPTION_A = "Project meeting";

    public static final String VALID_LOCATION_B = "GYG @ Star Vista";
    public static final String VALID_START_B = "14-12-2021 1200";
    public static final String VALID_END_B = "14-12-2021 1300";
    public static final String VALID_DESCRIPTION_B = "Company lunch";

    public static final String LOCATION_DESC_A = " " + PREFIX_ADDRESS + VALID_LOCATION_A;
    public static final String START_DESC_A = " " + PREFIX_STARTDATETIME + VALID_START_A;
    public static final String END_DESC_A = " " + PREFIX_ENDDATETIME + VALID_END_A;
    public static final String DESCRIPTION_DESC_A = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_A;

    public static final String LOCATION_DESC_B = " " + PREFIX_ADDRESS + VALID_LOCATION_B;
    public static final String START_DESC_B = " " + PREFIX_STARTDATETIME + VALID_START_B;
    public static final String END_DESC_B = " " + PREFIX_ENDDATETIME + VALID_END_B;
    public static final String DESCRIPTION_DESC_B = " " + PREFIX_DESCRIPTION + VALID_DESCRIPTION_B;

    public static final String INVALID_LOCATION_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_START_DESC = " " + PREFIX_STARTDATETIME
            + "01-01-2021 2500"; // HHmm is out of range
    public static final String INVALID_END_DESC = " " + PREFIX_ENDDATETIME
            + "01/01/2022 1400"; // separators should be hyphens
    public static final String INVALID_DESCRIPTION_DESC = " "
            + PREFIX_DESCRIPTION; // empty string not allowed for description

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;
    public static final EditAppCommand.EditAppDescriptor DESC_A;
    public static final EditAppCommand.EditAppDescriptor DESC_B;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_A = new EditAppDescriptorBuilder()
                .withLocation(VALID_LOCATION_A).withStart(VALID_START_A)
                .withEnd(VALID_END_A).withDescription(VALID_DESCRIPTION_A).build();
        DESC_B = new EditAppDescriptorBuilder()
                .withLocation(VALID_LOCATION_B).withStart(VALID_START_B)
                .withEnd(VALID_END_B).withDescription(VALID_DESCRIPTION_B).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, Ui uiStub,
                                            CommandResult expectedCommandResult, Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel, uiStub);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, Ui, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, Ui uiStub, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, uiStub, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the schedule, contacts, filtered person list filtered appointment list
     * and selected person in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, Ui uiStub, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        Contacts contacts = new Contacts(actualModel.getContacts());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel, uiStub));
        assertEquals(contacts, actualModel.getContacts());
        assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s contacts.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the appointment at the given {@code targetIndex} in the
     * {@code model}'s schedule.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAppointmentList().size());

        Appointment appointment = model.getFilteredAppointmentList().get(targetIndex.getZeroBased());
        final String[] splitDesc = appointment.getDescription().split("\\s+");
        model.updateFilteredAppointmentList(new DescriptionContainsKeywordsPredicate(Arrays.asList(splitDesc[0])));

        assertEquals(1, model.getFilteredAppointmentList().size());
    }
}
