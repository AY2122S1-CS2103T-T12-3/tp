package seedu.placebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.ReadOnlyUserPrefs;
import seedu.placebook.model.UserPrefs;

/**
 * Manages storage of Placebook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ContactsStorage contactsStorage;
    private UserPrefsStorage userPrefsStorage;
    private ScheduleStorage scheduleStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code ContactsStorage},
     * {@code UserPrefStorage} and {@code ScheduleStorage}
     * and {@code ScheduleStorage}.
     */
    public StorageManager(ContactsStorage contactsStorage, UserPrefsStorage userPrefsStorage,
                          ScheduleStorage scheduleStorage) {
        super();
        this.contactsStorage = contactsStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.scheduleStorage = scheduleStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Contacts methods ==============================

    @Override
    public Path getContactsFilePath() {
        return contactsStorage.getContactsFilePath();
    }

    @Override
    public Optional<ReadOnlyContacts> readContacts() throws DataConversionException, IOException {
        return readContacts(contactsStorage.getContactsFilePath());
    }

    @Override
    public Optional<ReadOnlyContacts> readContacts(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return contactsStorage.readContacts(filePath);
    }

    @Override
    public void saveContacts(ReadOnlyContacts contacts) throws IOException {
        saveContacts(contacts, contactsStorage.getContactsFilePath());
    }

    @Override
    public void saveContacts(ReadOnlyContacts contacts, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        contactsStorage.saveContacts(contacts, filePath);
    }

    // ================ Schedule methods ==============================

    @Override
    public Path getScheduleFilePath() {
        return scheduleStorage.getScheduleFilePath();
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException {
        return readSchedule(scheduleStorage.getScheduleFilePath());
    }

    @Override
    public Optional<ReadOnlySchedule> readSchedule(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return scheduleStorage.readSchedule(filePath);
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule) throws IOException {
        saveSchedule(schedule, scheduleStorage.getScheduleFilePath());
    }

    @Override
    public void saveSchedule(ReadOnlySchedule schedule, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        scheduleStorage.saveSchedule(schedule, filePath);
    }
}
