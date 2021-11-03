package seedu.placebook.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.placebook.testutil.Assert.assertThrows;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;

import seedu.placebook.model.schedule.exceptions.EndTimeBeforeStartTimeException;

public class TimePeriodTest {
    private static final LocalDateTime TEST_MOMENT1 = LocalDateTime.of(2021, 01, 01, 00, 00);
    private static final LocalDateTime TEST_MOMENT2 = LocalDateTime.of(2021, 01, 01, 23, 59);
    private static final LocalDateTime TEST_MOMENT3 = LocalDateTime.of(2021, 01, 02, 00, 00);
    private static final LocalDateTime TEST_MOMENT4 = LocalDateTime.of(2021, 01, 02, 23, 59);

    @Test
    public void timePeriod_endDateTimeIsBeforeStartDateTime_throwsException() {
        assertThrows(EndTimeBeforeStartTimeException.class, () -> new TimePeriod(TEST_MOMENT2, TEST_MOMENT1));
    }

    @Test
    public void timePeriod_endDateTimeIsNotAfterStartDateTime_throwsException() {
        assertThrows(EndTimeBeforeStartTimeException.class, () -> new TimePeriod(TEST_MOMENT2, TEST_MOMENT2));
    }

    @Test
    public void getStartDateTime_returnsCorrectStartDateTime() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        assertTrue(TEST_MOMENT1.equals(tp.getStartDateTime()));
    }

    @Test
    public void getEndDateTime_returnsCorrectEndDateTime() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        assertTrue(TEST_MOMENT2.equals(tp.getEndDateTime()));
    }

    @Test
    public void setStartDateTime_newStartDateTimeBeforeEndDateTime_success() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        tp.setStartDateTime(TEST_MOMENT1);
        assertTrue(TEST_MOMENT1.equals(tp.getStartDateTime()));
    }

    @Test
    public void setStartDateTime_newStartDateTimeAfterEndDateTime_throwsException() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        assertThrows(EndTimeBeforeStartTimeException.class, () -> tp.setStartDateTime(TEST_MOMENT4));
    }

    @Test
    public void setEndDateTime_newEndDateTimeAfterStartDateTime_success() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        tp.setEndDateTime(TEST_MOMENT4);
        assertTrue(TEST_MOMENT4.equals(tp.getEndDateTime()));
    }

    @Test
    public void setEndDateTime_newEndDateTimeBeforeStartDateTime_throwsException() {
        TimePeriod tp = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        assertThrows(EndTimeBeforeStartTimeException.class, () -> tp.setEndDateTime(TEST_MOMENT1));
    }

    @Test
    public void hasConflictWith_overlapCase1_returnsTrue() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT4);
        assertTrue(tp1.hasConflictWith(tp2));
        assertTrue(tp2.hasConflictWith(tp1));
    }

    @Test
    public void hasConflictWith_overlapCase2_returnsTrue() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT4);
        assertTrue(tp1.hasConflictWith(tp2));
        assertTrue(tp2.hasConflictWith(tp1));
    }

    @Test
    public void hasConflictWith_overlapAtOnlyOnePoint_returnsFalse() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT3, TEST_MOMENT4);
        assertFalse(tp1.hasConflictWith(tp2));
        assertFalse(tp2.hasConflictWith(tp1));
    }

    @Test
    public void hasConflictWith_noOverlap_returnsFalse() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT3, TEST_MOMENT4);
        assertFalse(tp1.hasConflictWith(tp2));
        assertFalse(tp2.hasConflictWith(tp1));
    }

    @Test
    public void equals_sameObject_returnTrue() {
        TimePeriod timePeriod = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        assertTrue(timePeriod.equals(timePeriod));
    }

    @Test
    public void equals_null_returnFalse() {
        TimePeriod timePeriod = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        assertFalse(timePeriod.equals(null));
    }

    @Test
    public void equals_sameStartDateTimeAndEndDateTime_returnTrue() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        assertTrue(tp1.equals(tp2));
    }

    @Test
    public void equals_differentStartDateTime_returnFalse() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        assertFalse(tp1.equals(tp2));
    }

    @Test
    public void equals_differentEndDateTime_returnFalse() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT2);
        assertFalse(tp1.equals(tp2));
    }

    @Test
    public void getStartDateAndTimeAsString_getCorrectString() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        String correctString = TEST_MOMENT1.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        assertTrue(tp1.getStartDateTimeAsString().equals(correctString));
    }

    @Test
    public void getEndDateAndTimeAsString_getCorrectString() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        String correctString = TEST_MOMENT3.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"));
        assertTrue(tp1.getEndDateTimeAsString().equals(correctString));
    }

    @Test
    public void compareTo_tp1HasStartDateTimeEarlierThanThatOfTp2_returnNegativeValue() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT2, TEST_MOMENT3);
        assertTrue(tp1.compareTo(tp2) < 0);
        assertTrue(tp2.compareTo(tp1) > 0);
    }

    @Test
    public void compareTo_tp1AndTp2HaveSameStartDateTimeAndTp1HasEndDateTimeEarlierThanThatOfTp2_returnNegativeValue() {
        TimePeriod tp1 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT3);
        TimePeriod tp2 = new TimePeriod(TEST_MOMENT1, TEST_MOMENT4);
        assertTrue(tp1.compareTo(tp2) < 0);
        assertTrue(tp2.compareTo(tp1) > 0);
    }
}
