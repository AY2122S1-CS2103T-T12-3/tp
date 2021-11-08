package seedu.placebook.model.schedule;

import java.util.List;
import java.util.function.Predicate;

import seedu.placebook.commons.util.StringUtil;

/**
 * Tests that a {@code Appointment}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Appointment> {
    //@@author
    // adapted from AB-3

    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Appointment appointment) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(appointment.getDescription(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
