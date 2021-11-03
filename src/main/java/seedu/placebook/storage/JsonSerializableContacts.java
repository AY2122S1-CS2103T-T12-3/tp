package seedu.placebook.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.placebook.commons.exceptions.IllegalValueException;
import seedu.placebook.model.Contacts;
import seedu.placebook.model.ReadOnlyContacts;
import seedu.placebook.model.person.Person;

/**
 * Immutable Contacts that are serializable to JSON format.
 */
@JsonRootName(value = "contacts")
class JsonSerializableContacts {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableContacts} with the given persons.
     */
    @JsonCreator
    public JsonSerializableContacts(@JsonProperty("persons") List<JsonAdaptedPerson> persons) {
        this.persons.addAll(persons);
    }

    /**
     * Converts a given {@code ReadOnlyContacts} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableContacts}.
     */
    public JsonSerializableContacts(ReadOnlyContacts source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
    }

    /**
     * Converts this Contacts into the model's {@code Contacts} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Contacts toModelType() throws IllegalValueException {
        Contacts contacts = new Contacts();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (contacts.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            contacts.addPerson(person);
        }
        return contacts;
    }

}
