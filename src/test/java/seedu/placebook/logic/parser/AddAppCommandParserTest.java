package seedu.placebook.logic.parser;

import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.AddAppCommand;
import seedu.placebook.model.person.Address;

public class AddAppCommandParserTest {
    private ArrayList<Index> indexes = new ArrayList<>();
    private AddAppCommandParser parser = new AddAppCommandParser();

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
        // missing index
        assertParseFailure(parser,
                " id/ a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                "Index is not a non-zero unsigned integer.");

        // missing address
        assertParseFailure(parser,
                " id/1 a/ start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                "Addresses can take any values, and it should not be blank");

        // missing start
        assertParseFailure(parser,
                " id/1 a/vivocity start/ end/01-01-2021 1000 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // missing end
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/ ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid id
        assertParseFailure(parser,
                " id/at a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/",
                "Index is not a non-zero unsigned integer.");

        // invalid start, invalid values
        assertParseFailure(parser,
                " id/1 a/vivocity start/00-00-0000 0000 end/01-01-2021 1000 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid start, missing values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 end/01-01-2021 1000 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid end, invalid values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/00-00-0000 0000 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid end, missing values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/01-01-2021 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");
    }
}
