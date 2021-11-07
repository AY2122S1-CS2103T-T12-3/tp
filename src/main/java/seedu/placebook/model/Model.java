package seedu.placebook.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /** {@code Predicate} that always evaluate to true */
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' contacts file path.
     */
    Path getContactsFilePath();

    /**
     * Sets the user prefs' contacts file path.
     */
    void setContactsFilePath(Path contactsFilePath);

    /**
     * Replaces PlaceBook data with the data in {@code contacts}.
     */
    void setContact(ReadOnlyContacts contacts);

    /** Returns contacts */
    ReadOnlyContacts getContacts();

    /**
     * Replaces PlaceBook schedule data with the data in {@code schedule}.
     */
    void setSchedule(ReadOnlySchedule schedule);

    /** Returns the Schedule */
    ReadOnlySchedule getSchedule();

    /**
     * Returns true if a person with the same identity as {@code person} exists in PlaceBook.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in PlaceBook.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in PlaceBook.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in contacts.
     * The person identity of {@code editedPerson} must not be the same as another existing person in PlaceBook.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Adds the given Appointment
     */
    void addAppointment(Appointment a);

    /**
     * Removes the given Appointment
     */
    void deleteAppointment(Appointment a);

    /** Returns an unmodifiable view of the filtered appointment list */
    ObservableList<Appointment> getFilteredAppointmentList();

    /** Returns a sorted unmodifiable view of the filtered appointment list */
    void sortFilteredAppointmentList(String sortBy);

    /**
     * Updates the filter of the filtered appointments list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Returns the String representation of {@Code Appointment} that is related to the {@Code Person}.
     *
     * @param client the person to search.
     * @return the String representation of the list of appointments related to the client.
     */
    String getRelatedAppointmentsAsString(Person client);

    /**
     * Returns the String representation of {@Code Appointment} that only have this {@Code client} in its client list.
     *
     * @param client the person to search.
     * @return the String representation of the list of appointments that only have this client in its client list.
     */
    String getAppointmentsThatOnlyHaveThisClientAsString(Person client);

    /**
     * Replace the client in the client list of the appointments by a new edited client.
     * @param personToEdit the previous client.
     * @param editedPerson the new client that created when edit person information.
     */
    void updateEditedClientInAppointments(Person personToEdit, Person editedPerson);

    /**
     * Remove the given person from the client list of the appointments,
     * if the appointment has no client after the deletion, the appointment will be removed from schedule.
     * @param personToDelete the given person to delete.
     */
    void removePersonFromAppointments(Person personToDelete);

    /**
     * Check whether there are appointments that has time conflicts
     * with the given appointment in the current appointment list.
     * @param appointment The given appointment to check.
     * @return A list of appointments, the appointments that has time conflicts
     * with the given appointment.
     */
    List<Appointment> getClashingAppointments(Appointment appointment);

    /**
     * Replaces the given appointment {@code target} with {@code editedAppointment}.
     * {@code target} must exist in Placebook.
     * The appointment identity of {@code editedAppointment}
     * must not be the same as another existing appointment in PlaceBook.
     */
    void setAppointment(Appointment appointmentToEdit, Appointment editedAppointment);

    /**
     * Go back to the previous state before executing a certain command.
     * @throws NoHistoryStatesException Throw exception if there is no previous state.
     */
    void undo() throws NoHistoryStatesException;

    /**
     * Add the current state into the history states.
     */
    public void updateState(String commandName);

    public String getCommandName();
}
