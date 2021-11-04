package seedu.placebook.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalPersons.ALICE;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.exceptions.DuplicatePersonException;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.testutil.PersonBuilder;

public class ContactsTest {

    private final Contacts contacts = new Contacts();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), contacts.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contacts.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyContacts_replacesData() {
        Contacts newData = getTypicalContacts();
        contacts.resetData(newData);
        assertEquals(newData, contacts);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Person> newPersons = Arrays.asList(ALICE, editedAlice);
        ContactsStub newData = new ContactsStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> contacts.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> contacts.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInContacts_returnsFalse() {
        assertFalse(contacts.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personInContacts_returnsTrue() {
        contacts.addPerson(ALICE);
        assertTrue(contacts.hasPerson(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInContacts_returnsTrue() {
        contacts.addPerson(ALICE);
        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(contacts.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> contacts.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyContacts whose persons list can violate interface constraints.
     */
    private static class ContactsStub implements ReadOnlyContacts {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        ContactsStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }
    }

}
