package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.tag.Tag;
import seedu.address.testutil.Assert;

public class PriceTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Price(null));
    }

    @Test
    public void constructor_invalidPrice_throwsNumberFormatException() {
        String invalidPrice = "";
        Assert.assertThrows(NumberFormatException.class, () -> new Price(invalidPrice));
    }

    @Test
    public void isValidPrice() {

        // invalid prices
        assertFalse(Price.isValidPrice("-5")); // negative numbers
        assertFalse(Price.isValidPrice("-100"));

        // valid prices
        assertTrue(Price.isValidPrice("25"));
        assertTrue(Price.isValidPrice("5")); // single digit
        assertTrue(Price.isValidPrice("123456")); // large number
    }

    @Test
    public void checkPriceEquality() {
        //test price against non-price type
        assertFalse(new Price("100").equals(null));
        assertFalse(new Price("100").equals(new Tag("100")));
        //test correctly returns equal if price string is the same
        assertTrue(new Name("100").equals(new Name("100")));
    }
}
