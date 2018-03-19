package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class LevelTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Level(null));
    }

    @Test
    public void constructor_invalidLevel_throwsIllegalArgumentException() {
        String invalidLevel = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Level(invalidLevel));
    }

    @Test
    public void isValidLevel() {
        // null level
        Assert.assertThrows(NullPointerException.class, () -> Level.isValidLevel(null));

        // invalid levels
        assertFalse(Level.isValidLevel("JC")); // not accepted levels
        assertFalse(Level.isValidLevel("middleSec"));

        // valid levels
        assertTrue(Level.isValidLevel("upperSec"));
        assertTrue(Level.isValidLevel("lowerPri"));
        assertTrue(Level.isValidLevel("Upperpri")); //check case insensitive
        assertTrue(Level.isValidLevel("Lowersec"));
    }

    @Test
    public void checkLevelEquality() {
        //test level against non-level type
        assertFalse(new Level("upperSec").equals(null));
        assertFalse(new Level("upperSec").equals(new Tag("upperSec")));
        //test correctly returns equal if level string is the same
        assertTrue(new Level("upperSec").equals(new Level("upperSec")));
    }

    @Test
    public void checkLevelHashCode() {
        Level level = new Level("uppersec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lowersec");
        assertTrue(level.hashCode() == level.value.hashCode());
        level = new Level("lowerpri");
        assertTrue(level.hashCode() == level.value.hashCode());
    }
}
