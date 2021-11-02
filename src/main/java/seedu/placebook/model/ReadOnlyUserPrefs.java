package seedu.placebook.model;

import java.nio.file.Path;

import seedu.placebook.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getContactsFilePath();

}
