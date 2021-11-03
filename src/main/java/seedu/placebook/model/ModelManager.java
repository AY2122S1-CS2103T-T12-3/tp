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
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private AddressBook addressBook;
    private final UserPrefs userPrefs;
    private Schedule schedule;
    private FilteredList<Person> filteredPersons;
    private FilteredList<Appointment> filteredAppointments;
    private final HistoryStates historyStates;

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs and schedule.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs, ReadOnlySchedule schedule) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.schedule = new Schedule(schedule);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.schedule.getSchedule());
        this.historyStates = new HistoryStates();
        State originState = new State(this.addressBook, this.schedule);
        this.historyStates.addNewState(originState);
    }

    /**
     * Initializes a ModelManager with the given addressBook, userPrefs, schedule and history states.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs,
                        ReadOnlySchedule schedule, HistoryStates historyStates) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.schedule = new Schedule(schedule);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAppointments = new FilteredList<>(this.schedule.getSchedule());
        this.historyStates = historyStates;
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs(), new Schedule());
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
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
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

        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
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
     * {@code versionedAddressBook}
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
     * {@code versionedAddressBook}
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
        return addressBook.equals(other.addressBook)
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
     * Returns the String representation of the appointments that have time conflict with the
     * given appointment.
     * @param appointment The given appointment to check.
     * @return The String representation of the appointments that have time conflict with the
     * given appointment.
     */
    @Override
    public String getClashingAppointmentsAsString(Appointment appointment) {
        List<Appointment> clashingAppointments = getClashingAppointments(appointment);
        StringBuilder stringBuilder = new StringBuilder();
        for (Appointment app : clashingAppointments) {
            stringBuilder.append(app + "\n");
        }
        return stringBuilder.toString();
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
            this.addressBook = previousState.getAddressBook();
            filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
            filteredAppointments = new FilteredList<>(this.schedule.getSchedule());
            System.out.println(this.addressBook.getPersonList().size() + " " + this.schedule.hashCode());
        }
    }

    /**
     * Add the current state into the history states.
     */
    public void updateState() {
        State stateToUpdate = new State(this.addressBook, this.schedule);
        this.historyStates.addNewState(stateToUpdate);
    }
}
