package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_END_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_LOCATION_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_START_A;
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
import seedu.placebook.model.person.Person;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.testutil.AppointmentBuilder;
import seedu.placebook.testutil.ContactsBuilder;
import seedu.placebook.testutil.EditAppDescriptorBuilder;
import seedu.placebook.testutil.PersonBuilder;
import seedu.placebook.testutil.Seed;
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

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());

        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);
        Appointment editedAppointment = appointmentInList
                .withLocation(VALID_LOCATION_B)
                .withTimePeriod(VALID_START_A, VALID_END_A)
                .withDescription(VALID_DESCRIPTION_A)
                .build();

        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder()
                .withLocation(VALID_LOCATION_B)
                .withStart(VALID_START_A)
                .withEnd(VALID_END_A)
                .withDescription(VALID_DESCRIPTION_A)
                .build();
        EditAppCommand editAppCommand = new EditAppCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setAppointment(lastAppointment, editedAppointment);

        assertCommandSuccess(editAppCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastAppointment = Index.fromOneBased(model.getFilteredAppointmentList().size());
        Appointment lastAppointment = model.getFilteredAppointmentList().get(indexLastAppointment.getZeroBased());

        AppointmentBuilder appointmentInList = new AppointmentBuilder(lastAppointment);
        Appointment editedAppointment = appointmentInList.withLocation(VALID_LOCATION_B)
                .withDescription(VALID_DESCRIPTION_A)
                .build();

        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder().withLocation(VALID_LOCATION_B)
                .withDescription(VALID_DESCRIPTION_A)
                .build();
        EditAppCommand editAppCommand = new EditAppCommand(indexLastAppointment, descriptor);

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setAppointment(lastAppointment, editedAppointment);

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
    public void execute_unfilteredList_success() {
        Appointment appointmentInFilteredList = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment editedAppointment = new AppointmentBuilder(appointmentInFilteredList)
                .withLocation(VALID_LOCATION_B)
                .withDescription(VALID_DESCRIPTION_A)
                .build();
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppDescriptorBuilder()
                        .withLocation(VALID_LOCATION_B)
                        .withDescription(VALID_DESCRIPTION_A)
                        .build());

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        assertCommandSuccess(editAppCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showAppointmentAtIndex(model, INDEX_FIRST_APPOINTMENT);

        Appointment appointmentInFilteredList = model.getFilteredAppointmentList()
                .get(INDEX_FIRST_APPOINTMENT.getZeroBased());
        Appointment editedAppointment = new AppointmentBuilder(appointmentInFilteredList)
                .withLocation(VALID_LOCATION_B)
                .withDescription(VALID_DESCRIPTION_A)
                .build();
        EditAppCommand editAppCommand = new EditAppCommand(INDEX_FIRST_APPOINTMENT,
                new EditAppDescriptorBuilder()
                        .withLocation(VALID_LOCATION_B)
                        .withDescription(VALID_DESCRIPTION_A)
                        .build());

        String expectedMessage = String.format(EditAppCommand.MESSAGE_SUCCESS, editedAppointment);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setAppointment(model.getFilteredAppointmentList().get(0), editedAppointment);

        assertCommandSuccess(editAppCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidAppointmentIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredAppointmentList().size() + 1);
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder().build();
        EditAppCommand editAppCommand = new EditAppCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editAppCommand, model, uiStub, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

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

        EditAppCommand editAppCommand = new EditAppCommand(outOfBoundIndex,
                new EditAppDescriptorBuilder().build());

        assertCommandFailure(editAppCommand, model, uiStub, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

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
