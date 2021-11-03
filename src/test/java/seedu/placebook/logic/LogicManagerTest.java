package seedu.placebook.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.placebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.placebook.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.placebook.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.placebook.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.placebook.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalPersons.AMY;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.placebook.logic.commands.AddCommand;
import seedu.placebook.logic.commands.CommandResult;
import seedu.placebook.logic.commands.ListCommand;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.Person;
import seedu.placebook.storage.JsonContactsStorage;
import seedu.placebook.storage.JsonScheduleStorage;
import seedu.placebook.storage.JsonUserPrefsStorage;
import seedu.placebook.storage.StorageManager;
import seedu.placebook.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonContactsStorage addressBookStorage =
                new JsonContactsStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        JsonScheduleStorage scheduleStorage = new JsonScheduleStorage(temporaryFolder.resolve("schedule.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, scheduleStorage);
        logic = new LogicManager(model, storage);
        logic.setUi(UiStubFactory.getUiStub(true));
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonAddressBookIoExceptionThrowingStub
        JsonContactsStorage addressBookStorage =
                new JsonContactsIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionAddressBook.json"));
        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        JsonScheduleStorage scheduleStorage =
                new JsonScheduleStorage(temporaryFolder.resolve("ioExceptionSchedule.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage, scheduleStorage);
        logic = new LogicManager(model, storage);
        logic.setUi(UiStubFactory.getUiStub(true));

        // Execute add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addPerson(expectedPerson);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
            Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage) {
        Model expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
            String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonContactsIoExceptionThrowingStub extends JsonContactsStorage {
        private JsonContactsIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveContacts(ReadOnlyContacts contacts, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
