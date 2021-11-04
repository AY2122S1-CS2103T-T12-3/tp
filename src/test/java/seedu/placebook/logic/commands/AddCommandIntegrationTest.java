package seedu.placebook.logic.commands;

import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.placebook.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.placebook.testutil.TypicalAppointment.getTypicalSchedule;
import static seedu.placebook.testutil.TypicalPersons.getTypicalContacts;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.placebook.logic.UiStubFactory;
import seedu.placebook.model.Model;
import seedu.placebook.model.ModelManager;
import seedu.placebook.model.UserPrefs;
import seedu.placebook.model.person.Person;
import seedu.placebook.testutil.PersonBuilder;
import seedu.placebook.ui.Ui;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;
    private Ui ui;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalContacts(), new UserPrefs(), getTypicalSchedule());

        // default positive confirmation ui. This will not affect AddCommand
        ui = UiStubFactory.getUiStub(true);
    }

    @Test
    public void execute_newPerson_success() {
        Person validPerson = new PersonBuilder().build();

        Model expectedModel = new ModelManager(model.getContacts(), new UserPrefs(), model.getSchedule());
        expectedModel.addPerson(validPerson);

        assertCommandSuccess(new AddCommand(validPerson), model, ui,
                String.format(AddCommand.MESSAGE_SUCCESS, validPerson), expectedModel);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person personInList = model.getContacts().getPersonList().get(0);
        assertCommandFailure(new AddCommand(personInList), model, ui, AddCommand.MESSAGE_DUPLICATE_PERSON);
    }

}
