package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.testutil.Assert;

public class SubjectTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Subject(null));
    }

    @Test
    public void constructor_invalidPrice_throwsIllegalArgumentException() {
        String invalidPrice = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidPrice));
    }

    @Test
    public void isValidPrice() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject
        assertFalse(Subject.isValidSubject("computer science")); //subjects not in list
        assertFalse(Subject.isValidSubject("malay"));

        // valid prices
        assertTrue(Subject.isValidSubject("math"));
        assertTrue(Subject.isValidSubject("English")); // check that case doesn't matter
        assertTrue(Subject.isValidSubject("chemistrY"));
    }
}
