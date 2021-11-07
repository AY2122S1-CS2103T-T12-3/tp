package seedu.placebook.model;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.commons.core.LogsCenter;
import seedu.placebook.model.historystates.HistoryStates;
import seedu.placebook.model.historystates.State;
import seedu.placebook.model.historystates.exceptions.NoHistoryStatesException;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.model.schedule.exceptions.ClashingAppointmentsException;
import seedu.placebook.model.schedule.exceptions.DuplicateAppointmentException;

/**
 * Represents the in-memory model of the Placebook data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private Contacts contacts;
    private final UserPrefs userPrefs;
    private Schedule schedule;
    private FilteredList<Person> filteredPersons;
    private FilteredList<Appointment> filteredAppointments;
    private final HistoryStates historyStates;

    /**
     * Initializes a ModelManager with the given contacts, userPrefs and schedule.
     */
    public ModelManager(ReadOnlyContacts contacts, ReadOnlyUserPrefs userPrefs, ReadOnlySchedule schedule) {
        super();
        requireAllNonNull(contacts, userPrefs, schedule);

        logger.fine("Initializing with contacts: " + contacts
                + ", user prefs " + userPrefs + " and schedule " + schedule);

        this.contacts = new Contacts(contacts);
        this.userPrefs = new UserPrefs(userPrefs);
        this.schedule = new Schedule(schedule);
        filteredPersons = new FilteredList<>(this.contacts.getPersonList());
        filteredAppointments = new FilteredList<>(this.schedule.getSchedule());
        this.historyStates = new HistoryStates();
        State originState = new State(
                this.contacts,
                this.schedule,
                this.filteredPersons.getPredicate(),
                this.filteredAppointments.getPredicate(),
                "original state, no command name");
        this.historyStates.addNewState(originState);
    }

    public ModelManager() {
        this(new Contacts(), new UserPrefs(), new Schedule());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getContactsFilePath() {
        return userPrefs.getContactsFilePath();
    }

    @Override
    public void setContactsFilePath(Path contactsFilePath) {
        requireNonNull(contactsFilePath);
        userPrefs.setContactsFilePath(contactsFilePath);
    }

    //=========== Contacts ================================================================================

    @Override
    public void setContact(ReadOnlyContacts contacts) {
        this.contacts.resetData(contacts);
    }

    @Override
    public ReadOnlyContacts getContacts() {
        return contacts;
    }

    @Override
    public void setSchedule(ReadOnlySchedule schedule) {
        this.schedule.resetData(schedule);
    }

    @Override
    public ReadOnlySchedule getSchedule() {
        return schedule;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);

        return contacts.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        contacts.removePerson(target);
        this.removePersonFromAppointments(target);
    }

    @Override
    public void addPerson(Person person) {
        contacts.addPerson(person);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        contacts.setPerson(target, editedPerson);
    }

    @Override
    public void addAppointment(Appointment a) throws ClashingAppointmentsException, DuplicateAppointmentException {
        requireNonNull(a);

        schedule.addAppointment(a);
    }

    @Override
    public void deleteAppointment(Appointment a) {
        requireNonNull(a);

        schedule.deleteAppointment(a);
    }

    @Override
    public void setAppointment(Appointment appointmentToEdit, Appointment editedAppointment) {
        requireAllNonNull(appointmentToEdit, editedAppointment);

        schedule.setAppointment(appointmentToEdit, editedAppointment);
    }

    //=========== Query Operations =============================================================

    /**
     * Returns the String representation of {@Code Appointment} that is related to the {@Code Person}
     */
    @Override
    public String getRelatedAppointmentsAsString(Person client) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Appointment> relatedAppointment = getRelatedAppointments(client);

        for (Appointment appointment : relatedAppointment) {
            stringBuilder.append(appointment + "\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public String getAppointmentsThatOnlyHaveThisClientAsString(Person client) {
        List<Appointment> appointmentsThatOnlyHaveThisClient = new ArrayList<>();
        for (Appointment appointment : getRelatedAppointments(client)) {
            if (appointment.isTheOnlyClient(client)) {
                appointmentsThatOnlyHaveThisClient.add(appointment);
            }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Appointment appointment : appointmentsThatOnlyHaveThisClient) {
            stringBuilder.append(appointment + "\n");
        }

        return stringBuilder.toString();
    }

    /**
     * When a person is edited, update all the appointments in the appointment list that contains this person
     * by replacing the {@Code personToEdit} with the new {@Code editedPerson} instance.
     * @param personToEdit The person to be edited.
     * @param editedPerson The new person instance created.
     */
    @Override
    public void updateEditedClientInAppointments(Person personToEdit, Person editedPerson) {
        List<Appointment> relatedAppointments = getRelatedAppointments(personToEdit);
        for (Appointment appointment : relatedAppointments) {
            appointment.setClient(personToEdit, editedPerson);
        }
    }

    /**
     * Remove the given person from the client list of the appointments,
     * if the appointment has no client after the deletion, the appointment will be removed from schedule.
     * @param personToDelete the given person to delete.
     */
    @Override
    public void removePersonFromAppointments(Person personToDelete) {
        List<Appointment> relatedAppointments = getRelatedAppointments(personToDelete);
        for (Appointment appointment : relatedAppointments) {
            appointment.removeClient(personToDelete);
            if (appointment.isClientListEmpty()) {
                schedule.deleteAppointment(appointment);
            }
        }
    }

    /**
     * Returns the Observablelist of {@Code Appointment} that is related to the {@Code Person}
     */
    private List<Appointment> getRelatedAppointments(Person client) {
        return schedule.getRelatedAppointments(client);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedPlacebook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);

        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Appointment List Accessors =========================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedPlacebook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointments;
    }

    @Override
    public void sortFilteredAppointmentList(String sortBy) {
        if (sortBy.equals("Time")) {
            schedule.sortAppointmentByTimePeriod();
        } else if (sortBy.equals("Description")) {
            schedule.sortAppointmentByDescription();
        }
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return contacts.equals(other.contacts)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons)
                && filteredAppointments.equals(other.filteredAppointments);
    }

    /**
     * Check whether there are appointments that has time conflicts
     * with the given appointment in the current appointment list.
     * @param appointment The given appointment to check.
     * @return A list of appointments, the appointments that has time conflicts
     * with the given appointment.
     */
    @Override
    public List<Appointment> getClashingAppointments(Appointment appointment) {
        List<Appointment> clashingAppointments = new ArrayList<>();
        for (Appointment app : this.schedule) {
            if (app.getTimePeriod().hasConflictWith(appointment.getTimePeriod())) {
                clashingAppointments.add(app);
            }
        }
        return clashingAppointments;
    }

    /**
     * Go back to the previous state before executing a certain command.
     * @throws NoHistoryStatesException Throw exception if there is no previous state.
     */
    @Override
    public void undo() throws NoHistoryStatesException {
        if (!this.historyStates.hasHistoryStates()) {
            throw new NoHistoryStatesException();
        } else {
            this.historyStates.undo();
            State previousState = this.historyStates.getCurrentState();

            this.schedule = previousState.getSchedule();
            this.contacts = previousState.getContacts();

            filteredPersons = new FilteredList<>(this.contacts.getPersonList());
            filteredAppointments = new FilteredList<>(this.schedule.getSchedule());

            filteredPersons.setPredicate(previousState.getPersonListPredicate());
            filteredAppointments.setPredicate(previousState.getAppointmentListPredicate());
        }
    }

    /**
     * Add the current state into the history states.
     */
    @Override
    public void updateState(String commandName) {
        State stateToUpdate = new State(
                this.contacts,
                this.schedule,
                this.filteredPersons.getPredicate(),
                this.filteredAppointments.getPredicate(),
                commandName);
        this.historyStates.addNewState(stateToUpdate);
    }

    @Override
    public String getCommandName() {
        return this.historyStates.getCurrentState().getCommandName();
    }
}
