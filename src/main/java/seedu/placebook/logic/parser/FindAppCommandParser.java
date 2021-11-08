package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.placebook.logic.commands.FindAppCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.schedule.DescriptionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindAppCommand object
 */
public class FindAppCommandParser implements Parser<FindAppCommand> {
    //@@author
    // adapted from AB-3

    /**
     * Parses the given {@code String} of arguments in the context of the FindAppCommand
     * and returns a FindAppCommand object for execution.
     * @return FindAppCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindAppCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindAppCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindAppCommand(new DescriptionContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
