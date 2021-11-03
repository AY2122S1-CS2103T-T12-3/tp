package seedu.placebook.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.commons.exceptions.DataConversionException;
import seedu.placebook.commons.exceptions.IllegalValueException;
import seedu.placebook.commons.util.FileUtil;
import seedu.placebook.commons.util.JsonUtil;
import seedu.placebook.model.ReadOnlyContacts;

/**
 * A class to access Contact data stored as a json file on the hard disk.
 */
public class JsonContactsStorage implements ContactsStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonContactsStorage.class);

    private Path filePath;

    public JsonContactsStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getContactsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyContacts> readContacts() throws DataConversionException {
        return readContacts(filePath);
    }

    /**
     * Similar to {@link #readContacts()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyContacts> readContacts(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableContacts> jsonContacts = JsonUtil.readJsonFile(
                filePath, JsonSerializableContacts.class);
        if (!jsonContacts.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonContacts.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveContacts(ReadOnlyContacts contacts) throws IOException {
        saveContacts(contacts, filePath);
    }

    /**
     * Similar to {@link #saveContacts(ReadOnlyContacts)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveContacts(ReadOnlyContacts contacts, Path filePath) throws IOException {
        requireNonNull(contacts);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableContacts(contacts), filePath);
    }

}
