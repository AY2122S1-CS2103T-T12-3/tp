package seedu.placebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.ReadOnlyUserPrefs;
import seedu.placebook.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends ContactsStorage, UserPrefsStorage, ScheduleStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getContactsFilePath();

    @Override
    Optional<ReadOnlyContacts> readContacts() throws DataConversionException, IOException;

    @Override
    void saveContacts(ReadOnlyContacts contacts) throws IOException;

    @Override
    Path getScheduleFilePath();

    @Override
    Optional<ReadOnlySchedule> readSchedule() throws DataConversionException, IOException;

    @Override
    void saveSchedule(ReadOnlySchedule schedule) throws IOException;
}
