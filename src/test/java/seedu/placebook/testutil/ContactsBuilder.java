package seedu.placebook.testutil;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.person.Person;

/**
 * A utility class to help with building Contact objects.
 * Example usage: <br>
 *     {@code contacts ab = new ContactsBuilder().withPerson("John", "Doe").build();}
 */
public class ContactsBuilder {

    private Contacts contacts;

    public ContactsBuilder() {
        contacts = new Contacts();
    }

    public ContactsBuilder(Contacts contacts) {
        this.contacts = contacts;
    }

    /**
     * Adds a new {@code Person} to the {@code contacts} that we are building.
     */
    public ContactsBuilder withPerson(Person person) {
        contacts.addPerson(person);
        return this;
    }

    public Contacts build() {
        return contacts;
    }
}
