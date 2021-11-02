package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_INDEXES;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.AddAppCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.person.Address;

public class AddAppCommandParser implements Parser<AddAppCommand> {

    @Override
    public AddAppCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEXES, PREFIX_ADDRESS, PREFIX_STARTDATETIME,
                        PREFIX_ENDDATETIME, PREFIX_DESCRIPTION);

        if (!arePrefixesPresent(argMultimap, PREFIX_INDEXES, PREFIX_ADDRESS, PREFIX_STARTDATETIME, PREFIX_ENDDATETIME,
                PREFIX_DESCRIPTION)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAppCommand.MESSAGE_USAGE));
        }

        List<Index> indexes = ParserUtil.parseIndexes(argMultimap.getValue(PREFIX_INDEXES).get());
        Address address = ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        LocalDateTime startDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_STARTDATETIME).get());
        LocalDateTime endDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_ENDDATETIME).get());
        String description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());

        return new AddAppCommand(indexes, address, startDateTime, endDateTime, description);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
