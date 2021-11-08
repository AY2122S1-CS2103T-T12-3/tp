package seedu.placebook.logic.parser;

import static seedu.placebook.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.placebook.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.placebook.logic.commands.AddAppCommand;
import seedu.placebook.logic.commands.AddCommand;
import seedu.placebook.logic.commands.ClearCommand;
import seedu.placebook.logic.commands.Command;
import seedu.placebook.logic.commands.DelAppCommand;
import seedu.placebook.logic.commands.DeleteCommand;
import seedu.placebook.logic.commands.EditAppCommand;
import seedu.placebook.logic.commands.EditCommand;
import seedu.placebook.logic.commands.ExitCommand;
import seedu.placebook.logic.commands.FindAppCommand;
import seedu.placebook.logic.commands.FindCommand;
import seedu.placebook.logic.commands.FindTagsCommand;
import seedu.placebook.logic.commands.HelpCommand;
import seedu.placebook.logic.commands.ListAppCommand;
import seedu.placebook.logic.commands.ListCommand;
import seedu.placebook.logic.commands.UndoCommand;
import seedu.placebook.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class PlaceBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddAppCommand.COMMAND_WORD:
            return new AddAppCommandParser().parse(arguments);

        case DelAppCommand.COMMAND_WORD:
            return new DelAppCommandParser().parse(arguments);

        case EditAppCommand.COMMAND_WORD:
            return new EditAppCommandParser().parse(arguments);

        case ListAppCommand.COMMAND_WORD:
            return new ListAppCommandParser().parse(arguments);

        case FindAppCommand.COMMAND_WORD:
            return new FindAppCommandParser().parse(arguments);

        case FindTagsCommand.COMMAND_WORD:
            return new FindTagsCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            return new UndoCommand();

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
