package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

//@@author sherlynng
public class RateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Rate((Double) null, true));
    }

    @Test
    public void checkRateAccumulatedValue() {
        Rate oldRate = new Rate(2, true);
        oldRate.setCount(2);
        Rate newRate = new Rate(3, true);
        Rate expectedRate = new Rate(2.3, true);
        expectedRate.setCount(3);

        Rate actualRate = Rate.accumulatedValue(oldRate, newRate);
        assertTrue(expectedRate.equals(actualRate));
    }

    @Test
    public void checkRateToString() {
        assertTrue(new Rate(3, true).toString().equals("3.0")); // Integer rating
        assertTrue(new Rate(2.1, true).toString().equals("2.1")); // Rating with decimal value
    }

    @Test
    public void isValidRate() {

        // invalid rate
        assertFalse(Rate.isValidRate("-1.0")); // negative numbers
        assertFalse(Rate.isValidRate("6.0")); // exceed 5

        // valid rate
        assertTrue(Rate.isValidRate("3.3"));
        assertTrue(Rate.isValidRate("1")); // single digit
    }

    @Test
    public void checkRateEquality() {
        //test rate against non-rate type
        assertFalse(new Rate(1, true).equals(null));
        assertFalse(new Rate(1, true).equals(new Tag("100")));
        //test correctly returns equal if rate string is the same
        assertTrue(new Rate(1, true).equals(new Rate(1, true)));
    }

    @Test
    public void checkRateHashCode() {
        Rate rate = new Rate(3, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(2.1, true);
        assertTrue(rate.hashCode() == rate.hashCode());
        rate = new Rate(4.5, false);
        assertTrue(rate.hashCode() == rate.hashCode());
    }
}
