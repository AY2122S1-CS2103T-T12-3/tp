package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.placebook.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.placebook.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;

import org.junit.jupiter.api.Test;

import seedu.placebook.logic.commands.DelAppCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DelAppCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DelAppCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DelAppCommandParserTest {

    private DelAppCommandParser parser = new DelAppCommandParser();

    @Test
    public void parse_validArgs_returnsDelAppCommand() {
        assertParseSuccess(parser, "1", new DelAppCommand(INDEX_FIRST_APPOINTMENT));
    }

    @Test
    public void parse_validArgs_returnsDelAppCommand2() {
        assertParseSuccess(parser, "2", new DelAppCommand(INDEX_SECOND_APPOINTMENT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAppCommand.MESSAGE_USAGE)
                        + "\n"
                        + ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidArgs_throwsParseException2() {
        assertParseFailure(parser, "1 0 2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAppCommand.MESSAGE_USAGE)
                        + "\n"
                        + ParserUtil.MESSAGE_INVALID_INDEX);
    }
}
