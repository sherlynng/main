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
    public void constructor_invalidSubject_throwsIllegalArgumentException() {
        String invalidSubject = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Subject(invalidSubject));
    }

    @Test
    public void isValidPrice() {
        // null subject
        Assert.assertThrows(NullPointerException.class, () -> Subject.isValidSubject(null));

        // invalid subject
        assertFalse(Subject.isValidSubject("computer science")); //subjects not in list
        assertFalse(Subject.isValidSubject("malay"));

        // valid subjects
        assertTrue(Subject.isValidSubject("math"));
        assertTrue(Subject.isValidSubject("English")); // check that case doesn't matter
        assertTrue(Subject.isValidSubject("chemistrY"));
    }

    @Test
    public void checkSubjectEquality() {
        //test name against non-name type
        assertFalse(new Subject("math").equals(null));
        assertFalse(new Subject("math").equals(new Address("math")));
        //test correctly returns equal if name string is the same
        assertTrue(new Subject("math").equals(new Subject("math")));
    }
}
