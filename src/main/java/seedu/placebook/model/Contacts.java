package seedu.placebook.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.placebook.model.person.Person;
import seedu.placebook.model.person.UniquePersonList;


/**
 * Wraps all data at the Placebook level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class Contacts implements ReadOnlyContacts {

    private final UniquePersonList persons;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public Contacts() {}

    /**
     * Creates a Contacts using the Persons in the {@code toBeCopied}
     */
    public Contacts(ReadOnlyContacts toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * A factory method to create deep copy of the given {@Code Contacts}.
     * @param contactsToCopy The given {@Code Contacts} to copy.
     * @return A deep copy of the given contacts to copy.
     */
    public static Contacts deepCopy(Contacts contactsToCopy) {
        Contacts result = new Contacts();
        for (Person person : contactsToCopy.getPersonList()) {
            result.addPerson(Person.deepCopy(person));
        }
        return result;
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code contacts} with {@code newData}.
     */
    public void resetData(ReadOnlyContacts newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in contacts.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to contacts.
     * The person must not already exist in contacts.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in contacts.
     * The person identity of {@code editedPerson} must not be the same as another existing person in contacts.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code contacts}.
     * {@code key} must exist in the contacts.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Contacts // instanceof handles nulls
                && persons.equals(((Contacts) other).persons));
    }

    @Override
    public int hashCode() {
        return persons.hashCode();
    }
}
