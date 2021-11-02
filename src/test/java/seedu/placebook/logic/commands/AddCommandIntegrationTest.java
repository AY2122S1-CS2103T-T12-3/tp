package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.Person;
import seedu.placebook.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs(), getTypicalSchedule());
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getContacts().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
