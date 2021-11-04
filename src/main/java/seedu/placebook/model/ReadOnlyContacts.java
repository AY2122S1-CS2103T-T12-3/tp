package seedu.placebook.model;

import javafx.collections.ObservableList;
import seedu.placebook.model.person.Person;

/**
 * Unmodifiable view of Contacts
 */
public interface ReadOnlyContacts {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();
}
