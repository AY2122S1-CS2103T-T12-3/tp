package seedu.placebook.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.ReadOnlyContacts;

/**
 * Represents a storage for {@link Contacts}.
 */
public interface ContactsStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getContactsFilePath();

    /**
     * Returns Contacts data as a {@link ReadOnlyContacts}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyContacts> readContacts() throws DataConversionException, IOException;

    /**
     * @see #getContactsFilePath()
     */
    Optional<ReadOnlyContacts> readContacts(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyContacts} to the storage.
     * @param contacts cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveContacts(ReadOnlyContacts contacts) throws IOException;

    /**
     * @see #saveContacts(ReadOnlyContacts)
     */
    void saveContacts(ReadOnlyContacts contacts, Path filePath) throws IOException;

}
