package seedu.placebook.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.placebook.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.placebook.commons.core.GuiSettings;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.exceptions.CommandException;
import seedu.placebook.model.AddressBook;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.ReadOnlyAddressBook;
import seedu.placebook.model.ReadOnlySchedule;
import seedu.placebook.model.ReadOnlyUserPrefs;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.testutil.AppointmentBuilder;
import seedu.placebook.testutil.PersonBuilder;

public class AddAppCommandTest {

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new AddAppCommand(null, null, null, null, null));
    }

    @Test
    public void constructor_invalidAppointment_invalidTime_throwsDateTimeException() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(2));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        assertThrows(DateTimeException.class,
        () -> new AddAppCommand(
                indexes,
                new Address("vivocity"),
                LocalDateTime.of(2021, 13, 20, 10, 0),
                LocalDateTime.of(2022, 1, 21, 10, 0),
                "Halloween Sales"));
    }

    @Test
    public void constructor_invalidAppointment_emptyAddress_throwsIllegalArgumentException() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(2));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        assertThrows(IllegalArgumentException.class,
                () -> new AddAppCommand(
                        indexes,
                        new Address(""),
                        LocalDateTime.of(2021, 1, 20, 10, 0),
                        LocalDateTime.of(2021, 1, 21, 10, 0),
                        "Halloween Sales"));
    }

    @Test
    public void execute_validAppointmentSinglePerson_returnSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(0));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        Appointment validAppointment = new AppointmentBuilder().build();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        CommandResult commandResult = new AddAppCommand(
                indexes,
                new Address("vivocity"),
                LocalDateTime.of(2021, 12, 25, 21, 30),
                LocalDateTime.of(2021, 12, 25, 22, 30),
                "Halloween Sales").execute(modelStub);

        assertEquals(String.format(AddAppCommand.MESSAGE_SUCCESS, validAppointment), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validAppointmentTwoPerson_returnSuccess() throws Exception {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(0));
        indexes.add(Index.fromZeroBased(1));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();

        Appointment validAppointment = new AppointmentBuilder().addClient("BOB").build();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        modelStub.addPerson(new PersonBuilder().withName("BOB").build());
        CommandResult commandResult = new AddAppCommand(
                indexes,
                new Address("vivocity"),
                LocalDateTime.of(2021, 12, 25, 21, 30),
                LocalDateTime.of(2021, 12, 25, 22, 30),
                "Halloween Sales").execute(modelStub);

        assertEquals(String.format(AddAppCommand.MESSAGE_SUCCESS, validAppointment), commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidAppointment_invalidPerson_returnInvalid() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(2));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        Command commandResult = new AddAppCommand(
                indexes,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 2, 10, 0),
                "Halloween Sales");

        assertThrows(CommandException.class, ()
            -> commandResult.execute(modelStub));
    }

    @Test
    public void execute_duplicatePerson_returnInvalid() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(0));
        indexes.add(Index.fromZeroBased(0));
        ModelStubAcceptingAppointmentAdded modelStub = new ModelStubAcceptingAppointmentAdded();
        modelStub.addPerson(new PersonBuilder().withName("ALICE").build());
        modelStub.addPerson(new PersonBuilder().withName("BOB").build());
        Command commandResult = new AddAppCommand(
                indexes,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 2, 10, 0),
                "Halloween Sales");

        assertThrows(CommandException.class, ()
            -> commandResult.execute(modelStub));
    }

    @Test
    public void execute_duplicateAppointmentTime_returnInvalid() {
        // use ModelManager instead of Stub for integration test
        ModelManager modelTester = new ModelManager();

        ArrayList<Index> indexOne = new ArrayList<>();
        indexOne.add(Index.fromZeroBased(0));
        ArrayList<Index> indexTwo = new ArrayList<>();
        indexTwo.add(Index.fromZeroBased(1));
        modelTester.addPerson(new PersonBuilder().withName("ALICE").build());
        modelTester.addPerson(new PersonBuilder().withName("BOB").build());
        Command initialCommand = new AddAppCommand(
                indexOne,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 2, 10, 0),
                "Halloween Sales");
        Command commandResult = new AddAppCommand(
                indexTwo,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 2, 10, 0),
                "Halloween Sales");
        try {
            initialCommand.execute(modelTester);
        } catch (CommandException e) {
            throw new AssertionError("This method should not be called.");
        }

        assertThrows(CommandException.class, ()
            -> commandResult.execute(modelTester));
    }

    @Test
    public void execute_conflictingAppointmentTime_returnInvalid() {
        // use ModelManager instead of Stub for integration test
        ModelManager modelTester = new ModelManager();

        ArrayList<Index> indexOne = new ArrayList<>();
        indexOne.add(Index.fromZeroBased(0));
        ArrayList<Index> indexTwo = new ArrayList<>();
        indexTwo.add(Index.fromZeroBased(1));
        modelTester.addPerson(new PersonBuilder().withName("ALICE").build());
        modelTester.addPerson(new PersonBuilder().withName("BOB").build());
        Command initialCommand = new AddAppCommand(
                indexOne,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 10, 0),
                LocalDateTime.of(2021, 1, 1, 12, 0),
                "Halloween Sales");
        Command commandResult = new AddAppCommand(
                indexTwo,
                new Address("vivocity"),
                LocalDateTime.of(2021, 1, 1, 11, 0),
                LocalDateTime.of(2021, 1, 1, 13, 0),
                "Halloween Sales");
        try {
            initialCommand.execute(modelTester);
        } catch (CommandException e) {
            throw new AssertionError("This method should not be called.");
        }

        assertThrows(CommandException.class, ()
            -> commandResult.execute(modelTester));
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setSchedule(ReadOnlySchedule schedule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlySchedule getSchedule() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment a) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment a) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredAppointmentList(String sortBy) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getRelatedAppointmentsAsString(Person client) {
            return "";
        }

        @Override
        public String getAppointmentsThatOnlyHaveThisClientAsString(Person client) {
            return "";
        }

        @Override
        public void updateEditedClientInAppointments(Person personToEdit, Person editedPerson) {
        }

        @Override
        public void removePersonFromAppointments(Person personToDelete) {
        }

        @Override
        public List<Appointment> getClashingAppointments(Appointment appointment) {
            return new ArrayList<>();
        }

        @Override
        public String getClashingAppointmentsAsString(Appointment appointment) {
            return "";
        }

        @Override
        public void setAppointment(Appointment appointmentToEdit, Appointment editedAppointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateState() {
        }

        @Override
        public void undo() {
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingAppointmentAdded extends AddAppCommandTest.ModelStub {
        final ObservableList<Appointment> appointmentAdded = FXCollections.observableArrayList();
        final ObservableList<Person> personsAdded = FXCollections.observableArrayList();

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return personsAdded;
        }

        @Override
        public void addAppointment(Appointment a) {
            requireNonNull(a);
            appointmentAdded.add(a);
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            return appointmentAdded;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
