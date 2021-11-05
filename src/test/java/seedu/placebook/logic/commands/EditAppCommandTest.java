package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.CommandTestUtil.showAppointmentAtIndex;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.model.schedule.TimePeriod;
import seedu.placebook.model.schedule.exceptions.ClashingAppointmentsException;
import seedu.placebook.testutil.*;
import seedu.placebook.ui.Ui;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditAppCommand.
 */
public class EditAppCommandTest {
    // default positive confirmation ui. This will not affect EditAppCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private Model model;
    private Contacts testContacts;
    private Schedule testSchedule;

    @BeforeEach
    public void setUp() {
        Person client1 = new PersonBuilder().withName("Client1").build();
        Person client2 = new PersonBuilder().withName("Client2").build();

        testContacts = new ContactsBuilder().build();
        testContacts.addPerson(client1);
        testContacts.addPerson(client2);

        Appointment appointment1 = new AppointmentBuilder(Seed.ONE).addClient(client1).build();
        Appointment appointment2 = new AppointmentBuilder(Seed.THREE).addClient(client1).addClient(client2).build();

        testSchedule = new Schedule();
        testSchedule.addAppointment(appointment1);
        testSchedule.addAppointment(appointment2);
        model = new ModelManager(testContacts, new UserPrefs(), testSchedule);
        System.out.println(model.getFilteredAppointmentList().get(0));
        System.out.println(model.getFilteredAppointmentList().get(1));
    }

    // TODO: FIX THIS TEST
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Appointment editedAppointment = new AppointmentBuilder().build();
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder(editedAppointment).build();
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT, descriptor);

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(testContacts, new UserPrefs(), testSchedule);

        Appointment appointmentToEdit = model.getFilteredAppointmentList().get(0);
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        System.out.println(model.equals(expectedModel));
        assertCommandSuccess(editAppCommand, model, uiStub, expectedMessage, expectedModel);
    }

    // TODO: FIX THIS TEST
    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditCommand.EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, uiStub, expectedMessage, expectedModel);
    }

    // TODO: HOW TO TEST FOR EMPTY EDITING SHIT
    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        EditAppCommand.EditAppDescriptor emptyDescriptor = new EditAppCommand.EditAppDescriptor();
        emptyDescriptor.setClients(null);
        emptyDescriptor.setLocation(null);
        emptyDescriptor.setStart(null);
        emptyDescriptor.setEnd(null);
        emptyDescriptor.setDescription(null);
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT,
                emptyDescriptor);

        String expectedMessage = String.format(EditAppCommand.MESSAGE_NOT_EDITED);

        assertCommandFailure(editAppCommand, model, uiStub, expectedMessage);
    }

    // TODO: FIX THIS TEST
    @Test
    public void execute_filteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentInFilteredList = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment editedAppointment = new AppointmentBuilder(appointmentInFilteredList)
                .withClient(VALID_NAME_BOB).build();
        UniquePersonList clients = new UniquePersonList();
        clients.add(TypicalPersons.BOB);
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppDescriptorBuilder().withClients(clients).build());

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        assertCommandSuccess(editAppCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clashingAppointmentUnfilteredList_failure() {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder(firstAppointment)
                .withDescription("test").build();
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_SECOND_APPOINTMENT, descriptor);

        String expectedMessage = String
                .format(Messages.MESSAGE_APPOINTMENTS_CLASHING_APPOINTMENT_ADDED + "\n" + firstAppointment + "\n");

        assertCommandFailure(editAppCommand, model, uiStub, expectedMessage);
    }

    @Test
    public void execute_clashingAppointmentFilteredList_failure() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        // edit appointment in filtered list into a duplicate in appointment
        Appointment appointmentInList = model.getSchedule().getSchedule().get(INDEX_SECOND_APPOINTMENT.getZeroBased());
        Appointment clashingEditedAppointment = new AppointmentBuilder(Seed.FOUR).build();
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppDescriptorBuilder(clashingEditedAppointment).build());

        String expectedMessage = String
                .format(Messages.MESSAGE_APPOINTMENTS_CLASHING_APPOINTMENT_ADDED + "\n" + appointmentInList + "\n");

        assertCommandFailure(editAppCommand, model, uiStub, expectedMessage);
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        UniquePersonList clients = new UniquePersonList();
        clients.add(TypicalPersons.ALICE);
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder()
                .withClients(clients).build();
        EditAppCommand editAppCommand = new EditAppCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAppCommand, model, uiStub, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    // THIS WORKS
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of appointment list
     */
    @Test
    public void execute_invalidAppointmentIndexFilteredList_failure() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);
        Index outOfBoundIndex = INDEX_SECOND_APPOINTMENT;
        // ensures that outOfBoundIndex is still in bounds of contacts list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getSchedule().getSchedule().size());

        UniquePersonList clients = new UniquePersonList();
        clients.add(TypicalPersons.BOB);
        EditAppCommand editAppCommand = new EditAppCommand(outOfBoundIndex,
                new EditAppDescriptorBuilder().withClients(clients).build());

        assertCommandFailure(editAppCommand, model, uiStub, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    // THIS WORKS
    @Test
    public void equals() {
        final EditAppCommand standardCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT, DESC_A);

        // same values -> returns true
        EditAppCommand.EditAppDescriptor copyDescriptor = new EditAppCommand.EditAppDescriptor(DESC_A);
        EditAppCommand commandWithSameValues = new EditAppCommand(INDEX_FIRST_APPOINTMENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditAppCommand(INDEX_SECOND_APPOINTMENT, DESC_A)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditAppCommand(INDEX_FIRST_APPOINTMENT, DESC_B)));
    }

}
