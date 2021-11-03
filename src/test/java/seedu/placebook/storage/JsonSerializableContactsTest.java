package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.placebook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.placebook.commons.exceptions.IllegalValueException;
import seedu.placebook.commons.util.JsonUtil;
import seedu.placebook.model.Contacts;
import seedu.placebook.testutil.TypicalPersons;

public class JsonSerializableContactsTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableContactsTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsContacts.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonContacts.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonContacts.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableContacts dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableContacts.class).get();
        Contacts contactsFromFile = dataFromFile.toModelType();
        Contacts typicalContacts = TypicalPersons.getTypicalContacts();
        assertEquals(contactsFromFile, typicalContacts);
    }

    @Test
    public void toModelType_invalidPersonFile_throwsIllegalValueException() throws Exception {
        JsonSerializableContacts dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableContacts.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableContacts dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableContacts.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableContacts.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

}
