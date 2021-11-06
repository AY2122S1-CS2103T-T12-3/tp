package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.commands.CommandTestUtil.DESCRIPTION_DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.DESCRIPTION_DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.END_DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.END_DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.placebook.logic.commands.CommandTestUtil.INVALID_END_DESC;
import static seedu.placebook.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.placebook.logic.commands.CommandTestUtil.INVALID_START_DESC;
import static seedu.placebook.logic.commands.CommandTestUtil.LOCATION_DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.LOCATION_DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.START_DESC_A;
import static seedu.placebook.logic.commands.CommandTestUtil.START_DESC_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_DESCRIPTION_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_END_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_END_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_LOCATION_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_LOCATION_B;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_START_A;
import static seedu.placebook.logic.commands.CommandTestUtil.VALID_START_B;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.EditAppCommand;
import seedu.placebook.model.person.Address;
import seedu.placebook.testutil.EditAppDescriptorBuilder;

public class EditAppCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppCommand.MESSAGE_USAGE);

    private static final String MESSAGE_INVALID_INDEX = MESSAGE_INVALID_FORMAT
            + "\n"
            + ParserUtil.MESSAGE_INVALID_INDEX;

    private EditAppCommandParser parser = new EditAppCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_LOCATION_A, MESSAGE_INVALID_INDEX);

        // no field specified
        assertParseFailure(parser, "1", EditAppCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_LOCATION_B, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, "0" + VALID_LOCATION_B, MESSAGE_INVALID_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_INDEX);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_START_DESC,
                "DateTime format should be \"dd-MM-yyyy HHmm\". "
                        + "Don't forget that there are only 12 months in a year!"); // invalid DateTime format
        assertParseFailure(parser, "1" + INVALID_END_DESC,
                "DateTime format should be \"dd-MM-yyyy HHmm\". "
                        + "Don't forget that there are only 12 months in a year!"); // invalid DateTime format
        assertParseFailure(parser, "1"
                + INVALID_DESCRIPTION_DESC, "Description cannot be empty!"); // invalid description

        // invalid address followed by valid description
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC
                + DESCRIPTION_DESC_B, Address.MESSAGE_CONSTRAINTS);

        // valid description followed by invalid description
        assertParseFailure(parser, "1"
                + DESCRIPTION_DESC_B + INVALID_DESCRIPTION_DESC, "Description cannot be empty!");

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_START_DESC + INVALID_DESCRIPTION_DESC
                        + END_DESC_B + LOCATION_DESC_A,
                "DateTime format should be \"dd-MM-yyyy HHmm\". "
                        + "Don't forget that there are only 12 months in a year!");
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + START_DESC_B + LOCATION_DESC_A
                + DESCRIPTION_DESC_B + END_DESC_B;

        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder()
                .withLocation(VALID_LOCATION_A).withStart(VALID_START_B)
                .withEnd(VALID_END_B).withDescription(VALID_DESCRIPTION_B)
                .build();
        EditAppCommand expectedCommand = new EditAppCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_A + DESCRIPTION_DESC_B;

        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder().withLocation(VALID_LOCATION_A)
                .withDescription(VALID_DESCRIPTION_B)
                .build();
        EditAppCommand expectedCommand = new EditAppCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // location
        Index targetIndex = INDEX_SECOND_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_A;
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder()
                .withLocation(VALID_LOCATION_A)
                .build();
        EditAppCommand expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // start
        userInput = targetIndex.getOneBased() + START_DESC_B;
        descriptor = new EditAppDescriptorBuilder().withStart(VALID_START_B).build();
        expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // end
        userInput = targetIndex.getOneBased() + END_DESC_A;
        descriptor = new EditAppDescriptorBuilder().withEnd(VALID_END_A).build();
        expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_B;
        descriptor = new EditAppDescriptorBuilder().withDescription(VALID_DESCRIPTION_B).build();
        expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + LOCATION_DESC_A + START_DESC_B + DESCRIPTION_DESC_A
                + DESCRIPTION_DESC_B + DESCRIPTION_DESC_A + END_DESC_A + START_DESC_A + LOCATION_DESC_B + END_DESC_B;

        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder().withLocation(VALID_LOCATION_B)
                .withStart(VALID_START_A).withEnd(VALID_END_B).withDescription(VALID_DESCRIPTION_A)
                .build();
        EditAppCommand expectedCommand = new EditAppCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_APPOINTMENT;
        String userInput = targetIndex.getOneBased() + INVALID_START_DESC + START_DESC_A;
        EditAppCommand.EditAppDescriptor descriptor = new EditAppDescriptorBuilder().withStart(VALID_START_A).build();
        EditAppCommand expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + LOCATION_DESC_B + DESCRIPTION_DESC_B;
        descriptor = new EditAppDescriptorBuilder().withLocation(VALID_LOCATION_B)
                .withDescription(VALID_DESCRIPTION_B).build();
        expectedCommand = new EditAppCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
