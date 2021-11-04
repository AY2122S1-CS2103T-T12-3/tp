package seedu.placebook.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.placebook.commons.core.index.Index;
import seedu.placebook.commons.util.StringUtil;
import seedu.placebook.logic.parser.exceptions.ParseException;
import seedu.placebook.model.person.Address;
import seedu.placebook.model.person.Email;
import seedu.placebook.model.person.Name;
import seedu.placebook.model.person.Phone;
import seedu.placebook.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.\n"
            + "Please note that index should be less than 2^31.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code oneBasedIndexes} into a {@code List<Index>} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the any index is invalid (not non-zero unsigned integer).
     */
    public static List<Index> parseIndexes(String oneBasedIndexes) throws ParseException {
        String trimmedIndexes = oneBasedIndexes.trim();
        String[] indexesArray = trimmedIndexes.split(",");
        List<Index> indexes = new ArrayList<>();
        for (String oneBasedIndex : indexesArray) {
            indexes.add(parseIndex(oneBasedIndex));
        }
        return indexes;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String date} into a {@Code LocalDate}
     * Leading and trailing whitespaces will be removed.
     *
     * @throws ParseException if the given {@Code LocalDate} is invalid
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            return LocalDate.parse(trimmedDate, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Date format should be dd-MM-yyyy");
        }
    }

    /**
     * Parses a {@code String time} into a {@Code LocalTime}
     * Leading and trailing whitespaces will be removed.
     *
     * @throws ParseException if the given {@Code LocalTime} is invalid
     */
    public static LocalTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        try {
            return LocalTime.parse(trimmedTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("Time format should be HHMM");
        }
    }

    /**
     * Parses a {@code String time} into a {@Code LocalTime}
     * Leading and trailing whitespaces will be removed.
     *
     * @throws ParseException if the given {@Code LocalTime} is invalid
     */
    public static LocalDateTime parseDateTime(String dateTime) throws ParseException {
        requireNonNull(dateTime);
        String trimmedDateTime = dateTime.trim();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm");
        try {
            return LocalDateTime.parse(trimmedDateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ParseException("DateTime format should be \"dd-MM-yyyy HHmm\"."
                    + " Don't forget that there are only 12 months in a year!");
        }
    }

    /**
     * Parses a {@code String description} by removing leading and trailing whitespaces.
     */
    public static String parseDescription(String description) {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        return trimmedDescription;
    }
}
