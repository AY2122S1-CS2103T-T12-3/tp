package seedu.placebook.testutil;

import seedu.placebook.model.Contacts;
import seedu.placebook.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private Contacts addressBook;

    public AddressBookBuilder() {
        addressBook = new Contacts();
    }

    public AddressBookBuilder(Contacts addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        addressBook.addPerson(person);
        return this;
    }

    public Contacts build() {
        return addressBook;
    }
}
