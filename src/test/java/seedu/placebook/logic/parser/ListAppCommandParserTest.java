package seedu.placebook.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.placebook.logic.commands.FindTagsCommand;
import seedu.placebook.logic.commands.ListAppCommand;
import seedu.placebook.model.person.PersonHasTagsPredicate;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

class ListAppCommandParserTest {

    private ListAppCommandParser parser = new ListAppCommandParser();

    @Test
    public void parse_emptyArg_emptyStringReturned() {
        ListAppCommand emptyListAppCommand = new ListAppCommand("");
        assertParseSuccess(parser, "", emptyListAppCommand);
        assertParseSuccess(parser, "     ", emptyListAppCommand);
    }

    @Test
    public void parse_validArgs_returnsListAppCommand() {
        ListAppCommand expectedListAppCommand = new ListAppCommand("Time");

        // no leading and trailing whitespaces
        assertParseSuccess(parser, "Time", expectedListAppCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Time \n \t", expectedListAppCommand);
    }

}