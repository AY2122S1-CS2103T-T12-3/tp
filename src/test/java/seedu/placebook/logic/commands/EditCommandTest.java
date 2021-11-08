package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.commons.core.Messages;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.schedule.Schedule;
import seedu.placebook.testutil.AppointmentBuilder;
import seedu.placebook.testutil.ContactsBuilder;
import seedu.placebook.testutil.EditPersonDescriptorBuilder;
import seedu.placebook.testutil.PersonBuilder;
import seedu.placebook.testutil.Seed;
import seedu.placebook.ui.Ui;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {
    // default positive confirmation ui. This will not affect EditCommand
    private final Ui uiStub = UiStubFactory.getUiStub(true);

    private final UniquePersonList editTestPersonList = new UniquePersonList();

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

        Appointment appointment1 = new AppointmentBuilder(Seed.ONE).withClient(client1).build();
        Appointment appointment2 = new AppointmentBuilder(Seed.TWO).withClient(client1).addClient(client2).build();

        testSchedule = new Schedule();
        testSchedule.addAppointment(appointment1);
        testSchedule.addAppointment(appointment2);
        model = new ModelManager(testContacts, new UserPrefs(), testSchedule);
        System.out.println(model.getFilteredAppointmentList().get(0));
        System.out.println(model.getFilteredAppointmentList().get(1));
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(testContacts, new UserPrefs(), testSchedule);

        Person personToEdit = model.getFilteredPersonList().get(0);
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateEditedClientInAppointments(personToEdit, editedPerson);

        System.out.println(model.equals(expectedModel));
        assertCommandSuccess(editCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastPerson, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());

        assertCommandSuccess(editCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson);

        Model expectedModel =
                new ModelManager(new Contacts(model.getContacts()), new UserPrefs(), model.getSchedule());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, uiStub, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editCommand, model, uiStub, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in contacts
        Person personInList = model.getContacts().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, uiStub, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, uiStub, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of contacts
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of contacts list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getContacts().getPersonList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, uiStub, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

}
