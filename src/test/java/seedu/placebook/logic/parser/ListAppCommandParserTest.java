package seedu.placebook.logic.parser;

import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.commands.ListAppCommand;

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
