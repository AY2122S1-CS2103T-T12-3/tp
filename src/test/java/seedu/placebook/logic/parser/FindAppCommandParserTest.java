package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.commands.FindAppCommand;
import seedu.placebook.model.schedule.DescriptionContainsKeywordsPredicate;



public class FindAppCommandParserTest {

    private FindAppCommandParser parser = new FindAppCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindAppCommand() {
        // no leading and trailing whitespaces
        FindAppCommand expectedFindAppCommand =
                new FindAppCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList("Sales", "Meeting")));
        assertParseSuccess(parser, "Sales Meeting", expectedFindAppCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Sales \n \t Meeting \t", expectedFindAppCommand);
    }

}
