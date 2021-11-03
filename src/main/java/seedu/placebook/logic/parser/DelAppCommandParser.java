package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.DelAppCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DelAppCommand object
 */
public class DelAppCommandParser implements Parser<DelAppCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DelAppCommand
     * and returns a DelAppCommand object for execution.
     * @return DelAppCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    @Override
    public DelAppCommand parse(String args) throws ParseException {

        Index index;

        try {
            index = ParserUtil.parseIndex(args);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DelAppCommand.MESSAGE_USAGE), pe);
        }

        return new DelAppCommand(index);
    }

}
