package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_CARL_APPOINTMENT;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.schedule.Schedule;

public class JsonScheduleStorageTest {
    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonScheduleStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readSchedule_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readSchedule(null));
    }

    private java.util.Optional<ReadOnlySchedule> readSchedule(String filePath) throws Exception {
        return new JsonScheduleStorage(Paths.get(filePath)).readSchedule(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readSchedule("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readSchedule("notJsonFormatSchedule.json"));
    }

    @Test
    public void readSchedule_invalidAppointmentSchedule_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSchedule("invalidAppointmentSchedule.json"));
    }

    @Test
    public void readSchedule_invalidAndValidAppointmentSchedule_throwDataConversionException() {
        assertThrows(DataConversionException.class, ()
            -> readSchedule("invalidAndValidAppointmentSchedule.json"));
    }

    @Test
    public void readAndSaveSchedule_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempContacts.json");
        Schedule original = getTypicalSchedule();
        JsonScheduleStorage jsonScheduleStorage = new JsonScheduleStorage(filePath);

        // Save in new file and read back
        jsonScheduleStorage.saveSchedule(original, filePath);
        ReadOnlySchedule readBack = jsonScheduleStorage.readSchedule(filePath).get();
        assertEquals(original, new Schedule(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAppointment(ALICE_CARL_APPOINTMENT);
        original.deleteAppointment(ALICE_APPOINTMENT);
        jsonScheduleStorage.saveSchedule(original, filePath);
        readBack = jsonScheduleStorage.readSchedule(filePath).get();
        assertEquals(original, new Schedule(readBack));

        // Save and read without specifying file path
        original.addAppointment(ALICE_APPOINTMENT);
        jsonScheduleStorage.saveSchedule(original); // file path not specified
        readBack = jsonScheduleStorage.readSchedule().get(); // file path not specified
        assertEquals(original, new Schedule(readBack));

    }

    @Test
    public void saveSchedule_nullSchedule_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSchedule(null, "SomeFile.json"));
    }

    /**
     * Saves {@code contacts} at the specified {@code filePath}.
     */
    private void saveSchedule(ReadOnlySchedule schedule, String filePath) {
        try {
            new JsonScheduleStorage(Paths.get(filePath))
                    .saveSchedule(schedule, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveSchedule_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveSchedule(new Schedule(), null));
    }
}
