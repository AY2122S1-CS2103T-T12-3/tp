package seedu.placebook.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.logic.commands.CommandResult;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.ui.Ui;

/**
 * API of the Logic component
 */
public interface Logic {
    void setUi(Ui ui);

    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns contacts.
     *
     * @see seedu.placebook.model.Model#getContacts()
     */
    ReadOnlyContacts getContacts();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of appointments */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Returns the user prefs' contacts file path.
     */
    Path getContactsFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
