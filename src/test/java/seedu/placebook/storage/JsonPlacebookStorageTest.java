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

public class JsonPlacebookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyContacts> readAddressBook(String filePath) throws Exception {
        return new JsonPlacebookStorage(Paths.get(filePath)).readContacts(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        Contacts original = getTypicalContacts();
        JsonPlacebookStorage jsonPlacebookStorage = new JsonPlacebookStorage(filePath);

        // Save in new file and read back
        jsonPlacebookStorage.saveContacts(original, filePath);
        ReadOnlyContacts readBack = jsonPlacebookStorage.readContacts(filePath).get();
        assertEquals(original, new Contacts(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(HOON);
        original.removePerson(ALICE);
        jsonPlacebookStorage.saveContacts(original, filePath);
        readBack = jsonPlacebookStorage.readContacts(filePath).get();
        assertEquals(original, new Contacts(readBack));

        // Save and read without specifying file path
        original.addPerson(IDA);
        jsonPlacebookStorage.saveContacts(original); // file path not specified
        readBack = jsonPlacebookStorage.readContacts().get(); // file path not specified
        assertEquals(original, new Contacts(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyContacts addressBook, String filePath) {
        try {
            new JsonPlacebookStorage(Paths.get(filePath))
                    .saveContacts(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new Contacts(), null));
    }
}
