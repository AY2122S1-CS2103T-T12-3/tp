package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.placebook.logic.commands.FindTagsCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.person.PersonHasTagsPredicate;

/**
 * Parses input arguments and creates a new FindTagsCommand object
 */
public class FindTagsCommandParser implements Parser<FindTagsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindTagsCommand
     * and returns a FindTagsCommand object for execution.
     * @return FindTagsCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindTagsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagsCommand.MESSAGE_USAGE));
        }

        String[] tagsKeywords = trimmedArgs.split("\\s+");

        return new FindTagsCommand(new PersonHasTagsPredicate(Arrays.asList(tagsKeywords)));
    }

}
