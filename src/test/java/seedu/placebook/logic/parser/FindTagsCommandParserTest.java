package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.commands.FindTagsCommand;
import seedu.placebook.model.person.PersonHasTagsPredicate;

public class FindTagsCommandParserTest {

    private FindTagsCommandParser parser = new FindTagsCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindTagsCommand() {
        FindTagsCommand expectedFindTagsCommand =
                new FindTagsCommand(new PersonHasTagsPredicate(Arrays.asList("friends", "colleagues")));

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "friends colleagues", expectedFindTagsCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n friends \n \t colleagues  \t", expectedFindTagsCommand);
    }

}
