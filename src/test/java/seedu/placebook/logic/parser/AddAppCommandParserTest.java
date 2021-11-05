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
        ArrayList<Index> index = new ArrayList<>();
        index.add(Index.fromZeroBased(0));

        // Index with one value
        assertParseSuccess(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                new AddAppCommand(index, new Address("vivocity"),
                        LocalDateTime.of(2021, 1, 1, 10, 0),
                        LocalDateTime.of(2021, 1, 1, 12, 0),
                        "Halloween Sales"));


        indexes.add(Index.fromZeroBased(0));
        indexes.add(Index.fromZeroBased(1));
        indexes.add(Index.fromZeroBased(2));
        // Index with multiple values
        assertParseSuccess(parser,
                " id/1,2,3 a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                new AddAppCommand(indexes, new Address("vivocity"),
                        LocalDateTime.of(2021, 1, 1, 10, 0),
                        LocalDateTime.of(2021, 1, 1, 12, 0),
                        "Halloween Sales"));
    }

    @Test
    public void parse_descFieldsMissing_success() {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(Index.fromZeroBased(0));

        // Blank desc
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
                " id/ a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                "Index is not a non-zero unsigned integer.\n"
                        + "Please note that index should be less than 2^31.");

        // missing address
        assertParseFailure(parser,
                " id/1 a/ start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                "Addresses can take any values, and it should not be blank");

        // missing start
        assertParseFailure(parser,
                " id/1 a/vivocity start/ end/01-01-2021 1000 ds/Halloween Sales",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // missing end
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/ ds/Halloween Sales",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid id
        assertParseFailure(parser,
                " id/at a/vivocity start/01-01-2021 1000 end/01-01-2021 1200 ds/Halloween Sales",
                "Index is not a non-zero unsigned integer.\n"
                        + "Please note that index should be less than 2^31.");

        // invalid start, invalid values
        assertParseFailure(parser,
                " id/1 a/vivocity start/00-00-0000 0000 end/01-01-2021 1000 ds/Halloween Sales",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid start, missing values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 end/01-01-2021 1000 ds/Halloween Sales",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid end, invalid values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/00-00-0000 0000 ds/Halloween Sales",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");

        // invalid end, missing values
        assertParseFailure(parser,
                " id/1 a/vivocity start/01-01-2021 1000 end/01-01-2021 ds/",
                "DateTime format should be \"dd-MM-yyyy HHmm\"."
                        + " Don't forget that there are only 12 months in a year!");
    }
}
