package seedu.placebook.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_END_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_LOCATION_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_START_B;

import org.junit.jupiter.api.Test;

import seedu.placebook.testutil.EditAppDescriptorBuilder;

public class EditAppDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditAppCommand.EditAppDescriptor descriptorWithSameValues = new EditAppCommand.EditAppDescriptor(DESC_A);
        assertTrue(DESC_A.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_A.equals(DESC_A));

        // null -> returns false
        assertFalse(DESC_A.equals(null));

        // different types -> returns false
        assertFalse(DESC_A.equals(5));

        // different values -> returns false
        assertFalse(DESC_A.equals(DESC_B));

        // different location -> returns false
        EditAppCommand.EditAppDescriptor editedA = new EditAppDescriptorBuilder(DESC_A)
                .withLocation(VALID_LOCATION_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different start -> returns false
        editedA = new EditAppDescriptorBuilder(DESC_A).withStart(VALID_START_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different email -> returns false
        editedA = new EditAppDescriptorBuilder(DESC_A).withEnd(VALID_END_B).build();
        assertFalse(DESC_A.equals(editedA));

        // different address -> returns false
        editedA = new EditAppDescriptorBuilder(DESC_A).withDescription(VALID_DESCRIPTION_B).build();
        assertFalse(DESC_A.equals(editedA));

    }
}
