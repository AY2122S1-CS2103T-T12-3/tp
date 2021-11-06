package seedu.placebook.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import seedu.placebook.model.ReadOnlySchedule;

class SampleDataUtilTest {

    @Test
    void getSampleContacts() {
        // Sample contacts created is the same for every call

        assertEquals(SampleDataUtil.getSampleContacts(),
                SampleDataUtil.getSampleContacts());
    }

    @Test
    void getSampleSchedule() {
        // Sample schedule created depends on time

        ReadOnlySchedule schedule1 = SampleDataUtil.getSampleSchedule();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ReadOnlySchedule schedule2 = SampleDataUtil.getSampleSchedule();

        assertNotEquals(schedule1, schedule2);
    }
}
