package seedu.placebook.logic.parser.exceptions;

import seedu.placebook.commons.exceptions.IllegalValueException;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message + "\n" + cause.getMessage(), cause);
    }
}
