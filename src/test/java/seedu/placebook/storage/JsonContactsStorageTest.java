package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalPersons.ALICE;
import static seedu.placebook.testutil.TypicalPersons.HOON;
import static seedu.placebook.testutil.TypicalPersons.IDA;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.ReadOnlyContacts;

public class JsonContactsStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonPlaceBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readContacts_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readContacts(null));
    }

    private java.util.Optional<ReadOnlyContacts> readContacts(String filePath) throws Exception {
        return new JsonContactsStorage(Paths.get(filePath)).readContacts(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readContacts("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readContacts("notJsonFormatPlaceBook.json"));
    }

    @Test
    public void readContacts_invalidPersonContacts_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readContacts("invalidPersonPlaceBook.json"));
    }

    @Test
    public void readContacts_invalidAndValidPersonContacts_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readContacts("invalidAndValidPersonPlaceBook.json"));
    }

    @Test
    public void readAndSaveContacts_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempContacts.json");
        Contacts original = getTypicalContacts();
        JsonContactsStorage jsonContactsStorage = new JsonContactsStorage(filePath);

        // Save in new file and read back
        jsonContactsStorage.saveContacts(original, filePath);
        ReadOnlyContacts readBack = jsonContactsStorage.readContacts(filePath).get();
        assertEquals(original, new Contacts(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonContactsStorage.saveContacts(original, filePath);
        readBack = jsonContactsStorage.readContacts(filePath).get();
        assertEquals(original, new Contacts(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonContactsStorage.saveContacts(original); // file path not specified
        readBack = jsonContactsStorage.readContacts().get(); // file path not specified
        assertEquals(original, new Contacts(readBack));

    }

    @Test
    public void saveContacts_nullContacts_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContacts(null, "SomeFile.json"));
    }

    /**
     * Saves {@code contacts} at the specified {@code filePath}.
     */
    private void saveContacts(ReadOnlyContacts contacts, String filePath) {
        try {
            new JsonContactsStorage(Paths.get(filePath))
                    .saveContacts(contacts, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveContacts_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveContacts(new Contacts(), null));
    }
}
