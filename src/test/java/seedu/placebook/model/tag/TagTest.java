package seedu.placebook.model.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.placebook.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equalsMethod() {
        Tag tag = new Tag("1");
        Tag tag1 = new Tag("1");

        assertEquals(tag, tag);
        assertEquals(tag, tag1);
    }

}
