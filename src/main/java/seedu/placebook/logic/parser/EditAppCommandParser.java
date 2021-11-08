package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_ENDDATETIME;
import static seedu.placebook.logic.parser.CliSyntax.PREFIX_STARTDATETIME;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.logic.commands.EditAppCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.schedule.exceptions.EndTimeBeforeStartTimeException;

/**
 * Parses input arguments and creates a new EditAppCommand object
 */
public class EditAppCommandParser implements Parser<EditAppCommand> {

    @Override
    public EditAppCommand parse(String args) throws ParseException, EndTimeBeforeStartTimeException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ADDRESS, PREFIX_STARTDATETIME,
                        PREFIX_ENDDATETIME, PREFIX_DESCRIPTION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditAppCommand.MESSAGE_USAGE), pe);
        }

        EditAppCommand.EditAppDescriptor editAppDescriptor = new EditAppCommand.EditAppDescriptor();
        if (argMultimap.getValue(PREFIX_ADDRESS).isPresent()) {
            editAppDescriptor.setLocation(ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get()));
        }
        if (argMultimap.getValue(PREFIX_STARTDATETIME).isPresent()) {
            editAppDescriptor.setStart(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_STARTDATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_ENDDATETIME).isPresent()) {
            editAppDescriptor.setEnd(ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_ENDDATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            if (argMultimap.getValue(PREFIX_DESCRIPTION).get().equals("")) {
                throw new ParseException(String.format("Description cannot be empty!"));
            }
            editAppDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }

        if (!editAppDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAppCommand.MESSAGE_NOT_EDITED);
        }

        return new EditAppCommand(index, editAppDescriptor);
    }

}
