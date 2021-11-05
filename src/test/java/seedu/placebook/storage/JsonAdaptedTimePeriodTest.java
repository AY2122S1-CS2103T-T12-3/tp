package seedu.placebook.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.placebook.storage.JsonAdaptedTimePeriod.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.placebook.testutil.Assert.assertThrows;
import static seedu.placebook.testutil.TypicalAppointment.ALICE_APPOINTMENT;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.placebook.commons.exceptions.IllegalValueException;
import seedu.placebook.model.schedule.TimePeriod;

public class JsonAdaptedTimePeriodTest {
    private static final String INVALID_START_TIME = null;
    private static final String INVALID_END_TIME = null;

    private static final TimePeriod VALID_TIME_PERIOD = ALICE_APPOINTMENT.getTimePeriod();
    private static final String VALID_START_TIME = "25-12-2021 1000";
    private static final String VALID_END_TIME = "25-12-2021 1100";

    @Test
    public void toModelType_validTimePeriod_returnsTimePeriod() throws Exception {
        JsonAdaptedTimePeriod timePeriod = new JsonAdaptedTimePeriod(VALID_TIME_PERIOD);
        assertEquals(ALICE_APPOINTMENT.getTimePeriod(), timePeriod.toModelType());
    }

    @Test
    public void toModelType_validStartTimeEndTime_returnsTimePeriod() throws Exception {
        JsonAdaptedTimePeriod timePeriod = new JsonAdaptedTimePeriod(VALID_START_TIME, VALID_END_TIME);
        assertEquals(ALICE_APPOINTMENT.getTimePeriod(), timePeriod.toModelType());
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedTimePeriod timePeriod = new JsonAdaptedTimePeriod(INVALID_START_TIME, VALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, timePeriod::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedTimePeriod timePeriod = new JsonAdaptedTimePeriod(VALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, timePeriod::toModelType);
    }

    @Test
    public void toModelType_invalidStartTimeInvalidEndTime_throwsIllegalValueException() {
        JsonAdaptedTimePeriod timePeriod = new JsonAdaptedTimePeriod(INVALID_START_TIME, INVALID_END_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LocalDateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, timePeriod::toModelType);
    }
}
