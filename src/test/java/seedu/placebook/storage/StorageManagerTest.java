package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.schedule.Schedule;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonContactsStorage contactsStorage = new JsonContactsStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        JsonScheduleStorage scheduleStorage = new JsonScheduleStorage(getTempFilePath("sched"));
        storageManager = new StorageManager(contactsStorage, userPrefsStorage, scheduleStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void contactsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonContactsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonContactsStorageTest} class.
         */
        Contacts original = getTypicalContacts();
        storageManager.saveContacts(original);
        ReadOnlyContacts retrieved = storageManager.readContacts().get();
        assertEquals(original, new Contacts(retrieved));
    }

    @Test
    public void getContactsFilePath() {
        assertNotNull(storageManager.getContactsFilePath());
    }

    @Test
    public void scheduleReadSave() throws Exception {
        Schedule original = getTypicalSchedule();
        storageManager.saveSchedule(original);
        ReadOnlySchedule retrieved = storageManager.readSchedule().get();
        assertEquals(original, new Schedule(retrieved));
    }

    @Test
    public void getStorageFilePath() {
        assertNotNull(storageManager.getScheduleFilePath());
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

}
