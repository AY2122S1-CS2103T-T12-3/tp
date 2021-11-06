package seedu.placebook.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.placebook.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's phone number in contacts.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Phone {

    public static final int MAXIMUM_PHONE_NUMBER_LENGTH = 20;

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, it should be at least 3 digits long"
                    + " and at most " + MAXIMUM_PHONE_NUMBER_LENGTH + " digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";

    public final String value;

    /**
     * Constructs a {@code Phone}.
     * @param phone The given valid phone number.
     */
    public Phone(String phone) {
        requireNonNull(phone);
        checkArgument(isValidPhone(phone), MESSAGE_CONSTRAINTS);
        this.value = phone;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidPhone(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAXIMUM_PHONE_NUMBER_LENGTH;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
