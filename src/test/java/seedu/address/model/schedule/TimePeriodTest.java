package seedu.address.model.schedule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.schedule.exceptions.EndTimeBeforeStartTimeException;

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
}
