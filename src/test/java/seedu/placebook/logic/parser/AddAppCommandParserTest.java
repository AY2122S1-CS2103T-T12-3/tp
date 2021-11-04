package seedu.placebook.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.AddAppCommand;
import seedu.placebook.logic.commands.AddCommand;
import seedu.placebook.model.person.*;
import seedu.placebook.model.schedule.Appointment;
import seedu.placebook.model.tag.Tag;
import seedu.placebook.testutil.AppointmentBuilder;
import seedu.placebook.testutil.PersonBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.commands.CommandTestUtil.*;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.placebook.testutil.TypicalPersons.AMY;
import static seedu.placebook.testutil.TypicalPersons.BOB;


//TODO
public class AddAppCommandParserTest {
    private AddAppCommandParser parser = new AddAppCommandParser();
    ArrayList<Index> indexes = new ArrayList<>();


    @Test
    public void parse_allFieldsPresent_success() {
        indexes.add(Index.fromZeroBased(0));
        // whitespace only preamble
        assertParseSuccess(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                new AddAppCommand(indexes, new Address("vivocity"),
                        LocalDateTime.of(2021, 1, 1, 10, 0),
                        LocalDateTime.of(2021, 1, 1, 12, 0),
                        "Halloween Sales"));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        indexes.add(Index.fromZeroBased(0));

        // blank desc
        assertParseSuccess(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                new AddAppCommand(indexes, new Address("vivocity"),
                        LocalDateTime.of(2021, 1, 1, 10, 0),
                        LocalDateTime.of(2021, 1, 1, 12, 0),
                        ""));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE);

        // missing index prefix
        assertParseFailure(parser,
                " id/1 a/ start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                "Addresses can take any values, and it should not be blank");

        // missing address prefix
        assertParseFailure(parser,
                " id/1 a/ start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                "Addresses can take any values, and it should not be blank");

        // missing start prefix
        assertParseFailure(parser,
                " id/1 a/vivocity start/ end/01-01-2021 1000 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // missing end prefix
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/ ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
