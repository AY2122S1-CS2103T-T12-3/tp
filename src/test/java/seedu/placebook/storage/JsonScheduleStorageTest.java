package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.placebook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.ReadOnlySchedule;

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
    public void readSchedule_invalidPersonContacts_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSchedule("invalidPersonSchedule.json"));
    }

    @Test
    public void readSchedule_invalidAndValidPersonContacts_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readSchedule("invalidAndValidPersonSchedule.json"));
    }
}
